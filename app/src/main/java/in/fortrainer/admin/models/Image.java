
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("original_image_url")
    @Expose
    private Object originalImageUrl;
    @SerializedName("small_image_url")
    @Expose
    private Object smallImageUrl;
    @SerializedName("medium_image_url")
    @Expose
    private Object mediumImageUrl;
    @SerializedName("large_image_url")
    @Expose
    private Object largeImageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(Object originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public Object getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(Object smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getMediumImageUrl() {
        return (String) mediumImageUrl;
    }

    public void setMediumImageUrl(Object mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getLargeImageUrl() {
        return (String) largeImageUrl;
    }

    public void setLargeImageUrl(Object largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

}
