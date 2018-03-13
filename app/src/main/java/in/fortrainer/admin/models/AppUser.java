
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppUser {

    @SerializedName("id")
    @Expose
    public Integer UserId;
    @SerializedName("name")
    @Expose
    public String name;

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

}
