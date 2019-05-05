package ch.fhnw.justme.model;

public class NewCustomerFormVariables implements FormVariables {
    private Variable customerId;
    private Variable fullName;
    private Variable cardHolderName;
    private Variable cardNumber;
    private Variable cvc;
    private Variable yy;
    private Variable mm;
    private Variable addressLineOne;
    private Variable addressLineTwo;
    private Variable postcode;
    private Variable town;

    public NewCustomerFormVariables() {
    }

    public NewCustomerFormVariables(Variable customerId, Variable fullName, Variable cardHolderName, Variable cardNumber, Variable cvc, Variable yy, Variable mm, Variable addressLineOne, Variable addressLineTwo, Variable postcode, Variable town) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.yy = yy;
        this.mm = mm;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.postcode = postcode;
        this.town = town;
    }

    public Variable getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Variable customerId) {
        this.customerId = customerId;
    }

    public Variable getFullName() {
        return fullName;
    }

    public void setFullName(Variable fullName) {
        this.fullName = fullName;
    }

    public Variable getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(Variable cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Variable getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Variable cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Variable getCvc() {
        return cvc;
    }

    public void setCvc(Variable cvc) {
        this.cvc = cvc;
    }

    public Variable getYy() {
        return yy;
    }

    public void setYy(Variable yy) {
        this.yy = yy;
    }

    public Variable getMm() {
        return mm;
    }

    public void setMm(Variable mm) {
        this.mm = mm;
    }

    public Variable getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(Variable addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public Variable getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(Variable addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public Variable getPostcode() {
        return postcode;
    }

    public void setPostcode(Variable postcode) {
        this.postcode = postcode;
    }

    public Variable getTown() {
        return town;
    }

    public void setTown(Variable town) {
        this.town = town;
    }
}
