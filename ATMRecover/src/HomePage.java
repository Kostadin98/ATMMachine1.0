import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class HomePage extends JFrame {
    private JFrame frame;
    private JLabel generalLabel;

    private JButton withdrawal;
    private JButton balance;
    private JButton transfer;
    private JButton changePin;
    private JButton deposit;
    private JButton fastCash;
    private JButton logout;

    private ActionListener operationListener = new OperationListener();

    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);

    BankOperations bankOperations = new BankOperations(EntryPage.currentUserId);

    public HomePage() throws IOException {
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

        withdrawal = new JButton("Withdrawal");
        withdrawal.setBounds(0, 100, 200, 40);
        withdrawal.setFont(myFont);
        withdrawal.addActionListener(operationListener);
        frame.add(withdrawal);

        fastCash = new JButton("Fast Cash");
        fastCash.setBounds(0, 200, 200, 40);
        fastCash.setFont(myFont);
        fastCash.addActionListener(operationListener);
        frame.add(fastCash);


        transfer = new JButton("Transfer");
        transfer.setBounds(0, 300, 200, 40);
        transfer.setFont(myFont);
        transfer.addActionListener(operationListener);
        frame.add(transfer);

        changePin = new JButton("Change PIN");
        changePin.setBounds(500, 100, 200, 40);
        changePin.setFont(myFont);
        changePin.addActionListener(operationListener);
        frame.add(changePin);

        deposit = new JButton("Deposit");
        deposit.setBounds(500, 200, 200, 40);
        deposit.setFont(myFont);
        deposit.addActionListener(operationListener);
        frame.add(deposit);

        balance = new JButton("Balance");
        balance.setBounds(500, 300, 200, 40);
        balance.setFont(myFont);
        balance.addActionListener(operationListener);
        frame.add(balance);

        logout = new JButton("Logout");
        logout.setBounds(250, 370, 200, 40);
        logout.setFont(myFont);
        logout.addActionListener(operationListener);
        frame.add(logout);

        frame.setVisible(true);
    }

    private class OperationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Logout":
                    try {
                        new EntryPage();
                        AccessOperation.alreadyAccessed = false;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    break;
                case "Balance":
                    try {
                        if (AccessOperation.alreadyAccessed){
                            new Balance();
                        }else {
                            new AccessOperation("Balance");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    break;
                case "Change PIN":
                    try {
                        if (AccessOperation.alreadyAccessed) {
                            new ChangePIN();
                        }else {
                            new AccessOperation("ChangePIN");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    break;
                case "Deposit":
                    try {
                        if (AccessOperation.alreadyAccessed){
                            new Deposit();
                        }else {
                            new AccessOperation("Deposit");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    break;
                case "Withdrawal":
                    try{
                        if (AccessOperation.alreadyAccessed){
                            new Withdrawal();
                        }else {
                            new AccessOperation("Withdrawal");
                        }
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    break;
                case "Fast Cash":
                    try{
                        if(AccessOperation.alreadyAccessed){
                            new FastCash();
                        }else {
                            new AccessOperation("FastCash");
                        }
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    break;
                case "Transfer":
                    try{
                        if (AccessOperation.alreadyAccessed){
                            new Transfer();
                        }else {
                            new AccessOperation("Transfer");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    break;
            }
        }
    }
    private Account findCurrentUser(String currentUser){
        List<Account> accountList = bankOperations.getAccountList();
        for (Account acc: accountList) {
            if (acc.getUserId().equals(currentUser)){
                return acc;
            }
        }
        return null;
    }
}
