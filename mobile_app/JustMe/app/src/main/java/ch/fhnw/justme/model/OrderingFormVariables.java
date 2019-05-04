package ch.fhnw.justme.model;

public class OrderingFormVariables implements FormVariables {
    public Variable cardNumber;
    public Variable totalAmount;
    public Variable partnerName;
    public ListVariable<PictureDescription> cart;

    public OrderingFormVariables() {
    }

    public OrderingFormVariables(Variable cardNumber, Variable totalAmount, Variable partnerName, ListVariable<PictureDescription> cart) {
        this.cardNumber = cardNumber;
        this.totalAmount = totalAmount;
        this.partnerName = partnerName;
        this.cart = cart;
    }

    public Variable getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Variable cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Variable getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Variable totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Variable getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(Variable partnerName) {
        this.partnerName = partnerName;
    }

    public ListVariable<PictureDescription> getCart() {
        return cart;
    }

    public void setCart(ListVariable<PictureDescription> cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "OrderingFormVariables{" +
                "cardNumber=" + cardNumber +
                ", totalAmount=" + totalAmount +
                ", partnerName=" + partnerName +
                ", cart=" + cart +
                '}';
    }
}

