package ch.fhnw.justme.model;

public class SuggestionsFormVariables implements FormVariables {

    private Variable color;
    private Variable clothing;
    private Variable sexAndSize;
    private Variable delivery;
    private Variable priceClass;
    private Variable occasion;

    public SuggestionsFormVariables() {
    }

    public SuggestionsFormVariables(Variable color, Variable clothing, Variable sexAndSize, Variable delivery, Variable priceClass, Variable occasion) {
        this.color = color;
        this.clothing = clothing;
        this.sexAndSize = sexAndSize;
        this.delivery = delivery;
        this.priceClass = priceClass;
        this.occasion = occasion;
    }

    public Variable getColor() {
        return color;
    }

    public void setColor(Variable color) {
        this.color = color;
    }

    public Variable getClothing() {
        return clothing;
    }

    public void setClothing(Variable clothing) {
        this.clothing = clothing;
    }

    public Variable getSexAndSize() {
        return sexAndSize;
    }

    public void setSexAndSize(Variable sexAndSize) {
        this.sexAndSize = sexAndSize;
    }

    public Variable getDelivery() {
        return delivery;
    }

    public void setDelivery(Variable delivery) {
        this.delivery = delivery;
    }

    public Variable getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(Variable priceClass) {
        this.priceClass = priceClass;
    }

    public Variable getOccasion() {
        return occasion;
    }

    public void setOccasion(Variable occasion) {
        this.occasion = occasion;
    }

    @Override
    public String toString() {
        return "SuggestionsFormVariables{" +
                "color=" + color +
                ", clothing=" + clothing +
                ", sexAndSize=" + sexAndSize +
                ", delivery=" + delivery +
                ", priceClass=" + priceClass +
                ", occasion=" + occasion +
                '}';
    }
}
