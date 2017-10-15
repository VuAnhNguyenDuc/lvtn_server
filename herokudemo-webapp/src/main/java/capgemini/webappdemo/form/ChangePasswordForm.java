package capgemini.webappdemo.form;

/**
 * Created by Vu Anh Nguyen Duc on 10/15/2017.
 */
public class ChangePasswordForm {
    private String old_pass;
    private String new_pass;
    private String json_token;

    public String getOld_pass() {
        return old_pass;
    }

    public void setOld_pass(String old_pass) {
        this.old_pass = old_pass;
    }

    public String getNew_pass() {
        return new_pass;
    }

    public void setNew_pass(String new_pass) {
        this.new_pass = new_pass;
    }

    public String getJson_token() {
        return json_token;
    }

    public void setJson_token(String json_token) {
        this.json_token = json_token;
    }
}
