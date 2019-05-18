package ch.fhnw.justme.model;

public class MeasurementsFormVariables implements FormVariables {
    Variable customerId;
    Variable neckCircumference;
    Variable shoulderLength;
    Variable chestCircumference;
    Variable underbustCircumference;
    Variable waistCircumference;
    Variable armLength;
    Variable hipCircumference;
    Variable wristCircumference;
    Variable waistToKnee;
    Variable height;

    public MeasurementsFormVariables() {
    }

    public MeasurementsFormVariables(Variable customerId, Variable neckCircumference, Variable shoulderLength, Variable chestCircumference, Variable underbustCircumference, Variable waistCircumference, Variable armLength, Variable hipCircumference, Variable wristCircumference, Variable waistToKnee, Variable height) {
        this.customerId = customerId;
        this.neckCircumference = neckCircumference;
        this.shoulderLength = shoulderLength;
        this.chestCircumference = chestCircumference;
        this.underbustCircumference = underbustCircumference;
        this.waistCircumference = waistCircumference;
        this.armLength = armLength;
        this.hipCircumference = hipCircumference;
        this.wristCircumference = wristCircumference;
        this.waistToKnee = waistToKnee;
        this.height = height;
    }

    public Variable getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Variable customerId) {
        this.customerId = customerId;
    }

    public Variable getNeckCircumference() {
        return neckCircumference;
    }

    public void setNeckCircumference(Variable neckCircumference) {
        this.neckCircumference = neckCircumference;
    }

    public Variable getShoulderLength() {
        return shoulderLength;
    }

    public void setShoulderLength(Variable shoulderLength) {
        this.shoulderLength = shoulderLength;
    }

    public Variable getChestCircumference() {
        return chestCircumference;
    }

    public void setChestCircumference(Variable chestCircumference) {
        this.chestCircumference = chestCircumference;
    }

    public Variable getUnderbustCircumference() {
        return underbustCircumference;
    }

    public void setUnderbustCircumference(Variable underbustCircumference) {
        this.underbustCircumference = underbustCircumference;
    }

    public Variable getWaistCircumference() {
        return waistCircumference;
    }

    public void setWaistCircumference(Variable waistCircumference) {
        this.waistCircumference = waistCircumference;
    }

    public Variable getArmLength() {
        return armLength;
    }

    public void setArmLength(Variable armLength) {
        this.armLength = armLength;
    }

    public Variable getHipCircumference() {
        return hipCircumference;
    }

    public void setHipCircumference(Variable hipCircumference) {
        this.hipCircumference = hipCircumference;
    }

    public Variable getWristCircumference() {
        return wristCircumference;
    }

    public void setWristCircumference(Variable wristCircumference) {
        this.wristCircumference = wristCircumference;
    }

    public Variable getWaistToKnee() {
        return waistToKnee;
    }

    public void setWaistToKnee(Variable waistToKnee) {
        this.waistToKnee = waistToKnee;
    }

    public Variable getHeight() {
        return height;
    }

    public void setHeight(Variable height) {
        this.height = height;
    }
}
