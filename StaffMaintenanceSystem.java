import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class StaffMaintenanceSystem extends JPanel implements ActionListener {
    JLabel titleLabel, assetIDLabel, maintenanceDetailsLabel;
    JTextField assetIDTextField;
    JTextArea maintenanceDetailsTextArea;
    JButton submitButton, logoutButton, viewDataButton;
    Map<String, String> maintenanceMap = new HashMap<>();

    public StaffMaintenanceSystem() {
        titleLabel = new JLabel("Staff Maintenance System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetIDLabel = new JLabel("Asset ID: ");
        assetIDLabel.setFont(new Font("Arial", Font.BOLD, 18));
        maintenanceDetailsLabel = new JLabel("Maintenance Details: ");
        maintenanceDetailsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetIDTextField = new JTextField(20);
        maintenanceDetailsTextArea = new JTextArea(5, 20);
        submitButton = new JButton("Submit Maintenance Request");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        viewDataButton = new JButton("View File Data");
        viewDataButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));

        logoutButton.addActionListener(this);
        viewDataButton.addActionListener(this);
        submitButton.addActionListener(this);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2));
        centerPanel.add(assetIDLabel);
        centerPanel.add(assetIDTextField);
        centerPanel.add(maintenanceDetailsLabel);
        centerPanel.add(maintenanceDetailsTextArea);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitButton);
        bottomPanel.add(viewDataButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String assetID = assetIDTextField.getText();
            String maintenanceDetails = maintenanceDetailsTextArea.getText();

            if (!assetID.isEmpty() && !maintenanceDetails.isEmpty()) {
                if(!SubstringChecker(assetID)) {
                
                    showErrorDialog("Asset with the given ID Don't exists.");
                    return;
                
            }
            else{
                maintenanceMap.put(assetID, maintenanceDetails);
                updateFile(assetID, maintenanceDetails);
                showSuccessDialog("Maintenance request submitted successfully.");
            } }else {
                showErrorDialog("Please fill in both Asset ID and Maintenance Details.");
            }
        } else if (e.getSource() == viewDataButton) {
            displayFileData();
        } else if (e.getSource() == logoutButton) {
            openLoginPage();
        }
    }

    private void displayFileData() {
        File file = new File("assetData.txt");
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder fileContent = new StringBuilder();

            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            JTextArea fileDataTextArea = new JTextArea(20, 40);
            fileDataTextArea.setText(fileContent.toString());
            fileDataTextArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(fileDataTextArea);
            JOptionPane.showMessageDialog(this, scrollPane, "Asset Data", JOptionPane.PLAIN_MESSAGE);
        } catch (FileNotFoundException ex) {
           // showErrorMessage("File not found or cannot be accessed.");
        }
    }
     public boolean SubstringChecker(String substring) {
    File file = new File("assetData.txt");
     
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(substring)) {
                    return true; // Substring found in this line
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Substring not found in any line or error occurred while reading the file
    }

    private void updateFile(String assetID, String maintenanceDetails) {
        File file = new File("assetData.txt");
        StringBuilder content = new StringBuilder();
        String line2="";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
           
            while ((line = reader.readLine()) != null) {
                
                    
                    
                
                
                content.append(line).append("\n");
                
                
            }
            line2 = "ASSET_ID- " + assetID +  " || MAINTENANCE_DESCRIPTION- " + maintenanceDetails;
            content.append(line2).append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openLoginPage() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.dispose();
        new LogoutApp();
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Staff Maintenance System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new StaffMaintenanceSystem());
        frame.pack();
        frame.setVisible(true);
    }
}
