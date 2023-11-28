package laundryattendant.registernlogin;

public class LoginForm implements Form {

    private String username;
    private String password;
    private boolean isAdmin = false;

    LoginForm(String username,String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setAdmin(boolean bool){
        isAdmin = bool;
    }
    public boolean isAdmin(){
        return isAdmin;
    }
}
