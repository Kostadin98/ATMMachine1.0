import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FastCash {

    private JFrame frame;
    private JLabel generalLabel;

    private JButton firstSum;
    private JButton secondSum;
    private JButton thirdSum;
    private JButton fourthSum;
    private JButton fifthSum;
    private JButton backButton;

    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);
    String currentID = EntryPage.currentUserId;
    BankOperations bankOperations = new BankOperations(currentID);
    public FastCash() throws IOException {

        frame = new JFrame();
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        generalLabel = new JLabel("Choose an option");
        generalLabel.setBounds(215, 25, 300, 50);
        generalLabel.setFont(myFont);
        frame.add(generalLabel);

        firstSum = new JButton("20");
        firstSum.setBounds(0, 100, 200, 40);
        firstSum.setFont(myFont);
        firstSum.addActionListener(actionListener);
        frame.add(firstSum);

        secondSum = new JButton("50");
        secondSum.setBounds(0, 200, 200, 40);
        secondSum.setFont(myFont);
        secondSum.addActionListener(actionListener);
        frame.add(secondSum);

        thirdSum = new JButton("100");
        thirdSum.setBounds(0, 300, 200, 40);
        thirdSum.setFont(myFont);
        thirdSum.addActionListener(actionListener);
        frame.add(thirdSum);

        fourthSum = new JButton("200");
        fourthSum.setBounds(500, 100, 200, 40);
        fourthSum.setFont(myFont);
        fourthSum.addActionListener(actionListener);
        frame.add(fourthSum);

        fifthSum = new JButton("400");
        fifthSum.setBounds(500, 200, 200, 40);
        fifthSum.setFont(myFont);
        fifthSum.addActionListener(actionListener);
        frame.add(fifthSum);

        backButton = new JButton("Back");
        backButton.setBounds(250, 370, 200, 40);
        backButton.setFont(myFont);
        backButton.addActionListener(actionListener);
        frame.add(backButton);

        frame.setVisible(true);
    }

    ActionListener actionListener = new ActionListener() {
        double withdrawalAmount = 0.0;
        double currentBalance = bankOperations.getAccountByID(currentID).getBalance();
        double newBalance = 0.0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(firstSum)) {
                withdrawalAmount = 20;
                newBalance = currentBalance - withdrawalAmount;
                bankOperations.getAccountByID(currentID).setBalance(newBalance);
                automatedUpdate(newBalance);
            }else if (e.getSource().equals(secondSum)){
                withdrawalAmount = 50;
                newBalance = currentBalance - withdrawalAmount;
                bankOperations.getAccountByID(currentID).setBalance(newBalance);
                automatedUpdate(newBalance);
            }else if (e.getSource().equals(thirdSum)){
                withdrawalAmount = 100;
                newBalance = currentBalance - withdrawalAmount;
                bankOperations.getAccountByID(currentID).setBalance(newBalance);
                automatedUpdate(newBalance);
            }else if (e.getSource().equals(fourthSum)){
                withdrawalAmount = 200;
                newBalance = currentBalance - withdrawalAmount;
                bankOperations.getAccountByID(currentID).setBalance(newBalance);
                automatedUpdate(newBalance);
            }else if (e.getSource().equals(fifthSum)){
                withdrawalAmount = 400;
                newBalance = currentBalance - withdrawalAmount;
                bankOperations.getAccountByID(currentID).setBalance(newBalance);
                automatedUpdate(newBalance);
            } else if (e.getSource().equals(backButton)){
                try {
                    new HomePage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };
    private void automatedUpdate(double newBalance){
        try {
            update(newBalance);
            JOptionPane.showMessageDialog(frame, "Operation complete successfully");
            new HomePage();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        frame.dispose();
    }
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