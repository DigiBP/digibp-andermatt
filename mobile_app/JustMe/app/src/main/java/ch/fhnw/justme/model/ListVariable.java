package ch.fhnw.justme.model;

import java.util.List;

public class ListVariable {
    List<String> value;

    public ListVariable(List<String> value) {
        this.value = value;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ListVariable{" +
                "value=" + value +
                '}';
    }
}
