import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

// ==================== ENUM: Amenity ====================
enum Amenity 
{
    AC, WIFI, TV
}

// ==================== ENUM: RoomType ====================
enum RoomType 
{
    SINGLE,
    DOUBLE,
    DELUXE,
    SUITE,
    PRESIDENTIAL;

    public double getBasePrice() 
    {
        switch (this) 
        {
            case SINGLE: 
                return 1500;
            case DOUBLE: 
                return 2500;
            case DELUXE: 
                return 4000;
            case SUITE: 
                return 7000;
            case PRESIDENTIAL: 
                return 12000;
            default: 
                return 1000;
        }
    }
}

// ==================== CLASS: Room ====================
class Room 
{
    private int roomNo;
    private RoomType roomType;
    private String status;
    private int floor;
    private java.util.List<Amenity> amenities;
    private double price;

    public Room(int roomNo, RoomType roomType, String status, int floor, java.util.List<Amenity> amenities, double price) 
    {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.status = status;
        this.floor = floor;
        this.amenities = amenities;
        this.price = price;
    }

    public int getRoomNo() 
    { 
        return roomNo; 
    }
    
    public RoomType getRoomType() 
    { 
        return roomType; 
    }
    
    public String getStatus() 
    { 
        return status; 
    }
    
    public int getFloor() 
    { 
        return floor; 
    }
    
    public java.util.List<Amenity> getAmenitiesList() 
    { 
        return amenities; 
    }
    
    public double getPrice() 
    { 
        return price; 
    }

    public String getAmenities() 
    {
        return amenities.stream().map(Enum::name).collect(java.util.stream.Collectors.joining(", "));
    }

    public void setRoomType(RoomType roomType) 
    { 
        this.roomType = roomType; 
    }
    
    public void setStatus(String status) 
    { 
        this.status = status; 
    }
    
    public void setFloor(int floor) 
    { 
        this.floor = floor; 
    }
    
    public void setAmenities(java.util.List<Amenity> amenities) 
    { 
        this.amenities = amenities; 
    }
    
    public void setPrice(double price) 
    { 
        this.price = price; 
    }

    public String toString() 
    {
        return "Room " + roomNo + " (" + roomType + ")";
    }
}

// ==================== CLASS: RoomDAO ====================
class RoomDAO 
{
    private java.util.List<Room> roomList;
    
    public RoomDAO()
    {
        roomList = new ArrayList<Room>();
        loadFakeData();
    }
    
    private void loadFakeData() 
    {
        ArrayList<Amenity> am1 = new ArrayList<Amenity>();
        am1.add(Amenity.AC);
        am1.add(Amenity.WIFI);
        roomList.add(new Room(101, RoomType.SINGLE, "Available", 1, am1, 1500));
        
        ArrayList<Amenity> am2 = new ArrayList<Amenity>();
        am2.add(Amenity.AC);
        am2.add(Amenity.WIFI);
        am2.add(Amenity.TV);
        roomList.add(new Room(201, RoomType.DOUBLE, "Available", 2, am2, 2500));
        
        ArrayList<Amenity> am3 = new ArrayList<Amenity>();
        am3.add(Amenity.AC);
        am3.add(Amenity.WIFI);
        am3.add(Amenity.TV);
        roomList.add(new Room(301, RoomType.DELUXE, "Occupied", 3, am3, 4000));
        
        ArrayList<Amenity> am4 = new ArrayList<Amenity>();
        am4.add(Amenity.AC);
        am4.add(Amenity.WIFI);
        am4.add(Amenity.TV);
        roomList.add(new Room(401, RoomType.SUITE, "Available", 4, am4, 7000));
        
        ArrayList<Amenity> am5 = new ArrayList<Amenity>();
        am5.add(Amenity.AC);
        am5.add(Amenity.WIFI);
        am5.add(Amenity.TV);
        roomList.add(new Room(501, RoomType.PRESIDENTIAL, "Maintenance", 5, am5, 12000));
    }
    
    public void addRoom(Room r) 
    {
        for (int i = 0; i < roomList.size(); i++) 
        {
            Room existing = roomList.get(i);
            if (existing.getRoomNo() == r.getRoomNo()) 
            {
                throw new IllegalArgumentException("Room number already exists!");
            }
        }
        roomList.add(r);
    }

    public java.util.List<Room> getAllRooms() 
    {
        return roomList;
    }

    public boolean updateRoom(Room r) 
    {
        for (int i = 0; i < roomList.size(); i++) 
        {
            if (roomList.get(i).getRoomNo() == r.getRoomNo()) 
            {
                roomList.set(i, r);
                return true;
            }
        }
        return false;
    }

    public boolean deleteRoom(int roomNo) 
    {
        for (int i = 0; i < roomList.size(); i++) 
        {
            if (roomList.get(i).getRoomNo() == roomNo) 
            {
                roomList.remove(i);
                return true;
            }
        }
        return false;
    }
}

// ==================== CLASS: RoomManagementPanel ====================
class RoomManagementPanel extends JFrame 
{        
    private JTextField txtRoomNo, txtPrice, txtFloor, txtSearch;
    private JComboBox<RoomType> cmbType;
    private JComboBox<String> cmbStatus;
    private JCheckBox cbAC, cbWiFi, cbTV;
    private JTable table;
    private DefaultTableModel model;
    private RoomDAO dao;
    
    public RoomManagementPanel() 
    {
        dao = new RoomDAO();
        setTitle("🏨 Hotel Room Management Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("🏨 Hotel Room Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(40, 40, 90));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(255, 255, 255));
        form.setBorder(new CompoundBorder(new EmptyBorder(15, 15, 15, 15),
                new TitledBorder(new LineBorder(new Color(150, 150, 200), 1, true),
                        "Room Details", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14))));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        txtRoomNo = new JTextField();
        cmbType = new JComboBox<RoomType>(RoomType.values());
        txtPrice = new JTextField();
        cmbStatus = new JComboBox<String>(new String[]{"Available", "Occupied", "Maintenance", "Cleaning", "Out of Order"});
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
        
        String[] columns = {"Room No", "Type", "Price", "Status", "Floor", "Amenities"};
        model = new DefaultTableModel(columns, 0) 
        {
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new EmptyBorder(15, 15, 0, 15));
        txtSearch = new JTextField(20);
        txtSearch.setToolTipText("Search by room number, type, or status");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearch);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(searchPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, rightPanel);
        splitPane.setDividerLocation(350);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);

        btnAdd.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                addRoom();
            }
        });
        
        btnUpdate.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                updateRoom();
            }
        });
        
        btnDelete.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                deleteRoom();
            }
        });
        
        btnClear.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                clearForm();
            }
        });

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            { 
                filterTable(txtSearch.getText()); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            { 
                filterTable(txtSearch.getText()); 
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            { 
                filterTable(txtSearch.getText()); 
            }
        });

        table.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int row = table.getSelectedRow();
                if (row >= 0) 
                {
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
            }
        });

        cmbType.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                RoomType type = (RoomType) cmbType.getSelectedItem();
                if (type != null) 
                {
                    txtPrice.setText(String.valueOf(type.getBasePrice()));
                }
            }
        });

        refreshTable();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void addField(JPanel panel, GridBagConstraints c, int row, String label, JComponent field) 
    {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        panel.add(field, c);
    }

    private JButton styledButton(String text, Color color) 
    {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private void refreshTable() 
    {
        model.setRowCount(0);
        java.util.List<Room> rooms = dao.getAllRooms();
        for (int i = 0; i < rooms.size(); i++) 
        {
            Room r = rooms.get(i);
            model.addRow(new Object[]
            {
                r.getRoomNo(),
                r.getRoomType(),
                r.getPrice(),
                r.getStatus(),
                r.getFloor(),
                r.getAmenities()
            });
        }
    }

    private void addRoom() 
    {
        try 
        {
            int roomNo = Integer.parseInt(txtRoomNo.getText());
            double price = Double.parseDouble(txtPrice.getText());
            int floor = Integer.parseInt(txtFloor.getText());

            java.util.List<Amenity> amenities = new ArrayList<Amenity>();
            if (cbAC.isSelected()) 
            {
                amenities.add(Amenity.AC);
            }
            if (cbWiFi.isSelected()) 
            {
                amenities.add(Amenity.WIFI);
            }
            if (cbTV.isSelected()) 
            {
                amenities.add(Amenity.TV);
            }

            Room r = new Room(roomNo, (RoomType) cmbType.getSelectedItem(),
                    cmbStatus.getSelectedItem().toString(), floor, amenities, price);

            dao.addRoom(r);
            JOptionPane.showMessageDialog(this, "✅ Room Added Successfully!");
            refreshTable();
            clearForm();
        } 
        catch (NumberFormatException ex) 
        {
            JOptionPane.showMessageDialog(this, "Invalid Number Format!");
        } 
        catch (IllegalArgumentException ex) 
        {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateRoom() 
    {
        try 
        {
            int roomNo = Integer.parseInt(txtRoomNo.getText());
            double price = Double.parseDouble(txtPrice.getText());
            int floor = Integer.parseInt(txtFloor.getText());

            java.util.List<Amenity> amenities = new ArrayList<Amenity>();
            if (cbAC.isSelected()) 
            {
                amenities.add(Amenity.AC);
            }
            if (cbWiFi.isSelected()) 
            {
                amenities.add(Amenity.WIFI);
            }
            if (cbTV.isSelected()) 
            {
                amenities.add(Amenity.TV);
            }

            Room r = new Room(roomNo, (RoomType) cmbType.getSelectedItem(),
                    cmbStatus.getSelectedItem().toString(), floor, amenities, price);

            if (dao.updateRoom(r)) 
            {
                JOptionPane.showMessageDialog(this, "Room Updated Successfully!");
                refreshTable();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Room not found!");
            }

        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(this, "Error Updating Room!");
        }
    }

    private void deleteRoom() 
    {
        try 
        {
            int roomNo = Integer.parseInt(txtRoomNo.getText());
            if (dao.deleteRoom(roomNo)) 
            {
                JOptionPane.showMessageDialog(this, "Room Deleted!");
                refreshTable();
                clearForm();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Room not found!");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Select a valid room!");
        }
    }

    private void clearForm() 
    {
        txtRoomNo.setText("");
        txtPrice.setText("");
        txtFloor.setText("");
        cbAC.setSelected(false);
        cbWiFi.setSelected(false);
        cbTV.setSelected(false);
        cmbType.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
    }

    private void filterTable(String keyword) 
    {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword)); 
    }
}

// ==================== MAIN CLASS ====================
public class RoomManagementComplete 
{
    public static void main(String[] args) 
    {
        new RoomManagementPanel();
    }
}
