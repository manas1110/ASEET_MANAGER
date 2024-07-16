import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AdminManagementSystem extends JPanel implements ActionListener {
    private JLabel assetTitleLabel, assetIDLabel, assetTypeLabel, assetDescriptionLabel;
    private JTextField assetIDTextField, assetTypeTextField;
    private JTextArea assetDescriptionTextArea;
    private JButton addButton, editButton, deleteButton, logoutButton, viewFileDataButton;
    private JCheckBox duplicateCheckbox;

    private JLabel maintenanceTitleLabel, maintenanceAssetIDLabel, maintenanceDetailsLabel;
    private JTextField maintenanceAssetIDTextField;
    private JTextArea maintenanceDetailsTextArea;
    private JButton submitMaintenanceStatusButton; // Updated button name
    private AddingCostDialog addingCostDialog; // New field

    private Map<String, String> assetMap = new HashMap<>();
    private List<String> assetList = new ArrayList<>();
    private Map<String, String> maintenanceMap = new HashMap<>();

    public AdminManagementSystem() {
        
        assetTitleLabel = new JLabel("Asset Management System");
        assetTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetIDLabel = new JLabel("Asset ID: ");
        assetIDLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetTypeLabel = new JLabel("Asset Type: ");
        assetTypeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetDescriptionLabel = new JLabel("Asset Description: ");
        assetDescriptionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetIDTextField = new JTextField(20);
        assetTypeTextField = new JTextField(20);
        assetDescriptionTextArea = new JTextArea(5, 20);
        addButton = new JButton("Add Asset");
        addButton.setFont(new Font("Arial", Font.BOLD, 18));
        editButton = new JButton("Edit Asset");
        editButton.setFont(new Font("Arial", Font.BOLD, 18));
        deleteButton = new JButton("Delete Asset");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 18));
        duplicateCheckbox = new JCheckBox("Check for duplicates before adding", true);
        duplicateCheckbox.setFont(new Font("Arial", Font.BOLD, 18));

        maintenanceTitleLabel = new JLabel("Staff Maintenance System");
        maintenanceTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        maintenanceAssetIDLabel = new JLabel("Asset ID: ");
        maintenanceAssetIDLabel.setFont(new Font("Arial", Font.BOLD, 18));
        maintenanceDetailsLabel = new JLabel("Maintenance Details: ");
        maintenanceDetailsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        maintenanceAssetIDTextField = new JTextField(20);
        maintenanceDetailsTextArea = new JTextArea(5, 20);
        submitMaintenanceStatusButton = new JButton("Submit Maintenance Status"); // Updated button name
        submitMaintenanceStatusButton.setFont(new Font("Arial", Font.BOLD, 18));

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));

        viewFileDataButton = new JButton("View File Data");
        viewFileDataButton.setFont(new Font("Arial", Font.BOLD, 18));
        viewFileDataButton.addActionListener(this);

        setLayout(new BorderLayout());

        JPanel assetPanel = new JPanel();
        assetPanel.setLayout(new GridLayout(6, 2, 10, 5));
        assetPanel.add(assetTitleLabel);
        assetPanel.add(new JLabel(""));
        assetPanel.add(assetIDLabel);
        assetPanel.add(assetIDTextField);
        assetPanel.add(assetTypeLabel);
        assetPanel.add(assetTypeTextField);
        assetPanel.add(assetDescriptionLabel);
        assetPanel.add(assetDescriptionTextArea);

        assetPanel.add(addButton);
        assetPanel.add(editButton);
        assetPanel.add(deleteButton);
        assetPanel.add(duplicateCheckbox);

        JPanel maintenancePanel = new JPanel();
        maintenancePanel.setLayout(new GridLayout(4, 2, 10, 5));
        maintenancePanel.add(maintenanceTitleLabel);
        maintenancePanel.add(new JLabel(""));
        maintenancePanel.add(maintenanceAssetIDLabel);
        maintenancePanel.add(maintenanceAssetIDTextField);
        maintenancePanel.add(maintenanceDetailsLabel);
        maintenancePanel.add(maintenanceDetailsTextArea);
        maintenancePanel.add(submitMaintenanceStatusButton); // Updated button name

        add(assetPanel, BorderLayout.NORTH);
        add(maintenancePanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.SOUTH);
        topPanel.add(viewFileDataButton);
        add(topPanel, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        submitMaintenanceStatusButton.addActionListener(this); // Updated button name

        JFrame frame = new JFrame("Admin Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);  // Remove the creation of a new instance
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String assetID = assetIDTextField.getText();
            String assetType = assetTypeTextField.getText();
            String assetDescription = assetDescriptionTextArea.getText();
            if (SubstringChecker(assetID)) {

                showErrorMessage("Asset with the given ID already exists.");
                return;

            } else {
                showAddingCostDialog();
            }

        } else if (e.getSource() == editButton) {
            String assetID = assetIDTextField.getText();
            String assetType = assetTypeTextField.getText();
            String assetDescription = assetDescriptionTextArea.getText();

            assetMap.put(assetID, assetType + "," + assetDescription);
            showSuccessMessage("Asset updated successfully.");
            updateFile(assetID, assetType, assetDescription);
        } else if (e.getSource() == deleteButton) {
            String assetID = assetIDTextField.getText();
            if (!SubstringChecker(assetID)) {

                showErrorMessage("Asset with the given ID don't exists.");
                return;

            } else {

                assetMap.remove(assetID);
                assetList.remove(assetID);
                showSuccessMessage("Asset deleted successfully.");
                deleteFile(assetID);
            }
       

 } else if (e.getSource() == submitMaintenanceStatusButton) { // Updated button name
            String assetID = maintenanceAssetIDTextField.getText();
            String maintenanceDetails = maintenanceDetailsTextArea.getText();
            // Check if asset ID and maintenance details are filled
            if (!assetID.isEmpty() || !maintenanceDetails.isEmpty()) {
                if (!SubstringChecker(assetID)) {

                    showErrorMessage("Asset with the given ID Don't exists.");
                    return;

                } else {
                    showMaintenanceStatusOptions(assetID, maintenanceDetails);
                }
            } else {
                showErrorMessage("Please fill in Asset ID and Maintenance Details.");
                return;
            }

        } else if (e.getSource() == logoutButton) {
            openLoginApp();
        } else if (e.getSource() == viewFileDataButton) {
            displayFileData();
        }
    }

    private void showAddingCostDialog() {
        if (addingCostDialog == null) {
            addingCostDialog = new AddingCostDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                    assetIDTextField.getText(),
                    assetTypeTextField.getText(),
                    assetDescriptionTextArea.getText(), this); // Pass the reference to AdminManagementSystem
        }
        addingCostDialog.setVisible(true);
    }

    private void showMaintenanceStatusOptions(String assetID, String maintenanceDetails) {
        String[] options = {"Maintenance Complete", "Maintenance Incomplete"};
        int choice = JOptionPane.showOptionDialog(this,
                "Choose Maintenance Status",
                "Maintenance Status",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            showMaintenanceCostInput(assetID, maintenanceDetails);
        } else if (choice == 1) {
            showMaintenanceDueMessage();
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

    public void handleAddingCost(String assetID, String assetType, String assetDescription, String additionalCost) {
        // Process the additionalCost as needed
        writeAssetInfoToFileWithCost(assetID, assetType, assetDescription, additionalCost);
        // You can store it, use it, or perform additional actions
        showSuccessMessage("Asset added successfully.");
    }

    private void showMaintenanceCostInput(String assetID, String maintenanceDetails) {
        String maintenanceCost = JOptionPane.showInputDialog("Enter Maintenance Cost:");

        if (maintenanceCost != null) {
            maintenanceMap.put(assetID, maintenanceDetails + "," + maintenanceCost);
            showSuccessMessage("Maintenance Data submitted successfully.");
            updatetheFile(assetID, maintenanceDetails, maintenanceCost);
        } else {
            showErrorMessage("Please enter Maintenance Cost.");
        }
    }

    private void openLoginApp() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.dispose();
        new LogoutApp();
    }

    private void updatetheFile(String a, String b, String cost) {
        File file = new File("assetData.txt");
        StringBuilder content = new StringBuilder();
        String line2 = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line2 = "ASSET_ID- " + a + " || MAINTENANCE_DESCRIPTION- " + b + " || MAINTENANCE_COST- " + cost;
                content.append(line).append("\n");
            }
            line2 = "ASSET_ID- " + a + " ||  MAINTENANCE_DESCRIPTION- " + b + " ||  MAINTENANCE_COST- " + cost;
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

    private void writeUpdatesToFile(String updateData) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("updates.txt", true));
            writer.write(updateData);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            showErrorMessage("Error writing updates to file.");
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
            JOptionPane.showMessageDialog(this, scrollPane, "File Data", JOptionPane.PLAIN_MESSAGE);
        } catch (FileNotFoundException ex) {
            showErrorMessage("File not found or cannot be accessed.");
        }
    }

    private void writeToFile(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("assetData.txt", true));
            writer.write(data);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            showErrorMessage("Error writing to file.");
        }
    }

    private void deleteFile(String assetID) {
        File file = new File("assetData.txt");
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ASSET_ID - " + assetID)) {
                    content.append(line).append("\n");
                    content.append(line).append(" || DELETED").append("\n");
                } else {
                    content.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFile(String assetID, String assetType, String assetDescription) {
        File file = new File("assetData.txt");
        StringBuilder content = new StringBuilder();
        String line2;
        line2 = "ASSET_ID - " + assetID + " ||    ASSET_TYPE- " + assetType + " ||     ASSET_INFO- " + assetDescription;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            content.append(line2).append(" || EDITED").append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMaintenanceDueMessage() {
        JOptionPane.showMessageDialog(this, "Maintenance is due.", "Maintenance Status", JOptionPane.INFORMATION_MESSAGE);
    }

    private void writeAssetInfoToFileWithCost(String assetID, String assetType, String assetDescription, String additionalCost) {
        String data = "ASSET_ID - " + assetID +
                " || ASSET_TYPE - " + assetType +
                " || ASSET_INFO - " + assetDescription +
                " || ADDITION_COST - " + additionalCost;
        writeToFile(data);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Admin Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new AdminManagementSystem());
            frame.pack();
            frame.setVisible(true);
        });
    }

class AddingCostDialog extends JDialog implements ActionListener {
    private JTextField addingCostTextField;
    private JButton okButton, cancelButton;
    private String assetID, assetType, assetDescription; // New fields to store the values
    private AdminManagementSystem adminManagementSystem; // Added reference to the main class

    public AddingCostDialog(JFrame parentFrame, String assetID, String assetType, String assetDescription, AdminManagementSystem adminManagementSystem) {
        super(parentFrame, "Enter Adding Cost", true);

        this.assetID = assetID; // Store the values
        this.assetType = assetType;
        this.assetDescription = assetDescription;
        this.adminManagementSystem = adminManagementSystem; // Store the reference

        addingCostTextField = new JTextField(10);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Adding Cost:"));
        panel.add(addingCostTextField);
        panel.add(okButton);
        panel.add(cancelButton);

        setLayout(new FlowLayout());
        add(panel);

        pack();
        setLocationRelativeTo(parentFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            String addingCost = addingCostTextField.getText();
            if (addingCost.isEmpty()) {
                showErrorMessage("Please enter an additional cost.");
            }
            else{
            // Process the addingCost as needed
            writeAssetInfoToFileWithCost(assetID, assetType, assetDescription, addingCost);
            // You can store it, use it, or perform additional actions
            // For example, you can call a method in AdminManagementSystem to handle the addingCost
            // adminManagementSystem.handleAddingCost(addingCost);
            setVisible(false);
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false);
        }
    }
    // Inside AdminManagementSystem class
    public void handleAddingCost(String assetID, String assetType, String assetDescription, String additionalCost) {
        // Process the additionalCost as needed
        writeAssetInfoToFileWithCost(assetID, assetType, assetDescription, additionalCost);
        // You can store it, use it, or perform additional actions
        showSuccessMessage("Asset added successfully.");
        }   
    }
}