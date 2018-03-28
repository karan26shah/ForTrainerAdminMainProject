package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by foram on 28/3/18.
 */

public class AppUser1 {

    @SerializedName("id")
    @Expose
    public int UserId;
    @SerializedName("full_name")
    @Expose
    public String name1;
    @SerializedName("profile_image_url")
    @Expose
    public String image_url;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
