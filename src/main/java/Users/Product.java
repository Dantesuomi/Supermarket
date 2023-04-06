package Users;

public class Product {
    //total quantity(weight or number, reduces when product is bought),

    private String name;
    private Double availableQuantity;
    private Double itemQuantity;
    private Double purchasePrice;
    private Double sellingPrice;

    public Product(String name, Double totalQuantity, Double itemQuantity, Double purchasePrice, Double sellingPrice) {
        this.name = name;
        this.availableQuantity = totalQuantity;
        this.itemQuantity = itemQuantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }
    public Product(int product_id, double amount_sold){
        
    }

    public Product(String text, String text1) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", totalQuantity=" + availableQuantity +
                ", itemQuantity=" + itemQuantity +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
