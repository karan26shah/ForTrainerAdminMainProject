
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppUser {

    @SerializedName("user_id")
    @Expose
    public int UserId;
    @SerializedName("app_id")
    @Expose
    public int AppId;
    @SerializedName("full_name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile_number")
    @Expose
    public String mobilenumber;
    @SerializedName("shared_image")
    @Expose
    public SharedImage sharedImage;

    public int getAppId() {
        return AppId;
    }

    public void setAppId(int appId) {
        AppId = appId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
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

    public SharedImage getImage() {
        return sharedImage;
    }

    public void setImage(SharedImage sharedImage) {
        this.sharedImage = sharedImage;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
    public static List<AppUser> getAppuserslist(int itemCount) {
        return null;
    }
}
