/*

 * Classname
 *  CMSC 495
 * Version information
 *  beta 0.1
 * Date
 *  11/20/2023
 * Member:
 * Austin Melvin
 * Chris Robinson
 * Genglin Yu
 * Raynick Ruiters
 * Kone Ronald
 */
package laundryattendant;
import laundryattendant.registernlogin.FormBuilder;

public class Main {
    public static String hello() {
        return ("Hello World");
    }

    public static void main(String[] args) {

        try {
            FormBuilder.get("admin", "12341234aA");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}