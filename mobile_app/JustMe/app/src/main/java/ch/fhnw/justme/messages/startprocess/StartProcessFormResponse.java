package ch.fhnw.justme.messages.startprocess;

import ch.fhnw.justme.model.FormVariables;

public class StartProcessFormResponse<T extends FormVariables> {

    private String id;
    private T variables;

    public StartProcessFormResponse(String id, T variables) {
        this.id = id;
        this.variables = variables;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getVariables() {
        return variables;
    }

    public void setVariables(T variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "StartProcessFormResponse{" +
                "id='" + id + '\'' +
                ", variables=" + variables +
                '}';
    }
}
