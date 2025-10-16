package ui;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import utils.DBConnection;

public class RegistrationForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JPasswordField confirmPassField;
    private JButton registerBtn;
    private JButton backToLoginBtn;

    public RegistrationForm() {
        setTitle("User Registration");
        setSize(400, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        userField = new JTextField(20);
        add(userField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        passField = new JPasswordField(20);
        add(passField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        confirmPassField = new JPasswordField(20);
        add(confirmPassField, gbc);

        // Register Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> handleRegistration());
        add(registerBtn, gbc);

        // Back to Login Button
        gbc.gridx = 2;
        gbc.gridy = 3;
        backToLoginBtn = new JButton("Back to Login");
        backToLoginBtn.addActionListener(e -> {
            new LoginForm();
            dispose();
        });
        add(backToLoginBtn, gbc);

        setVisible(true);
    }

    private void handleRegistration() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        String confirmPassword = new String(confirmPassField.getPassword());

        // Validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        if (username.length() < 4) {
            JOptionPane.showMessageDialog(this, "Username must be at least 4 characters long!");
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        if (registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            new LoginForm();
            dispose();
        }
    }

    private boolean registerUser(String username, String password) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Unable to connect to database");
                return false;
            }

            // First check if username already exists
            String checkSql = "SELECT username FROM users WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists!");
                    return false;
                }
            }

            // If username is available, create new user
            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            String error = ex.getMessage();
            if (error.contains("users")) {
                JOptionPane.showMessageDialog(this, 
                    "Database table 'users' not found. Please run setup_db.sql first.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Database error: " + error,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}