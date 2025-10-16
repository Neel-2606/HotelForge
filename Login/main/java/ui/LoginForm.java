package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import utils.DBConnection;
import utils.SessionManager;

public class LoginForm extends JFrame implements ActionListener {
    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;
    JCheckBox showPass;
    int attempts = 0;
    final int MAX_ATTEMPTS = 3;

    public LoginForm() {
        setTitle("Login Form");
        setSize(350, 250);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        userField = new JTextField(20);
        add(userField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        passField = new JPasswordField(20);
        add(passField, gbc);

        // Show password checkbox
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        showPass = new JCheckBox("Show Password");
        showPass.addActionListener(e -> passField.setEchoChar(showPass.isSelected() ? (char)0 : '\u2022'));
        add(showPass, gbc);

        // Login button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        add(loginBtn, gbc);

        // Register button
        gbc.gridx = 2;
        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> {
            new RegistrationForm();
            dispose();
        });
        add(registerBtn, gbc);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userField.getText().trim();
        String pass = String.valueOf(passField.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }
        if (validateLogin(user, pass)) {
            SessionManager.setCurrentUser(user);
            JOptionPane.showMessageDialog(this, "Login Successful!");
            // Proceed to next window or main app
            dispose();
        } else {
            attempts++;
            if (attempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(this, "Too many failed attempts. Exiting.");
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials! Attempts left: " + (MAX_ATTEMPTS - attempts));
            }
        }
    }

    private boolean validateLogin(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return false;
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password); // For demo only! Use hashed passwords in production.
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}
