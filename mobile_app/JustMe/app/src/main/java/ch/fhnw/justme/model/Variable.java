package ch.fhnw.justme.model;

public class Variable {
    String value;

    public Variable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "value='" + value + '\'' +
                '}';
    }
}
