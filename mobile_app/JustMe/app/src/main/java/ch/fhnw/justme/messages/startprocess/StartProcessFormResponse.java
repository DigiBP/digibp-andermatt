package ch.fhnw.justme.messages.startprocess;

public class StartProcessFormResponse {

    private String id;

    public StartProcessFormResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
