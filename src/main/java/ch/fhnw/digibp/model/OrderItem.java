package ch.fhnw.digibp.model;

public class OrderItem {
    private String orderId;
    private String url;
    private String description;
    private Double price;
    private Integer count;

    public OrderItem(String orderId, String url, String description, Double price, Integer count) {
        this.orderId = orderId;
        this.url = url;
        this.description = description;
        this.price = price;
        this.count = count;
    }

    public OrderItem() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
