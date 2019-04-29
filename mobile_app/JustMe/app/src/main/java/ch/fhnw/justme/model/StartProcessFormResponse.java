package ch.fhnw.justme.model;

public class StartProcessFormResponse {

    private String processInstanceId;

    public StartProcessFormResponse(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
