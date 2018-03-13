
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("id")
    @Expose
    public Integer AppId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("short_description")
    @Expose
    public String shortDescription;

    public Integer getAppId() {
        return AppId;
    }

    public void setAppId(Integer id) {
        this.AppId = AppId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}
