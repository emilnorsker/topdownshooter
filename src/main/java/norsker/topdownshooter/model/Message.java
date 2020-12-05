package norsker.topdownshooter.model;

public class Message
{
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String message) {
        this.message = message;
    }
    public Message() {

    }
    private String message;

}
