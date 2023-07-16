import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Transfer extends JFrame {

    JFrame frame;
    private JPanel mainPanel;
    private JTextField cardNumber;
    private JTextField amountOfTransaction;

    private JButton returnCardButton;
    private JButton backButton;
    private JButton transferButton;

    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);
    String currentUserID = EntryPage.currentUserId;
    BankOperations bankOperations = new BankOperations(currentUserID);

    String receiverID;

    public Transfer() throws IOException {
        frame = new JFrame();
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardNumber = new JTextField("card number");
        cardNumber.setBounds(50, 100, 300, 50);
        cardNumber.setFont(myFont);
        cardNumber.setEditable(true);
        frame.add(cardNumber);

        amountOfTransaction = new JTextField("amount of transaction");
        amountOfTransaction.setBounds(50, 180, 300, 50);
        amountOfTransaction.setFont(myFont);
        amountOfTransaction.setEditable(true);
        frame.add(amountOfTransaction);

        transferButton = new JButton("Transfer");
        transferButton.setBounds(380, 130, 200, 60);
        transferButton.setFocusable(false);
        transferButton.setFont(myFont);
        transferButton.addActionListener(actionListener);
        frame.add(transferButton);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBounds(50, 50, 600, 250);
        frame.add(mainPanel);

        returnCardButton = new JButton("Return Card");
        returnCardButton.setBounds(470, 350, 200, 50);
        returnCardButton.setFocusable(false);
        returnCardButton.setFont(myFont);
        returnCardButton.addActionListener(actionListener);
        frame.add(returnCardButton);

        backButton = new JButton("Back");
        backButton.setBounds(30, 350, 200, 50);
        backButton.setFocusable(false);
        backButton.setFont(myFont);
        backButton.addActionListener(actionListener);
        frame.add(backButton);

        frame.setVisible(true);
    }
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(backButton)){
                try {
                    new HomePage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            }else if (e.getSource().equals(returnCardButton)){
                try {
                    new EntryPage();
                    AccessOperation.alreadyAccessed = false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            }else if (e.getSource().equals(transferButton)){
                receiverID = cardNumber.getText();
                double currentReceiverBalance = bankOperations.getAccountByID(receiverID).getBalance();
                double amount = Double.parseDouble(amountOfTransaction.getText());
                double receiverNewBalance = currentReceiverBalance + amount;
                bankOperations.getAccountByID(receiverID).setBalance(receiverNewBalance);

                double currentUserBalance = bankOperations.getAccountByID(currentUserID).getBalance();
                double currentUserNewBalance = currentUserBalance - amount;
                bankOperations.getAccountByID(currentUserID).setBalance(currentUserNewBalance);
                automatedUpdate(receiverNewBalance, currentUserNewBalance);
            }
        }
    };
    private void update(double receiverNewBalance, double currentUserNewBalance) throws IOException {
        String filePath = "D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\Data.txt";
        String secondPath = "D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\newData.txt";
        File inputFile = new File(filePath);
        File tempFile = new File(secondPath);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(secondPath));

        String iDtoFix = receiverID;
        String receiverPIN = bankOperations.getAccountByID(receiverID).getUserPIN();

        String idToFix2 = currentUserID;
        String currentUserPIN = bankOperations.getAccountByID(currentUserID).getUserPIN();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String currentID = currentLine.split("\\s+")[0];
            if(currentID.equals(iDtoFix)){
                String tempLine = String.format("%s %s %f\n",currentID, receiverPIN, receiverNewBalance);
                writer.write(tempLine);
                continue;
            }else if (currentID.equals(idToFix2)){
                String tempLine = String.format("%s %s %f\n", currentID, currentUserPIN, currentUserNewBalance);
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
    private void automatedUpdate(double receiverNewBalance, double currentUserNewBalance ){
        try {
            update(receiverNewBalance, currentUserNewBalance);
            JOptionPane.showMessageDialog(frame, "Operation complete successfully");
            new HomePage();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        frame.dispose();
    }
}
