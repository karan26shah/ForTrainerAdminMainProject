
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppUser {

    @SerializedName("id")
    @Expose
    public int UserId;
    @SerializedName("full_name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile_number")
    @Expose
    public String mobilenumber;

    public int getId() {
        return UserId;
    }

    public void setId(int id) {
        this.UserId = UserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
    public static List<AppUser> getAppuserslist(int itemCount) {
        return null;
    }
}
