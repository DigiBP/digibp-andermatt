package ch.fhnw.justme.messages.message;

public class CorrelateMessageRequest {
    String messageName;
    String processInstanceId;

    public CorrelateMessageRequest(String messageName, String processInstanceId) {
        this.messageName = messageName;
        this.processInstanceId = processInstanceId;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
