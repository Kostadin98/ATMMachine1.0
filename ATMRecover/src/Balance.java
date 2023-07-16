import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Balance extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private JLabel fistLabel;
    private JLabel sumLabel;
    private JButton returnCardButton;
    private JButton backButton;


    private Font myFont = new Font("Ink Free", Font.ITALIC, 30);

    BankOperations bankOperations = new BankOperations(EntryPage.currentUserId);
    public Balance() throws IOException {
        bankOperations.setAccountList();

        frame = new JFrame();
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = getPanelOrganize(fistLabel, sumLabel);
        frame.add(panel);

        returnCardButton = new JButton("Return Card");
        returnCardButton.setBounds(470, 350, 200, 50);
        returnCardButton.setFocusable(false);
        returnCardButton.setFont(myFont);
        returnCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EntryPage();
                    AccessOperation.alreadyAccessed = false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            }
        });
        frame.add(returnCardButton);

        backButton = new JButton("Back");
        backButton.setBounds(30, 350, 200, 50);
        backButton.setFocusable(false);
        backButton.setFont(myFont);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new HomePage();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            }
        });
        frame.add(backButton);

        frame.setVisible(true);
    }

    public JPanel getPanelOrganize(JLabel fistLabel, JLabel sumLabel){
        JPanel maniPanel = new JPanel();
        maniPanel.setLayout(null);
        maniPanel.setBackground(Color.LIGHT_GRAY);
        maniPanel.setBounds(100, 50, 500, 200);

        fistLabel = new JLabel("Current balance inquiry:");
        fistLabel.setBounds(60, 20, 400, 50);
        fistLabel.setFont(myFont);
        maniPanel.add(fistLabel);

        double balance = bankOperations.balanceOperation(bankOperations.getAccountByID(bankOperations.currentUserId));
        sumLabel = new JLabel(balance + "");
        sumLabel.setBounds(180, 90, 200, 50);
        sumLabel.setFont(myFont);
        maniPanel.add(sumLabel);

        return maniPanel;
    }
}
