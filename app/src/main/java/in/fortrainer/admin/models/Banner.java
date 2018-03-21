
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Banner {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public Object description;
    @SerializedName("attachable_type")
    @Expose
    public String attachableType;
    @SerializedName("attachable_id")
    @Expose
    public Integer attachableId;
    @SerializedName("link_url")
    @Expose
    public Object linkUrl;
    @SerializedName("banner_type")
    @Expose
    public BannerType bannerType;
    @SerializedName("shared_image")
    @Expose
    public SharedImage sharedImage;

    public static List<Banner> getSupportedBannerSubList(List<Banner> banners) {
        List<Banner> supportedBanners = new ArrayList<>();
        for (Banner banner : banners) {
            if (banner.isSupported()) {
                supportedBanners.add(banner);
            }
        }
        return supportedBanners;
    }

    private boolean isSupported() {
        switch (getBannerType().getCode()) {
            case BannerType.WEB_LINK:
                return true;
            default:
                return false;
        }
    }
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

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getAttachableType() {
        return attachableType;
    }

    public void setAttachableType(String attachableType) {
        this.attachableType = attachableType;
    }

    public Integer getAttachableId() {
        return attachableId;
    }

    public void setAttachableId(Integer attachableId) {
        this.attachableId = attachableId;
    }

    public Object getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(Object linkUrl) {
        this.linkUrl = linkUrl;

    }


    public BannerType getBannerType() {
        return bannerType;
    }

    public void setBannerType(BannerType bannerType) {
        this.bannerType = bannerType;
    }

    public SharedImage getImage() {
        return sharedImage;
    }

    public void setImage(SharedImage sharedImage) {
        this.sharedImage = sharedImage;
    }

    public class BannerType {

        public static final String WEB_LINK = "WEB_LINK";
        @SerializedName("code")
        @Expose
        public String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }
}