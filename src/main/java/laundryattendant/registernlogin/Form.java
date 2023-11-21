package laundryattendant.registernlogin;
import java.time.LocalDate;
import java.util.Date;

import types.Gender;
public interface Form {
    String getName();
    String getEmail();
    String getAddress();
    Gender getGender();
    LocalDate getDOB();
    String getPhoneNum();
    String getSecurityQuestion();
    boolean isAdmin();
}
