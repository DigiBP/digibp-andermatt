package ch.fhnw.justme.model;

public class SuggestionsFormVariables implements FormVariables {

    private Variable color;
    private Variable clothing;

    public SuggestionsFormVariables() {
    }

    public SuggestionsFormVariables(Variable color, Variable clothing) {
        this.color = color;
        this.clothing = clothing;
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

    @Override
    public String toString() {
        return "SuggestionsFormVariables{" +
                "color=" + color +
                ", clothing=" + clothing +
                '}';
    }
}
