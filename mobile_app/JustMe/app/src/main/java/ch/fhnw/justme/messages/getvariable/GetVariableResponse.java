package ch.fhnw.justme.messages.getvariable;

import ch.fhnw.justme.model.Variable;

public class GetVariableResponse {
    Variable readyForPickup;
    Variable possibilities;


    public GetVariableResponse(Variable readyForPickup, Variable possibilities) {
        this.readyForPickup = readyForPickup;
        this.possibilities = possibilities;
    }

    public Variable getReadyForPickup() {
        return readyForPickup;
    }

    public void setReadyForPickup(Variable readyForPickup) {
        this.readyForPickup = readyForPickup;
    }

    public Variable getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(Variable possibilities) {
        this.possibilities = possibilities;
    }

    @Override
    public String toString() {
        return "GetVariableResponse{" +
                "readyForPickup=" + readyForPickup +
                ", possibilities=" + possibilities +
                '}';
    }
}
