public class Courier {
    private String firstName;
    private String password;
    private String login;
    public Courier(String firstName,String password,String login){
        this.firstName=firstName;
        this.login=login;
        this.password=password;
            }
    public Courier(String password,String login){
        this.login=login;
        this.password=password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}