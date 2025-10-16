package com.hotel.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

/**
 * AvailabilityGridDialog - Shows 30-day availability grid
 * Demonstrates advanced Swing UI components and custom rendering
 */
public class AvailabilityGridDialog extends JDialog {
    private Map<LocalDate, Set<Integer>> availabilityData;
    private JTable availabilityTable;
    private DefaultTableModel tableModel;
    
    public AvailabilityGridDialog(JFrame parent, Map<LocalDate, Set<Integer>> availability) {
        super(parent, "Room Availability Grid (Next 30 Days)", true);
        this.availabilityData = availability;
        
        initializeComponents();
        setupLayout();
        populateGrid();
        
        setSize(900, 600);
        setLocationRelativeTo(parent);
    }
    
    private void initializeComponents() {
        // Create table model with dates as rows and room IDs as columns
        Set<Integer> allRooms = getAllRoomIds();
        String[] columnNames = new String[allRooms.size() + 1];
        columnNames[0] = "Date";
        
        int index = 1;
        for (Integer roomId : allRooms) {
            columnNames[index++] = "Room " + roomId;
        }
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        availabilityTable = new JTable(tableModel);
        availabilityTable.setDefaultRenderer(Object.class, new AvailabilityCellRenderer());
        availabilityTable.setRowHeight(25);
        
        // Set column widths
        availabilityTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        for (int i = 1; i < columnNames.length; i++) {
            availabilityTable.getColumnModel().getColumn(i).setPreferredWidth(80);
        }
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Room Availability Grid - Next 30 Days");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        
        // Legend panel
        JPanel legendPanel = createLegendPanel();
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(availabilityTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(legendPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel(new FlowLayout());
        legendPanel.setBorder(BorderFactory.createTitledBorder("Legend"));
        
        // Available indicator
        JLabel availableLabel = new JLabel("Available");
        availableLabel.setOpaque(true);
        availableLabel.setBackground(Color.GREEN);
        availableLabel.setForeground(Color.WHITE);
        availableLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Booked indicator
        JLabel bookedLabel = new JLabel("Booked");
        bookedLabel.setOpaque(true);
        bookedLabel.setBackground(Color.RED);
        bookedLabel.setForeground(Color.WHITE);
        bookedLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Past date indicator
        JLabel pastLabel = new JLabel("Past Date");
        pastLabel.setOpaque(true);
        pastLabel.setBackground(Color.GRAY);
        pastLabel.setForeground(Color.WHITE);
        pastLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        legendPanel.add(availableLabel);
        legendPanel.add(Box.createHorizontalStrut(20));
        legendPanel.add(bookedLabel);
        legendPanel.add(Box.createHorizontalStrut(20));
        legendPanel.add(pastLabel);
        
        return legendPanel;
    }
    
    private void populateGrid() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        LocalDate today = LocalDate.now();
        Set<Integer> allRooms = getAllRoomIds();
        
        // Sort dates
        LocalDate[] sortedDates = availabilityData.keySet().toArray(new LocalDate[0]);
        java.util.Arrays.sort(sortedDates);
        
        for (LocalDate date : sortedDates) {
            Object[] row = new Object[allRooms.size() + 1];
            row[0] = date.format(formatter);
            
            Set<Integer> availableRooms = availabilityData.get(date);
            int index = 1;
            
            for (Integer roomId : allRooms) {
                if (date.isBefore(today)) {
                    row[index] = "PAST";
                } else if (availableRooms.contains(roomId)) {
                    row[index] = "AVAILABLE";
                } else {
                    row[index] = "BOOKED";
                }
                index++;
            }
            
            tableModel.addRow(row);
        }
    }
    
    private Set<Integer> getAllRoomIds() {
        Set<Integer> allRooms = new java.util.TreeSet<>(); // TreeSet for sorted order
        for (Set<Integer> rooms : availabilityData.values()) {
            allRooms.addAll(rooms);
        }
        
        // Add booked rooms that might not appear in available sets
        // This is a simplified approach - in real implementation, 
        // you'd query the database for all room IDs
        for (int i = 101; i <= 110; i++) {
            allRooms.add(i);
        }
        
        return allRooms;
    }
    
    /**
     * Custom cell renderer for availability grid
     */
    private class AvailabilityCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            
            if (column == 0) {
                // Date column - default rendering
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
                setHorizontalAlignment(SwingConstants.LEFT);
            } else {
                // Room columns - color coding
                String status = (String) value;
                setHorizontalAlignment(SwingConstants.CENTER);
                
                if ("AVAILABLE".equals(status)) {
                    setBackground(Color.GREEN);
                    setForeground(Color.WHITE);
                    setText("✓");
                } else if ("BOOKED".equals(status)) {
                    setBackground(Color.RED);
                    setForeground(Color.WHITE);
                    setText("✗");
                } else if ("PAST".equals(status)) {
                    setBackground(Color.GRAY);
                    setForeground(Color.WHITE);
                    setText("-");
                }
                
                // Override selection colors
                if (isSelected) {
                    setBackground(getBackground().darker());
                }
            }
            
            return component;
        }
    }
}
