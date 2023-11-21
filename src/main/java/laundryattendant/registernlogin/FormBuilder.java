package laundryattendant.registernlogin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.cj.xdevapi.JsonArray;

import types.Gender;

public class FormBuilder {
    private RegisterForm form;
    public void makeForm(String firstName, String lastName, String email, String password, String username, 
    LocalDate dOB, String gender, String phoneNum, String address, String securityQuestion, String securityAnswer, 
    String bankCard, String cvv)
    {
        if (firstName.length() > 25 || firstName.isEmpty())
            throw new Error("Firstname Format Wrong!");
        if (lastName.length() > 25 || lastName.isEmpty())
            throw new Error("Lastname Format Wrong!");
        if (!email.matches(".+@.+\\.com"))
            throw new Error("Email Format Wrong!");
        if (username.length() > 15 || username.isEmpty()) 
            throw new Error("Username Format Wrong!");
        if (password.length() < 8 || password.length() > 25 ) 
            throw new Error("Password must be 9-25 characters");
        if (!password.matches(".*\\d.*"))
            throw new Error("Password must contain at least one digit");
        if (!password.matches(".*[A-Z].*"))
            throw new Error("Password must contain at least one Uppercase letter");
        if (!password.matches(".*[a-z].*"))
            throw new Error("Password must contain at least one Lowercase letter");
        if (!isDigit(phoneNum) || phoneNum.length()!= 10)
            throw new Error("Phone Number format is incorrect");
        if (address.isEmpty())
            throw new Error("Address cannot be empty");
        if (securityQuestion.isEmpty())
            throw new Error("Security Question cannot be empty");
        if (securityAnswer.isEmpty())
            throw new Error("Security Answer cannot be empty");
        if (!isDigit(cvv))
            throw new Error("CVV must be digits");
        
        Gender genderObject;
        switch(gender){
            case "Male":
                genderObject = Gender.MALE;
                break;
            case "Female":
                genderObject = Gender.FEMALE;
                break;
            default:
                genderObject = Gender.UNKNOWN;
                break;
        }
        form = new RegisterForm(firstName, lastName, email, password, username, dOB, genderObject, phoneNum, address, securityQuestion, securityAnswer, bankCard, cvv);
        
        append(firstName, lastName, email, password, username, dOB.toString(), gender, phoneNum, address, securityQuestion, securityAnswer, bankCard, cvv);
    }

    public void append(String firstName, String lastName, String email, String password, String username, 
    String dOB, String gender, String phoneNum, String address, String securityQuestion, String securityAnswer, 
    String bankCard, String cvv) {
        JSONParser jsonParser = new JSONParser();
        try {
            Path filePath = Paths.get("./src/main/java/laundryattendant/users.json");
            
            JSONObject jsonObject = (JSONObject)jsonParser.parse(new FileReader(filePath.toString()));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");

            System.out.println(jsonArray);

            JSONObject userForm = new JSONObject();
            userForm.put("firstName", firstName);
            userForm.put("lastName", lastName);
            userForm.put("email", email);
            userForm.put("password", password);
            userForm.put("username", username);
            userForm.put("dOB", dOB);
            userForm.put("gender", gender);
            userForm.put("phoneNum", phoneNum);
            userForm.put("address", address);
            userForm.put("securityQuestion", securityQuestion);
            userForm.put("securityAnswer", securityAnswer);
            userForm.put("bankCard", bankCard);
            userForm.put("cvv",cvv);
            userForm.put("isAdmin",false);

            jsonArray.add(userForm);

            System.out.println(jsonArray);

            JSONObject json = new JSONObject();
            json.put("users", jsonArray);

            FileWriter file = new FileWriter(filePath.toString());
            file.write(json.toJSONString());
            file.flush();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDigit(String str) {
        for (int i =0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
}
