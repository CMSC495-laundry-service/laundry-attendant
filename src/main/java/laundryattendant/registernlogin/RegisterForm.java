package laundryattendant.registernlogin;

import java.time.LocalDate;
import java.util.Date;

import types.Gender;

public class RegisterForm implements Form{
    /*
     * ATTRIBUTE LIST:
     * string firstName
     * string lastName
     * string email
     * string password
     * string username
     * date dOB
     * gender gender
     * string phoneNum
     * string address
     * string securityQuestion
     * string securityAnswer
     * string bankCard
     * int cvv
     */
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private LocalDate dOB;
    private Gender gender;
    private String phoneNum;
    private String address;
    private String securityQuestion;
    private String securityAnswer;
    private String bankCard;
    private String cvv;
    private boolean isAdmin;

    RegisterForm(String firstName,String lastName,String email,String password, String username,
    LocalDate dOB, Gender gender, String phoneNum, String address,String securityQuestion, String securityAnswer,
    String bankCard, String cvv){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.dOB= dOB;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.address = address;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.bankCard = bankCard;
        this.cvv = cvv;
        this.isAdmin = false;
    }
    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return username;
    }
    public String getSecurityAnswer(){
        return securityAnswer;
    }
    public String getBankCard(){
        return bankCard;
    }
    public String getCVV(){
        return cvv;
    }
    public String getName(){
        return firstName + ' ' + lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public Gender getGender() {
        return gender;
    }
    public LocalDate getDOB() {
        return dOB;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public String getSecurityQuestion() {
        return securityQuestion;
    }
    @Override
    public boolean isAdmin() {
        return isAdmin;
    }
}
