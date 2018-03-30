package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heni on 20/6/17.
 */

public class SharedImage {

    public static final String EXTRA_JIMAGE = "jImage";
    public static final String EXTRA_JIMAGELIST = "jImagelist";
    public static String DUMMY_IMAGE_1 = "https://pbs.twimg.com/profile_images/618799893163827200/QIj_4T3W.jpg";
    public static String DUMMY_IMAGE_2 = "http://www.mountida.edu/wp-content/uploads/2013/01/add_visitcampus2013.jpg";
    public static String DUMMY_IMAGE_3 = "http://www.bestcollegereviews.org/wp-content/uploads/2014/01/hack-thumb-300x300.png";
    public static String DUMMY_IMAGE_4 = "http://www.studyat.uwa.edu.au/__data/assets/image/0009/1153908/St-Georges_new-image_CROPPED_300x300_for-website.jpg";
    public static String DUMMY_IMAGE_5 = "http://cdnak1.psbin.com/logos/id/33h6ox2iblgg5t31.png";
    public static String DUMMY_IMAGE_6 = "https://s-media-cache-ak0.pinimg.com/736x/c6/ba/1c/c6ba1cf6fab1b9c777b11e88fd000b1b.jpg";
    public static String DUMMY_IMAGE_7 = "http://www.georgeherald.com/img/gh201192115929.jpg";
    public static String DUMMY_IMAGE_8 = "https://s-media-cache-ak0.pinimg.com/736x/23/53/a9/2353a93d05a3a5c7407cc9039b662d68.jpg";
    public static String DUMMY_IMAGE_9 = "https://usatcollege.files.wordpress.com/2013/09/300-0913-altbreaks.jpg?w=300&h=300";
    public static String DUMMY_IMAGE_10 = "https://www.thiel.edu/assets/images/news/photo_archive_thumbs/small-hodge-residence-hall.jpg";
    public static String DUMMY_IMAGE_11 = "http://198.57.163.37/~eecappadmin/eecapp/eec/backend/web/uploads/study_abroad_college/study_abroad_college_57f501e980da9.jpg";

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image_thumb_url")
    @Expose
    private String imageThumbUrl;
    @SerializedName("original_image_url")
    @Expose
    private String originalImageUrl;
    @SerializedName("small_image_url")
    @Expose
    private String smallImageUrl;
    @SerializedName("medium_image_url")
    @Expose
    private String mediumImageUrl;
    @SerializedName("large_image_url")
    @Expose
    private String largeImageUrl;

    public SharedImage(String imageUrl) {
        this.largeImageUrl = imageUrl;
    }

    public static SharedImage getDummyImage(int entropy) {
        List<SharedImage> allDummyImages = getAllDummyImages();
        return allDummyImages.get(entropy % allDummyImages.size());
    }

    private static List<SharedImage> getAllDummyImages() {
        List<SharedImage> sharedImages = new ArrayList<>();
        sharedImages.add(new SharedImage(DUMMY_IMAGE_1));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_2));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_3));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_4));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_5));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_6));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_7));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_8));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_9));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_10));
        sharedImages.add(new SharedImage(DUMMY_IMAGE_11));
        return sharedImages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return getLargeImageUrl();
    }

    public String getImageThumbUrl() {
        return imageThumbUrl;
    }

    public void setImageThumbUrl(String imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
