package capgemini.webappdemo.domain;

public class Message {
    private String message;

    private String json_token;

    private String type;

    private int id;


    public Message(String message) {
        this.message = message;
        this.json_token = "";
    }

    public Message(String message, String json_token) {
        this.message = message;
        this.json_token = json_token;
    }

    public Message(String message, String json_token, String type, int id) {
        this.message = message;
        this.json_token = json_token;
        this.type = type;
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
