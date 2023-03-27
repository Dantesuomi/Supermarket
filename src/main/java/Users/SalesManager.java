package Users;

public class SalesManager {

    private String name;
    private String email;
    private String password;
    private Double shopBalance;

    public SalesManager(String name, String email, String password, Double shopBalance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.shopBalance = shopBalance;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getShopBalance() {
        return shopBalance;
    }

    public void setShopBalance(Double shopBalance) {
        this.shopBalance = shopBalance;
    }

    @Override
    public String toString() {
        return "SalesManager{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", shopBalance=" + shopBalance +
                '}';
    }
}
