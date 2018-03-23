package in.fortrainer.admin.utilities;

import com.google.gson.JsonObject;

import java.util.List;

import in.fortrainer.admin.models.Admin;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.models.userLogin;
import retrofit2.Call;
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
    Call<JsonObject> getOrderslist(@Query("page") int i,@Query("per_page") int perpage);
    @GET("users.json")
    Call<JsonObject> getuserslist(@Query("page") int i,@Query("per_page") int perpage);

    @GET("app/banners")
    Call<List<Banner>> listBanner();

    @GET("app/banners/{banner_id}/banner_details.json")
    Call<List<Banner>> getBannersDetails(@Path("banner_id") int Id);

    @DELETE("app/banners/{banner_id}/banner_delete.json")
    Call<JsonObject> deleteBanner(@Path("banner_id") int banner_id);

    @FormUrlEncoded
    @PUT("app/banners/{banner_id}/banner_update.json")
    Call<JsonObject> updateBannerDetails(@Path("banner_id") int id, @Field("banner[title]") String title, @Field("banner[link_url]") String LinkUrl);

    @GET("apps.json")
    Call<JsonObject> getApplist();

    @GET("app/posts")
    Call<JsonObject> getPostlist();

    @FormUrlEncoded
    @PUT("app/posts/{post_id}/update")
    Call<JsonObject> Updatepost( @Path("post_id") int post_id, @Field("app_post[title]") String post_title, @Field("app_post[description]") String post_desc);

    @GET("app/products")
    Call<JsonObject> getProductslist();

    @FormUrlEncoded
    @PUT("app/products/{product_id}/update")
    Call<JsonObject> Updateproduct(@Path("product_id") int product_id, @Field("app_product[name]") String product_name, @Field("app_product[price]") String product_price);

}
