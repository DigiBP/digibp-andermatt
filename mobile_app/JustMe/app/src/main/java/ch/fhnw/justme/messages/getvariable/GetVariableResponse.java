package ch.fhnw.justme.messages.getvariable;

import ch.fhnw.justme.model.ListVariable;
import ch.fhnw.justme.model.Variable;

public class GetVariableResponse {
    Variable readyForPickup;
    ListVariable possibilities;

    //TODO: pictures


    public GetVariableResponse(Variable readyForPickup, ListVariable possibilities) {
        this.readyForPickup = readyForPickup;
        this.possibilities = possibilities;
    }

    public Variable getReadyForPickup() {
        return readyForPickup;
    }

    public void setReadyForPickup(Variable readyForPickup) {
        this.readyForPickup = readyForPickup;
    }

    public ListVariable getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(ListVariable possibilities) {
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
