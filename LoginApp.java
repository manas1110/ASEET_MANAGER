import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginApp extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton adminRadioButton, userRadioButton, staffRadioButton;
    private JButton loginButton;

    public LoginApp() {
        setTitle("Login Page");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 5, 5, 10));
        ImageIcon imageIcon = new ImageIcon("Screenshot 2023-11-20 120606.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Add spacing after the image
        panel.add(new JLabel("")); // Add an empty label for spacing

        panel.setBackground(new Color(224, 224, 224)); // Set panel background color

        JLabel userTypeLabel = new JLabel("Select User Type:");
        userTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userTypeLabel.setForeground(Color.BLACK); // Set label text color

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setForeground(Color.BLACK); // Set label text color
        passwordLabel.setForeground(Color.BLACK); // Set label text color

        usernameField = new JTextField();
        usernameField.setColumns(100);
        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        adminRadioButton = new JRadioButton("Admin");
        adminRadioButton.setFont(new Font("Arial", Font.BOLD, 22));
        userRadioButton = new JRadioButton("User");
        userRadioButton.setFont(new Font("Arial", Font.BOLD, 22));
        staffRadioButton = new JRadioButton("Staff");
        staffRadioButton.setFont(new Font("Arial", Font.BOLD, 22));
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(Color.BLACK); // Set button background color
        loginButton.setForeground(Color.WHITE); // Set button text color

        ButtonGroup group = new ButtonGroup();
        group.add(adminRadioButton);
        group.add(userRadioButton);
        group.add(staffRadioButton);

        loginButton.addActionListener(this);

        panel.add(userTypeLabel);
        panel.add(new JLabel(""));
        panel.add(adminRadioButton);
        panel.add(userRadioButton);
        panel.add(staffRadioButton);
        panel.add(new JLabel(""));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel(""));
        panel.add(loginButton);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to the panel

        add(panel);
        setVisible(true);

    }
    

 

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String userType = "";

        if (e.getSource() == loginButton) {
            if (adminRadioButton.isSelected()) {
                if (username.equals("admin") && String.valueOf(password).equals("adminpass")) {
                    userType = "Admin";
                }
            } else if (userRadioButton.isSelected()) {
                if (username.equals("user") && String.valueOf(password).equals("userpass")) {
                    userType = "User";
                }
            } else if (staffRadioButton.isSelected()) {
                if (username.equals("staff") && String.valueOf(password).equals("staffpass")) {
                    userType = "Staff";
                }
            }

            if (userType.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            openDashboard(userType);
        }
    }

    private void openDashboard(String userType) {
        dispose(); // Close the login window
        if (userType.equals("Admin")) {
            JFrame dashboard = new JFrame("Dashboard - " + userType);
            dashboard.setSize(400, 300);
            dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboard.setLocationRelativeTo(null);

            JLabel welcomeLabel = new JLabel("Welcome, " + userType + "!");
            dashboard.add(welcomeLabel);

            // Create and add AssetManagementSystem panel to the dashboard
            AdminManagementSystem adminManagementSystem = new AdminManagementSystem();
            dashboard.getContentPane().add(adminManagementSystem);

            dashboard.setVisible(true);
        }
        else if(userType.equals("User")){
            JFrame dashboard = new JFrame("Dashboard - " + userType);
            dashboard.setSize(400, 300);
            dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboard.setLocationRelativeTo(null);

            JLabel welcomeLabel = new JLabel("Welcome, " + userType + "!");
            dashboard.add(welcomeLabel);

            // Create and add AssetManagementSystem panel to the dashboard
            AssetManagementSystem assetManagementSystem = new AssetManagementSystem();
            dashboard.getContentPane().add(assetManagementSystem);

            dashboard.setVisible(true);
        }
        else if(userType.equals("Staff")){
            JFrame dashboard = new JFrame("Dashboard - " + userType);
            dashboard.setSize(400, 300);
            dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboard.setLocationRelativeTo(null);

            JLabel welcomeLabel = new JLabel("Welcome, " + userType + "!");
            dashboard.add(welcomeLabel);

            // Create and add AssetManagementSystem panel to the dashboard
            StaffMaintenanceSystem staffMaintenanceSystem = new StaffMaintenanceSystem();
            dashboard.getContentPane().add(staffMaintenanceSystem);

            dashboard.setVisible(true);
        }

        // Remaining code for opening respective dashboards based on user type (Admin, User, Staff)
        // (Same as the previously provided code)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginApp());
    }
}
