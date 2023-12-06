package laundryattendant.laundryticket;

import types.Type;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.github.cdimascio.dotenv.Dotenv;

public class TicketFactory {
    // returns newly created laundry ticket
    public static LaundryTicket makeTicket(Type type, String phonenum, String name, String username, LocalDate dateEstimate, String comment) {
        // Input validation
        /*
         * Type: enum Type
         * phoneNum: long, phoneNum.length = 10
         * name: varchar(50)
         * if wrong input throw error
         */
        
        // log10 and round up to get length of int
        if (phonenum.length() != 10)
            throw new Error("Phone number format is wrong");
        for (int i = 0; i < 10; i++) {
            if (!Character.isDigit(phonenum.charAt(i)))
                throw new Error("Phone number format is wrong");
        }

        // length of name.length > 50 or empty
        if (name.trim().length() == 0)
            throw new Error("Name Field can't be empty");
        else if (name.length() > 50)
            throw new Error("Name cannot exceed 50 letters");

        //comment has more than 100 characters
        if(comment.length() > 100)
            throw new Error("Comment cannot exceed 100 characters");

        // assign arguments
        return new LaundryTicket(type, phonenum, name, username,dateEstimate,comment,getTypePrice(type));
    }

    // delete ticket from database
    public static void delete(String username, String password, int ticketId) {
        Dotenv dotenv = Dotenv.load();
        URL url;
        try {
            url = new URL(dotenv.get("URL") + "/deleteTicket");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set optional properties
            conn.setRequestMethod("DELETE");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("ticketId", ticketId);
            String jsonString = jsonObject.toJSONString();

            // to byte using utf-8 encoding
            byte[] postData = jsonString.getBytes(StandardCharsets.UTF_8);
            // connecting to outputstream
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                // Write to outputstream
                wr.write(postData);
            }

            int responseCode = conn.getResponseCode();
            // if responsecode is not 200
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Error("Status code :" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // sends ticket to the database
    public static void append(Ticket ticket, String password) throws Error {
        try {
            Dotenv dotenv = Dotenv.load();
            URL url = new URL(dotenv.get("URL") + "/createTicket");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request option
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", ticket.getType().toString());
            jsonObject.put("price", ticket.getPrice());
            jsonObject.put("datereceived", ticket.getDateRecieved().toString());
            jsonObject.put("dateextimated", ticket.getDateExtimated().toString());
            jsonObject.put("status", ticket.getStatus().toString());
            jsonObject.put("phonenum", ticket.getPhoneNum());
            jsonObject.put("name", ticket.getName());
            jsonObject.put("comment", ticket.getComment());
            jsonObject.put("username", ticket.getUsername());
            jsonObject.put("password", password);
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

    // update client laundry status
    public static void update(String username, String password, int ticketId, String status) {
        Dotenv dotenv = Dotenv.load();
        URL url;
        try {
            url = new URL(dotenv.get("URL") + "/progressUpdate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set optional properties
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("ticketId", ticketId);
            jsonObject.put("status", status);
            String jsonString = jsonObject.toJSONString();

            // to byte using utf-8 encoding
            byte[] postData = jsonString.getBytes(StandardCharsets.UTF_8);
            // connecting to outputstream
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                // Write to outputstream
                wr.write(postData);
            }

            int responseCode = conn.getResponseCode();
            // if responsecode is not 200
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Error("Status code :" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // sends username and password to get all the corresponding tickets
    public static JSONArray getTickets(String username, String password) throws IllegalArgumentException {
        Dotenv dotenv = Dotenv.load();
        URL url;
        try {
            url = new URL(dotenv.get("URL") + "/ticket");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set request option
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            String jsonRequestBody = jsonObject.toJSONString();
            // Convert the string to bytes using UTF-8 encoding
            byte[] postData = jsonRequestBody.getBytes(StandardCharsets.UTF_8);

            // connecting to outputstream
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                // Write to outputstream
                wr.write(postData);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {

                    JSONParser jsonParser = new JSONParser();
                    try {

                        JSONArray jsonArray = (JSONArray) jsonParser.parse(inputLine);
                        return jsonArray;
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }

            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return null;
    }

    // get ItemPrice of Type
    public static double getTypePrice(Type type) {
        double price;
        switch (type) {
            case SHIRTS:
                price = 4.50;
                break;
            case POLO:
                price = 4.50;
                break;
            case BLOUSE:
                price = 9.95;
                break;
            case SWEATHER:
                price = 11.95;
                break;
            case JACKET:
                price = 11.95;
                break;
            case VEST:
                price = 11.95;
                break;
            case PANTS:
                price = 11.95;
                break;
            case SKIRT:
                price = 9.95;
                break;
            case SHORTS:
                price = 9.95;
                break;
            case CASUALDRESS:
                price = 16.95;
                break;
            case FORMALDRESS:
                price = 23.90;
                break;
            case COAT:
                price = 11.95;
                break;
            case FULLSUIT:
                price = 23.90;
                break;
            case BATHMAT:
                price = 5.00;
                break;
            case BLANKET:
                price = 15.00;
                break;
            case COVER:
                price = 15.00;
                break;
            default:
                price = 8.00;
                break;
        }
        return price;
    }
}
