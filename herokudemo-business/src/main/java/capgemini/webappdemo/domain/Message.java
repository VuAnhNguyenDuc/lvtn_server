package capgemini.webappdemo.domain;

public class Message {
    private String message;

    private String json_token;

    private String user_type;


    public Message(String message) {
        this.message = message;
        this.json_token = "";
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
