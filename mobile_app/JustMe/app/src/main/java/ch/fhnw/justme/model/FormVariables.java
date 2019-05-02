package ch.fhnw.justme.model;

public class FormVariables {

    private Variable color;
    private Variable clothing;

    public FormVariables() {
    }

    public FormVariables(Variable color, Variable clothing) {
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
        return "FormVariables{" +
                "color=" + color +
                ", clothing=" + clothing +
                '}';
    }
}
