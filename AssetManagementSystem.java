import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class AssetManagementSystem extends JPanel implements ActionListener {
    JLabel titleLabel, assetIDLabel, assetTypeLabel, assetDescriptionLabel;
    JTextField assetIDTextField, assetTypeTextField;
    JTextArea assetDescriptionTextArea;
    JButton addButton, editButton, deleteButton, logoutButton, viewDataButton; // Buttons
    JCheckBox checkbox;
    Map<String, String> assetMap = new HashMap<>();
    java.util.List<String> assetList = new ArrayList<>();
    

    public AssetManagementSystem() {
        titleLabel = new JLabel("College Asset Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
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
        checkbox = new JCheckBox("Check for duplicates before adding", true);
        viewDataButton = new JButton("View File Data");
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));

        logoutButton.addActionListener(this);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewDataButton.addActionListener(this);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 2));
        centerPanel.add(assetIDLabel);
        centerPanel.add(assetIDTextField);
        centerPanel.add(assetTypeLabel);
        centerPanel.add(assetTypeTextField);
        centerPanel.add(assetDescriptionLabel);
        centerPanel.add(assetDescriptionTextArea);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(checkbox);
        bottomPanel.add(viewDataButton);
        add(bottomPanel, BorderLayout.SOUTH);
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
                
            }
            else{
            assetMap.put(assetID, assetType + "," + assetDescription);
            assetList.add(assetID);
             writeToFile("ASSET_ID - "+assetID +" ||    ASSET_TYPE- " + assetType + " ||     ASSET_INFO- " + assetDescription);
            showSuccessMessage("Asset added successfully.");
            }

           
        } else if (e.getSource() == editButton) {
            // Code for editing an asset
            // ...
             String assetID = assetIDTextField.getText();
            String assetType = assetTypeTextField.getText();
            String assetDescription = assetDescriptionTextArea.getText();
            
                assetMap.put(assetID, assetType + "," + assetDescription);
                updateFile(assetID,assetType,assetDescription);

                showSuccessMessage("Asset updated successfully.");
            


        } else if (e.getSource() == deleteButton) {
            // Code for deleting an asset
            // ... 
            String assetID = assetIDTextField.getText();
            if (!SubstringChecker(assetID)) {
                
                    showErrorMessage("Asset with the given ID don't exists.");
                    return;
                
            }
            else{

            
                assetMap.remove(assetID);
                assetList.remove(assetID);
                showSuccessMessage("Asset deleted successfully.");
                deleteFile(assetID);
            }
            
          
        } else if (e.getSource() == logoutButton) {
            openLoginPage();
        } else if (e.getSource() == viewDataButton) {
            displayFileData();
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
            showErrorMessage("File not found or cannot be accessed.");
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
        String line2="";

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
        line2="ASSET_ID - "+assetID +" ||    ASSET_TYPE- " + assetType + " ||     ASSET_INFO- " + assetDescription;

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

    private void openLoginPage() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.dispose();

        // Open the LogoutApp to confirm logout
        new LogoutApp();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asset Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new AssetManagementSystem());
        frame.pack();
        frame.setVisible(true);
    }
}