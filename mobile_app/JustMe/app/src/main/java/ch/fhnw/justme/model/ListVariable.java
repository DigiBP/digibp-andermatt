package ch.fhnw.justme.model;

import java.util.List;

public class ListVariable<T> {
    private List<T> value;
    private ValueInfo valueInfo;

    public ListVariable() {
    }

    public ListVariable(List<T> value, ValueInfo valueInfo) {
        this.value = value;
        this.valueInfo = valueInfo;
    }

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }

    public ValueInfo getValueInfo() {
        return valueInfo;
    }

    public void setValueInfo(ValueInfo valueInfo) {
        this.valueInfo = valueInfo;
    }

    @Override
    public String toString() {
        return "ListVariable{" +
                "value=" + value +
                ", valueInfo=" + valueInfo +
                '}';
    }
}
