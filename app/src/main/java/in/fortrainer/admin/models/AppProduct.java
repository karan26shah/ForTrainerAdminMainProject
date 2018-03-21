
package in.fortrainer.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppProduct {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("short_description")
    @Expose
    public String shortDescription;
    @SerializedName("long_description")
    @Expose
    public String longDescription;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("readable_price")
    @Expose
    public String readablePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getReadablePrice() {
        return readablePrice;
    }

    public void setReadablePrice(String readablePrice) {
        this.readablePrice = readablePrice;
    }

}
