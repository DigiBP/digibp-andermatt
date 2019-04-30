package ch.fhnw.justme.messages.startprocess;

import ch.fhnw.justme.model.FormVariables;

public class StartProcessFormRequest {

    FormVariables variables;

    public StartProcessFormRequest() {
    }

    public StartProcessFormRequest(FormVariables variables) {
        this.variables = variables;
    }

    public FormVariables getVariables() {
        return variables;
    }

    public void setVariables(FormVariables variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "StartProcessFormRequest{" +
                "variables=" + variables +
                '}';
    }
}
