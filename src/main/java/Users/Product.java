package Users;

public class Product {
    //total quantity(weight or number, reduces when product is bought),

    private String name;
    private Double totalQuantity;
    private Double itemQuantity;
    private Double purchasePrice;
    private Double sellingPrice;

    public Product(String name, Double totalQuantity, Double itemQuantity, Double purchasePrice, Double sellingPrice) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.itemQuantity = itemQuantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
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
                ", totalQuantity=" + totalQuantity +
                ", itemQuantity=" + itemQuantity +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
