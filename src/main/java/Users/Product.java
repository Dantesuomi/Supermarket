package Users;

public class Product {
    //total quantity(weight or number, reduces when product is bought),

    private String name;
    private Double availableQuantity;
    private Double unitSize;
    private Double purchasePrice;
    private Double retailPrice;

    public Product(String name, Double totalQuantity, Double unitSize, Double purchasePrice, Double retailPrice) {
        this.name = name;
        this.availableQuantity = totalQuantity;
        this.unitSize = unitSize;
        this.purchasePrice = purchasePrice;
        this.retailPrice = retailPrice;
    }
    public Product(int product_id, double amount_sold){
        
    }

    public Product() {
    }

    public Product( Product product){
        this.name = product.name;
        this.availableQuantity = product.availableQuantity;
        this.unitSize = product.unitSize;
        this.purchasePrice = product.purchasePrice;
        this.retailPrice = product.retailPrice;
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

    public Double getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(Double unitSize) {
        this.unitSize = unitSize;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Override
    public String toString() {
        return "Product: " + this.getName() + ", price: " + this.getRetailPrice() + ", available amount: " + this.getAvailableQuantity();
    }
}
