package ch.fhnw.digibp.model;

import java.util.List;

public class ShoppingCart {
    List<PictureDescription> cart;

    public ShoppingCart() {
    }

    public ShoppingCart(List<PictureDescription> cart) {
        this.cart = cart;
    }

    public List<PictureDescription> getCart() {
        return cart;
    }

    public void setCart(List<PictureDescription> cart) {
        this.cart = cart;
    }
}
