package ch.fhnw.justme.messages.startprocess;

import ch.fhnw.justme.model.FormVariables;

public class StartProcessFormRequest {

    private FormVariables variables;
    private boolean withVariablesInReturn;

    public StartProcessFormRequest() {
    }

    public StartProcessFormRequest(FormVariables variables, boolean withVariablesInReturn) {
        this.variables = variables;
        this.withVariablesInReturn = withVariablesInReturn;
    }

    public FormVariables getVariables() {
        return variables;
    }

    public void setVariables(FormVariables variables) {
        this.variables = variables;
    }

    public boolean getWithVariablesInReturn() {
        return withVariablesInReturn;
    }

    public void setWithVariablesInReturn(boolean withVariablesInReturn) {
        this.withVariablesInReturn = withVariablesInReturn;
    }

    @Override
    public String toString() {
        return "StartProcessFormRequest{" +
                "variables=" + variables +
                ", withVariablesInReturn=" + withVariablesInReturn +
                '}';
    }
}
