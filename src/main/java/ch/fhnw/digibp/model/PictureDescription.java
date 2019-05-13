package ch.fhnw.digibp.model;

public class PictureDescription {
    private String url;
    private String description;
    private Double price;
    private String partner;

    public PictureDescription(String url, String description, Double price, String partner) {
        this.url = url;
        this.description = description;
        this.price = price;
        this.partner = partner;
    }

    public PictureDescription() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "PictureDescription{" +
                "url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", partner='" + partner + '\'' +
                '}';
    }
}