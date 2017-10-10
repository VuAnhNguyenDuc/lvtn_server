package capgemini.webappdemo.domain;

public class Message {
    String message;

    String json_token;


    public Message(String message) {
        this.message = message;
    }

    public Message(String message, String json_token) {
        this.message = message;
        this.json_token = json_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJson_token() {
        return json_token;
    }

    public void setJson_token(String json_token) {
        this.json_token = json_token;
    }
}
