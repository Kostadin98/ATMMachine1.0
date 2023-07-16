import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.List;

public class EntryPage extends JFrame {
    public static String currentUserId;

    private JFrame frame;
    private JLabel label;
    private JTextField display;
    JButton entryButton;
    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);

    BankOperations bankOperations = new BankOperations(currentUserId);

    public EntryPage() throws IOException {
        bankOperations.setAccountList();

        frame = new JFrame();
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        label = new JLabel("Enter your card ID");
        label.setBounds(220, 50, 400, 50);
        label.setFont(myFont);
        frame.add(label);

        display = new JTextField();
        display.setBounds(150, 110, 400, 50);
        display.setFont(myFont);
        frame.add(display);

        entryButton = new JButton("Go To HomePage");
        entryButton.setBounds(200, 300, 300, 50);
        entryButton.setFont(new Font("Ink Free", Font.ITALIC, 20));
        entryButton.setFocusable(false);
        entryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Account> accountList = bankOperations.getAccountList();
                boolean accExists = false;

                for (Account account : accountList) {
                    if (account.getUserId().equals(display.getText())) {
                        currentUserId = account.getUserId();
                        accExists = true;
                        try {
                            HomePage homePage = new HomePage();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        frame.dispose();
                    }
                }

                if (!accExists) {
                    JFrame dialogWindow = new JFrame();
                    dialogWindow.setSize(300, 200);
                    JOptionPane.showMessageDialog(dialogWindow, "Incorrect ID", "Try Again", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        frame.add(entryButton);


        frame.setVisible(true);
    }
}

