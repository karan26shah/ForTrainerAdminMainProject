package in.fortrainer.admin.utilities;

import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import in.fortrainer.admin.models.Admin;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.models.Video;
import in.fortrainer.admin.models.userLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @POST("users/sign_in")
    Call<Admin> loginin(@Body userLogin userLogin);

    @GET("app/events")
    Call<JsonObject> getEventlist();

    @FormUrlEncoded
    @PUT("app/events/{event_id}/update")
    Call<JsonObject> updateEventDetails(@Path("event_id") int eventId, @Field("name") String event_name,@Field("venue") String event_venue,@Field("start_datetime") String event_stdate,@Field("end_datetime") String event_eddate,@Field("price") String event_price);

    @DELETE("app/events/{event_id}/delete")
    Call<JsonObject> deleteEvent(@Path("event_id") int eventId);

    @GET("app/orders")
    Call<JsonObject> getOrderslist( @Query("page") int i,@Query("per_page") int perpage);

    @GET("app/banners")
    Call<List<Banner>> listBanner();

    @DELETE("app/banners/{banner_id}/delete")
    Call<JsonObject> deleteBanner(@Path("banner_id") int banner_id);

    @FormUrlEncoded
    @PUT("app/banners/{banner_id}/update")
    Call<JsonObject> updateBannerDetails(@Path("banner_id") int id, @Field("banner[title]") String title, @Field("banner[link_url]") String LinkUrl);

    @GET("apps")
    Call<JsonObject> getApplist();

    @GET("app/posts")
    Call<JsonObject> getPostlist();

    @DELETE("app/posts/{post_id}/delete")
    Call<JsonObject> deletePost(@Path("post_id") int postId);

    @FormUrlEncoded
    @PUT("app/posts/{post_id}/update")
    Call<JsonObject> Updatepost( @Path("post_id") int post_id, @Field("post[title]") String post_title, @Field("post[description]") String post_desc);

    @GET("app/products")
    Call<JsonObject> getProductslist();

    @FormUrlEncoded
    @PUT("app/products/{product_id}/update")
    Call<JsonObject> Updateproduct(@Path("product_id") int product_id, @Field("app_product[name]") String product_name, @Field("app_product[price]") String product_price);

    @DELETE("app/products/{product_id}/delete")
    Call<JsonObject> deleteProduct(@Path("product_id") int productId);

    @GET("app/app_users")
    Call<JsonObject> getAppUserslist(@Query("page") int i,@Query("per_page") int perpage);


    @GET("embed/youtube")
    Call<JsonObject> getYouTubeVideoUrl(@Query("url") String url);

    @FormUrlEncoded
    @POST("app/posts")
    Call<JsonObject> CreatePost( @Field("post[title]") String post_title, @Field("post[description]") String post_desc,@Field("post[link_url]") String link_url,@Field("post[youtube_url]") String youtube_url,@Field("post[youtube_image_url]") String youtube_image_url,@Field("post[youtube_video_id]") String youtube_video_id);

    @FormUrlEncoded
    @POST("shared_images")
    Call<JsonObject> CreateSharedImageId( @Field("owner_type") String owner_type,@Field("column_name") String column_name,@Field("filename") String filename);

    @FormUrlEncoded
    @PUT("shared_images/{id}/image_uploaded")
    Call<JsonObject> ImageUploaded( @Path("id") int id, @Field("image_name") String image_name);
}
