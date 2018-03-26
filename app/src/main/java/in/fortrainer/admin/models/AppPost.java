
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppPost {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("shared_image")
    @Expose
    public SharedImage sharedImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SharedImage getImage() {
        return sharedImage;
    }

    public void setImage(SharedImage sharedImage) {
        this.sharedImage = sharedImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
