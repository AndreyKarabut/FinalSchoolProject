package user;

public class User {
    int id;
    float balance;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance){
        this.balance = balance;
    }
}
