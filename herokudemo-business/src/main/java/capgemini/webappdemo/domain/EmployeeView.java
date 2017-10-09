package capgemini.webappdemo.domain;

public class EmployeeView {
    private int id;
    private String username;
    private String email;
    private String employeeType;
    private String managerName;
    private int status;

    public EmployeeView() {
    }

    public EmployeeView(int id, String username, String email, String employeeType, String managerName, int status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.employeeType = employeeType;
        this.managerName = managerName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
