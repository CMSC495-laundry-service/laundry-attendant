package laundryattendant.registernlogin;
import java.util.Date;

import types.Gender;
public interface Form {
    String getName();
    String getEmail();
    String getAddress();
    Gender getGender();
    Date getDOB();
    String getPhoneNum();
    String getSecurityQuestion();
}
