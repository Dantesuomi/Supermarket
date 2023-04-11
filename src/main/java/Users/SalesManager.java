package Users;

public class SalesManager {

    private String name;
    private String email;
    private String passwordHash;
    private Double shopBalance;

    public SalesManager(String name, String email, String passwordHash, Double shopBalance) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.shopBalance = shopBalance;
    }

    public SalesManager() {
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String PasswordHash) {
        this.passwordHash = passwordHash;
    }

    public Double getShopBalance() {
        return shopBalance;
    }

    public void setShopBalance(Double shopBalance) {
        this.shopBalance = shopBalance;
    }

}
