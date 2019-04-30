package ch.fhnw.justme.messages.getvariable;

import ch.fhnw.justme.model.Variable;

public class GetVariableResponse {
    Variable readyForPickup;

    //TODO: pictures

    public GetVariableResponse(Variable readyForPickup) {
        this.readyForPickup = readyForPickup;
    }

    public Variable getReadyForPickup() {
        return readyForPickup;
    }

    public void setReadyForPickup(Variable readyForPickup) {
        this.readyForPickup = readyForPickup;
    }

    @Override
    public String toString() {
        return "GetVariableResponse{" +
                "readyForPickup=" + readyForPickup +
                '}';
    }
}
