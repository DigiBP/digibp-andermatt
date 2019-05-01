package ch.fhnw.justme.model;

import java.io.Serializable;

public class PictureDescription implements Serializable {
    private String url;
    private String description;
    private Double price;

    public PictureDescription() {
    }

    public PictureDescription(String url, String description, Double price) {
        this.url = url;
        this.description = description;
        this.price = price;
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

    @Override
    public String toString() {
        return "PictureDescription{" +
                "url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}