import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AccessOperation extends JFrame {
    public static boolean alreadyAccessed = false;
    JFrame frame;
    JLabel label;
    JTextField textField;
    JButton forwardButton;
    JButton backButton;

    String clazzToAccess;

    BankOperations bankOperations = new BankOperations(EntryPage.currentUserId);
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(backButton)){
                try {
                    new HomePage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else if (e.getActionCommand().equals("Enter")){
                String currentID = EntryPage.currentUserId;
                String currentPIN = bankOperations.getAccountByID(currentID).getUserPIN();
                if (currentPIN.equals(textField.getText())){
                    try {
                        switch (clazzToAccess){
                            case "Balance":
                                alreadyAccessed = true;
                                new Balance();
                                frame.dispose();
                                break;
                            case "ChangePIN":
                                alreadyAccessed = true;
                                new ChangePIN();
                                frame.dispose();
                                break;
                            case "Deposit":
                                alreadyAccessed = true;
                                new Deposit();
                                frame.dispose();
                                break;
                            case "Withdrawal":
                                alreadyAccessed = true;
                                new Withdrawal();
                                frame.dispose();
                                break;
                            case "FastCash":
                                alreadyAccessed = true;
                                new FastCash();
                                frame.dispose();
                                break;
                            case "Transfer":
                                alreadyAccessed = true;
                                new Transfer();
                                frame.dispose();
                                break;

                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(frame, "Wrong PIN code");
                }
            }
        }
    };

    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);

    public AccessOperation(String clazzToAccess) throws IOException {
        frame = new JFrame();
        frame.setSize(700, 450);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        label = new JLabel("Please enter your PIN");
        label.setFont(myFont);
        label.setBounds(190, 50, 400, 50);
        frame.add(label);

        textField = new JTextField();
        textField.setBounds(230, 120, 200, 50);
        textField.setEditable(true);
        textField.setFont(myFont);
        frame.add(textField);

        forwardButton = new JButton("Enter");
        forwardButton.setBounds(255, 200, 150, 40);
        forwardButton.setFont(myFont);
        forwardButton.addActionListener(actionListener);
        frame.add(forwardButton);

        backButton = new JButton("Back");
        backButton.setBounds(10, 360 , 150, 40);
        backButton.setFont(myFont);
        backButton.addActionListener(actionListener);
        frame.add(backButton);

        this.clazzToAccess = clazzToAccess;

        frame.setVisible(true);
    }

    // public void giveAcces() throws IOException {
    //     String realName = clazzName.split("\\W")[0];
    //     switch (realName){
    //         case "BalanceOptionPageFrame":
    //             new BalanceOptionPageFrame();
    //             break;
    //         case "ChangePINFrame":
    //             break;
    //     }
    // }
}


