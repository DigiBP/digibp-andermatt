package ch.fhnw.justme;

import android.graphics.Bitmap;

public class Message {

    public enum Sender {
        USER(1), BOT(2), USER_IMAGE(3);

        private int value;

        private Sender(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    private String text;

    private Sender sender;

    private Bitmap bitmap;

    public Message(Bitmap bitmap, Sender sender) {
        this.sender = sender;
        this.bitmap = bitmap;
    }

    public Message(String text, Sender sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
