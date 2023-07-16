import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class ChangePIN extends JFrame {

    JFrame frame;
    private JPanel mainPanel;
    private JTextField currentPIN;
    private JTextField newPIN;

    private JButton returnCardButton;
    private JButton backButton;
    private JButton changeButton;

    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);

    String currentUserID = EntryPage.currentUserId;
    BankOperations bankOperations = new BankOperations(currentUserID);
    String accCurrentPIN = bankOperations.getAccountByID(currentUserID).getUserPIN();

    public ChangePIN() throws IOException {
        frame = new JFrame();
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        currentPIN = new JTextField("Type current PIN");
        currentPIN.setBounds(50, 100, 300, 50);
        currentPIN.setFont(myFont);
        currentPIN.setEditable(true);
        frame.add(currentPIN);

        newPIN = new JTextField("Type new PIN");
        newPIN.setBounds(50, 180, 300, 50);
        newPIN.setFont(myFont);
        newPIN.setEditable(true);
        frame.add(newPIN);

        changeButton = new JButton("Change PIN");
        changeButton.setBounds(380, 130, 200, 60);
        changeButton.setFocusable(false);
        changeButton.setFont(myFont);
        changeButton.addActionListener(listener);
        frame.add(changeButton);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBounds(50, 50, 600, 250);
        frame.add(mainPanel);

        returnCardButton = new JButton("Return Card");
        returnCardButton.setBounds(470, 350, 200, 50);
        returnCardButton.setFocusable(false);
        returnCardButton.setFont(myFont);
        returnCardButton.addActionListener(listener);
        frame.add(returnCardButton);

        backButton = new JButton("Back");
        backButton.setBounds(30, 350, 200, 50);
        backButton.setFocusable(false);
        backButton.setFont(myFont);
        backButton.addActionListener(listener);
        frame.add(backButton);

        frame.setVisible(true);
    }

    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(backButton)) {
                try {
                    new HomePage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            } else if (e.getSource().equals(returnCardButton)) {
                try {
                    new EntryPage();
                    AccessOperation.alreadyAccessed = false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            } else if (e.getActionCommand().equals("Change PIN")) {
                if (currentPIN.getText().equals(accCurrentPIN)) {
                    bankOperations.changePIN(newPIN.getText());
                    JOptionPane.showMessageDialog(frame, "Operation complete successfully");
                    try {
                        update(newPIN.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Wrong PIN code");
                }
            }
        }
    };
    private void update(String toReplacePIN) throws IOException {
        String filePath = "D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\Data.txt";
        String secondPath = "D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\newData.txt";
        File inputFile = new File(filePath);
        File tempFile = new File(secondPath);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(secondPath));

        String iDtoFix = currentUserID;
        double accBalance = bankOperations.getAccountByID(currentUserID).getBalance();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String currentID = currentLine.split("\\s+")[0];
            if(currentID.equals(iDtoFix)){
                String tempLine = String.format("%s %s %f\n",currentID, toReplacePIN, accBalance);
                writer.write(tempLine);
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean deleted = inputFile.delete();
        boolean successfully = tempFile.renameTo(
                new File("D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\Data.txt"));
    }
}


