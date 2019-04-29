package ch.fhnw.justme.model;

public class StartProcessFormRequest {

    private String processId;

    private String clothing;
    private String color;

    public StartProcessFormRequest(String processId, String clothing, String color) {
        this.processId = processId;
        this.clothing = clothing;
        this.color = color;
    }

    public String getClothing() {
        return clothing;
    }

    public void setClothing(String clothing) {
        this.clothing = clothing;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
