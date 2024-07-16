import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutApp extends JFrame implements ActionListener {

    public LogoutApp() {
        setTitle("Logout Page");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Make the component fill the available horizontal space
        gbc.weighty = 1.0; // Make the component fill the available vertical space
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel messageLabel = new JLabel("Are you sure you want to logout?");
        panel.add(messageLabel, gbc);

        gbc.gridy = 1;
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        panel.add(logoutButton, gbc);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Logout")) {
            openLoginApp();
        }
    }

    private void openLoginApp() {
        JFrame loginFrame = new JFrame("Login Page");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.getContentPane().add(new LoginApp());
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        // Close the current LogoutApp frame
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LogoutApp());
    }
}
