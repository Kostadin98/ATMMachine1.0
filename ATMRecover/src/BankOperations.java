import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BankOperations {
    public List<Account> accountList;
    public BufferedReader bufferedReader;
    public Account account;
    public String currentUserId;


    public BankOperations(String userId) throws IOException {
        this.accountList = setAccountList();
        this.setCurrentUserId(userId);
    }
    public List<Account> setAccountList() throws IOException {
        File file = new File("D:\\User\\Desktop\\Java\\JavaOOP\\TestDrivenDevelopment\\RepeatTestDrivenDevelopmentLab\\ATMRecover\\src\\Data.txt");
        try {
            bufferedReader =
                    new BufferedReader(
                            new FileReader(file));

            String line = bufferedReader.readLine();
            accountList = new ArrayList<>();
            while (line != null) {
                String[] arrOFLineDetails = line.split("\\s+");
                String userId = arrOFLineDetails[0];
                String userPIN = arrOFLineDetails[1];
                String balanceAsString = arrOFLineDetails[2].replace(",", ".");
                double balance = Double.parseDouble(balanceAsString);
                account = new Account(userId, userPIN, balance);
                accountList.add(account);

                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!");
        } catch (IOException e) {
            System.out.println("Something wrong with the file...");
        }
        bufferedReader.close();
        return accountList;
    }
    public void setCurrentUserId(String currentUserId){
        this.currentUserId = currentUserId;
    }

    public List<Account> getAccountList(){
        return this.accountList;
    }

    public Account getAccountByID(String userId){
        for (Account acc: accountList) {
            if (acc.getUserId().equals(userId)){
                return acc;
            }
        }
        throw new IllegalStateException("No such userID");
    }

    public double balanceOperation (Account account){
        return account.getBalance();
    }

    public void changePIN(String newPIN){
        this.account.setUserPIN(newPIN);
    }
}
