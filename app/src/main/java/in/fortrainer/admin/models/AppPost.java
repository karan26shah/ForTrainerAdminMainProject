
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppPost {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("attachable_type")
    @Expose
    private Object attachableType;
    @SerializedName("attachable_id")
    @Expose
    private Object attachableId;
    @SerializedName("link_url")
    @Expose
    private Object linkUrl;
    @SerializedName("likes_count")
    @Expose
    private Integer likesCount;
    @SerializedName("liked")
    @Expose
    private Boolean liked;
    @SerializedName("shared_image")
    @Expose
    private SharedImage sharedImage;

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
        this.title = (String) title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = (String) description;
    }

    public Object getAttachableType() {
        return attachableType;
    }

    public void setAttachableType(Object attachableType) {
        this.attachableType = attachableType;
    }

    public Object getAttachableId() {
        return attachableId;
    }

    public void setAttachableId(Object attachableId) {
        this.attachableId = attachableId;
    }

    public Object getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(Object linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public SharedImage getSharedImage() {
        return sharedImage;
    }

    public void setSharedImage(SharedImage sharedImage) {
        this.sharedImage = sharedImage;
    }

}
