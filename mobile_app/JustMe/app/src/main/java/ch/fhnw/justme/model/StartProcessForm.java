package ch.fhnw.justme.model;

public class StartProcessForm {

    private String clothing;
    private String color;

    public StartProcessForm(String clothing, String color) {
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
}
