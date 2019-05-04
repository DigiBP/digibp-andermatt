package ch.fhnw.justme.model;

public class ValueInfo {
    private String serializationDataForm;

    public ValueInfo() {
    }

    public ValueInfo(String serializationDataForm) {
        this.serializationDataForm = serializationDataForm;
    }

    public String getSerializationDataForm() {
        return serializationDataForm;
    }

    public void setSerializationDataForm(String serializationDataForm) {
        this.serializationDataForm = serializationDataForm;
    }

    @Override
    public String toString() {
        return "ValueInfo{" +
                "serializationDataForm='" + serializationDataForm + '\'' +
                '}';
    }
}
