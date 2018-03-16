
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    public Integer UserId;
    @SerializedName("full_name")
    @Expose
    public String name;
    @SerializedName("mobile_number")
    @Expose
    public String mobileNumber;
    @SerializedName("email")
    @Expose
    public String email;

    public Integer getId() {
        return UserId;
    }

    public void setId(Integer id) {
        this.UserId = UserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public static List<User> getUserslist(int itemCount) {
        return null;
    }
}