package ch.fhnw.justme.messages.getvariable;

import ch.fhnw.justme.model.Variable;

public class GetVariableResponse {
    Variable readyForPickup;
    Variable possibilities;
    Variable producer;
    Variable partner;

    public GetVariableResponse() {
    }

    public GetVariableResponse(Variable readyForPickup, Variable possibilities, Variable producer, Variable partner) {
        this.readyForPickup = readyForPickup;
        this.possibilities = possibilities;
        this.producer = producer;
        this.partner = partner;
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

    public Variable getProducer() {
        return producer;
    }

    public void setProducer(Variable producer) {
        this.producer = producer;
    }

    public Variable getPartner() {
        return partner;
    }

    public void setPartner(Variable partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "GetVariableResponse{" +
                "readyForPickup=" + readyForPickup +
                ", possibilities=" + possibilities +
                ", producer=" + producer +
                ", partner=" + partner +
                '}';
    }
}
