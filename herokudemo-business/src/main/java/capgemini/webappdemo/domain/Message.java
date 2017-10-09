package capgemini.webappdemo.domain;

public class Message {
    String message;

    String jsonTokenKey;


    public Message(String message) {
        this.message = message;
    }

    public Message(String message, String jsonTokenKey) {
        this.message = message;
        this.jsonTokenKey = jsonTokenKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJsonTokenKey() {
        return jsonTokenKey;
    }

    public void setJsonTokenKey(String jsonTokenKey) {
        this.jsonTokenKey = jsonTokenKey;
    }


}
