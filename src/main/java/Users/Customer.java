package Users;

public class Customer {

    private String name;
    private String email;
    private String passwordHash;
    private Double balance;



    public Customer(String name, String email, String passwordHash, Double balance) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
    }

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
