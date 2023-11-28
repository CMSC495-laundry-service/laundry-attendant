package laundryattendant.registernlogin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.cdimascio.dotenv.Dotenv;
import laundryattendant.laundryticket.Ticket;
import laundryattendant.laundryticket.TicketFactory;
import types.Gender;

public class FormBuilder {
    public static RegisterForm makeForm(String firstName, String lastName, String email, String password,
            String username,
            LocalDate dOB, String gender, String phoneNum, String address, String securityQuestion,
            String securityAnswer,
            String bankCard, String cvv) {
        if (firstName.length() > 25 || firstName.isBlank())
            throw new Error("Firstname Format Wrong!");
        if (lastName.length() > 25 || lastName.isBlank())
            throw new Error("Lastname Format Wrong!");
        if (!email.matches(".+@.+\\.com") || email.length() > 45)
            throw new Error("Email Format Wrong!");
        if (username.length() > 15 || username.isBlank())
            throw new Error("Username Format Wrong!");
        if (password.length() < 8 || password.length() > 25)
            throw new Error("Password must be 9-25 characters");
        if (!password.matches(".*\\d.*"))
            throw new Error("Password must contain at least one digit");
        if (!password.matches(".*[A-Z].*"))
            throw new Error("Password must contain at least one Uppercase letter");
        if (!password.matches(".*[a-z].*"))
            throw new Error("Password must contain at least one Lowercase letter");
        if (!isDigit(phoneNum) || phoneNum.length() != 10)
            throw new Error("Phone Number format is incorrect");
        if (address.isBlank() || address.length() > 50)
            throw new Error("Address exceed limit");
        if (securityQuestion.isBlank() || securityQuestion.length() > 50)
            throw new Error("Security Question cannot be empty");
        if (securityAnswer.isBlank() || securityAnswer.length() > 50)
            throw new Error("Security Answer cannot be empty");
        if (bankCard.length() != 16 || !isDigit(bankCard))
            throw new Error("Bankcard must be 16 digit");
        if (cvv.length() != 3 || !isDigit(cvv))
            throw new Error("CVV must be 3 digits");

        Gender genderObject;
        switch (gender) {
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
        
        return new RegisterForm(firstName, lastName, email, password, username, dOB, genderObject, phoneNum, address,
                securityQuestion, securityAnswer, bankCard, cvv);
        // append(firstName, lastName, email, password, username, dOB.toString(),
        // gender, phoneNum, address, securityQuestion, securityAnswer, bankCard, cvv);
    }

    public static LoginForm get(String username, String password) throws Exception {
        LoginForm loginForm = new LoginForm(username, password);
        Dotenv dotenv = Dotenv.load();
        String attributeString = "?username=" + URLEncoder.encode(loginForm.getUsername(), "UTF-8") +
                "&password=" + URLEncoder.encode(loginForm.getPassword(), "UTF-8");
        URL url = new URL(dotenv.get("URL") + "/login" + attributeString);

        // Open connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Get the response code
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String permission = in.readLine();
            if (permission.equals("client"))
                loginForm.setAdmin(false);
            else
                loginForm.setAdmin(true);
            in.close();
            connection.disconnect();
            return loginForm;

        } else {
            connection.disconnect();
            throw new Exception("Status code: " + responseCode);
        }
    }

    public static void append(RegisterForm registerForm) {
        System.out.println("Connecting to the Database...");
        try {
            Dotenv dotenv = Dotenv.load();
            URL url = new URL(dotenv.get("URL") + "/register");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request option
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", registerForm.getFirstName());
            jsonObject.put("lastName", registerForm.getLastName());
            jsonObject.put("email", registerForm.getEmail());
            jsonObject.put("password", registerForm.getPassword());
            jsonObject.put("username", registerForm.getUsername());
            jsonObject.put("dOB", registerForm.getDOB().toString());
            jsonObject.put("gender", registerForm.getGender().toString());
            jsonObject.put("phoneNum", registerForm.getPhoneNum());
            jsonObject.put("address", registerForm.getAddress());
            jsonObject.put("securityQuestion", registerForm.getSecurityQuestion());
            jsonObject.put("securityAnswer", registerForm.getSecurityAnswer());
            jsonObject.put("bankCard", registerForm.getBankCard());
            jsonObject.put("cvv", registerForm.getCVV());
            jsonObject.put("isAdmin", false);
            String jsonRequestBody = jsonObject.toJSONString();

            // Convert the string to bytes using UTF-8 encoding
            byte[] postData = jsonRequestBody.getBytes(StandardCharsets.UTF_8);

            // connecting to outputstream
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                // Write to outputstream
                wr.write(postData);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                System.out.println(response.toString());
            } else {
                System.out.println("Status code: " + responseCode);
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static boolean isDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
}
