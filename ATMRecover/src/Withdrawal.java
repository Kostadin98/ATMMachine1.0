import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Withdrawal extends JFrame {

    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JButton forwardButton;
    private JButton backButton;

    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);
    String currentID = EntryPage.currentUserId;
    BankOperations bankOperations = new BankOperations(currentID);
    public Withdrawal() throws IOException {
        frame = new JFrame();
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        label = new JLabel("Please enter a number");
        label.setFont(myFont);
        label.setBounds(180, 50, 400,50 );
        frame.add(label);

        textField = new JTextField();
        textField.setBounds(220, 140, 200, 50);
        textField.setFont(myFont);
        textField.setEditable(true);
        frame.add(textField);

        forwardButton = new JButton("Withdrawal");
        forwardButton.setFont(myFont);
        forwardButton.setBounds(200, 220, 250, 40);
        forwardButton.addActionListener(actionListener);
        frame.add(forwardButton);

        backButton = new JButton("Back");
        backButton.setBounds(5, 370, 150, 40);
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
            }else if (e.getSource().equals(forwardButton)){
                double amountOfWithdrawal = Double.parseDouble(textField.getText());
                double currentBalance = bankOperations.getAccountByID(currentID).getBalance();
                bankOperations.getAccountByID(currentID).setBalance(currentBalance - amountOfWithdrawal);
                double newBalance = bankOperations.getAccountByID(currentID).getBalance();
                try {
                    update(newBalance);
                    JOptionPane.showMessageDialog(frame, "Operation complete successfully");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    private void update(double newBalance) throws IOException {
        String filePath = "D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\Data.txt";
        String secondPath = "D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\newData.txt";
        File inputFile = new File(filePath);
        File tempFile = new File(secondPath);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(secondPath));

        String iDtoFix = currentID;
        String userPIN = bankOperations.getAccountByID(currentID).getUserPIN();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String currentID = currentLine.split("\\s+")[0];
            if(currentID.equals(iDtoFix)){
                String tempLine = String.format("%s %s %f\n",currentID, userPIN, newBalance);
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
