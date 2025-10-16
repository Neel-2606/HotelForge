package main.ui;

import main.dao.RoomDAO;
import main.model.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class RoomManagementPanel extends JFrame {
    private JTextField txtRoomNo, txtPrice, txtFloor, txtSearch;
    private JComboBox<RoomType> cmbType;
    private JComboBox<String> cmbStatus;
    private JCheckBox cbAC, cbWiFi, cbTV;
    private JTable table;
    private DefaultTableModel model;
    private RoomDAO dao;

    public RoomManagementPanel() {
        dao = new RoomDAO();

        setTitle("🏨 Hotel Room Management Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 247, 250));

        // ---------- Title ----------
        JLabel title = new JLabel("🏨 Hotel Room Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(40, 40, 90));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // ---------- Form Panel ----------
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(255, 255, 255));
        form.setBorder(new CompoundBorder(new EmptyBorder(15, 15, 15, 15),
                new TitledBorder(new LineBorder(new Color(150, 150, 200), 1, true),
                        "Room Details", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14))));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        txtRoomNo = new JTextField();
        cmbType = new JComboBox<>(RoomType.values());
        txtPrice = new JTextField();
        cmbStatus = new JComboBox<>(new String[]{"Available", "Occupied", "Maintenance", "Cleaning", "Out of Order"});
        txtFloor = new JTextField();

        cbAC = new JCheckBox("AC");
        cbWiFi = new JCheckBox("WiFi");
        cbTV = new JCheckBox("TV");
        cbAC.setBackground(Color.WHITE);
        cbWiFi.setBackground(Color.WHITE);
        cbTV.setBackground(Color.WHITE);
        JPanel amPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amPanel.setBackground(Color.WHITE);
        amPanel.add(cbAC);
        amPanel.add(cbWiFi);
        amPanel.add(cbTV);

        int row = 0;
        addField(form, c, row++, "Room Number:", txtRoomNo);
        addField(form, c, row++, "Room Type:", cmbType);
        addField(form, c, row++, "Price:", txtPrice);
        addField(form, c, row++, "Status:", cmbStatus);
        addField(form, c, row++, "Floor:", txtFloor);
        addField(form, c, row++, "Amenities:", amPanel);

        JButton btnAdd = styledButton("Add", new Color(60, 180, 75));
        JButton btnUpdate = styledButton("Update", new Color(60, 120, 240));
        JButton btnDelete = styledButton("Delete", new Color(220, 70, 70));
        JButton btnClear = styledButton("Clear", new Color(140, 140, 140));

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        form.add(btnPanel, c);

        add(form, BorderLayout.WEST);

        // ---------- Table ----------
        model = new DefaultTableModel(new String[]{"Room No", "Type", "Price", "Status", "Floor", "Amenities"}, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setGridColor(new Color(230, 230, 230));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---------- Search Bar ----------
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(240, 243, 250));
        txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchPanel.add(new JLabel("🔍 Search: "));
        searchPanel.add(txtSearch);
        add(searchPanel, BorderLayout.SOUTH);

        refreshTable();

        // ---------- Listeners ----------
        btnAdd.addActionListener(e -> addRoom());
        btnUpdate.addActionListener(e -> updateRoom());
        btnDelete.addActionListener(e -> deleteRoom());
        btnClear.addActionListener(e -> clearForm());
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filterTable(txtSearch.getText());
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtRoomNo.setText(model.getValueAt(row, 0).toString());
                cmbType.setSelectedItem(RoomType.valueOf(model.getValueAt(row, 1).toString()));
                txtPrice.setText(model.getValueAt(row, 2).toString());
                cmbStatus.setSelectedItem(model.getValueAt(row, 3).toString());
                txtFloor.setText(model.getValueAt(row, 4).toString());
                String amenities = model.getValueAt(row, 5).toString();
                cbAC.setSelected(amenities.contains("AC"));
                cbWiFi.setSelected(amenities.contains("WIFI"));
                cbTV.setSelected(amenities.contains("TV"));
            }
        });

        // Auto-fill price when type changes
        cmbType.addActionListener(e -> {
            RoomType type = (RoomType) cmbType.getSelectedItem();
            if (type != null) txtPrice.setText(String.valueOf(type.getBasePrice()));
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addField(JPanel form, GridBagConstraints c, int row, String label, Component field) {
        c.gridx = 0;
        c.gridy = row;
        form.add(new JLabel(label), c);
        c.gridx = 1;
        form.add(field, c);
    }

    private JButton styledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Room r : dao.getAllRooms()) {
            model.addRow(new Object[]{
                    r.getRoomNo(),
                    r.getRoomType(),
                    r.getPrice(),
                    r.getStatus(),
                    r.getFloor(),
                    r.getAmenities()
            });
        }
    }

    private void addRoom() {
        try {
            int roomNo = Integer.parseInt(txtRoomNo.getText());
            double price = Double.parseDouble(txtPrice.getText());
            int floor = Integer.parseInt(txtFloor.getText());

            List<Amenity> amenities = new ArrayList<>();
            if (cbAC.isSelected()) amenities.add(Amenity.AC);
            if (cbWiFi.isSelected()) amenities.add(Amenity.WIFI);
            if (cbTV.isSelected()) amenities.add(Amenity.TV);

            Room r = new Room(roomNo, (RoomType) cmbType.getSelectedItem(),
                    cmbStatus.getSelectedItem().toString(), floor, amenities, price);

            dao.addRoom(r);
            JOptionPane.showMessageDialog(this, "✅ Room Added Successfully!");
            refreshTable();
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Number Format!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateRoom() {
        try {
            int roomNo = Integer.parseInt(txtRoomNo.getText());
            double price = Double.parseDouble(txtPrice.getText());
            int floor = Integer.parseInt(txtFloor.getText());

            List<Amenity> amenities = new ArrayList<>();
            if (cbAC.isSelected()) amenities.add(Amenity.AC);
            if (cbWiFi.isSelected()) amenities.add(Amenity.WIFI);
            if (cbTV.isSelected()) amenities.add(Amenity.TV);

            Room r = new Room(roomNo, (RoomType) cmbType.getSelectedItem(),
                    cmbStatus.getSelectedItem().toString(), floor, amenities, price);

            if (dao.updateRoom(r)) {
                JOptionPane.showMessageDialog(this, "Room Updated Successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Room not found!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error Updating Room!");
        }
    }

    private void deleteRoom() {
        try {
            int roomNo = Integer.parseInt(txtRoomNo.getText());
            if (dao.deleteRoom(roomNo)) {
                JOptionPane.showMessageDialog(this, "Room Deleted!");
                refreshTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Room not found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Select a valid room!");
        }
    }

    private void clearForm() {
        txtRoomNo.setText("");
        txtPrice.setText("");
        txtFloor.setText("");
        cbAC.setSelected(false);
        cbWiFi.setSelected(false);
        cbTV.setSelected(false);
        cmbType.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
    }

    private void filterTable(String keyword) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword)); // Case-insensitive
    }

    public static void main(String[] args) {
        new RoomManagementPanel();
    }
}
