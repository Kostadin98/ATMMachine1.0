public class Account {
    private String userId;
    private String userPIN;
    private double balance;

    public Account(String userId, String userPIN, double balance){
        this.userId = userId;
        this.userPIN = userPIN;
        this.balance =  balance;
    }

    public double getBalance(){
        return this.balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setUserPIN(String newPIN){
        this.userPIN = newPIN;
    }
    public String getUserPIN(){
        return this.userPIN;
    }
}
