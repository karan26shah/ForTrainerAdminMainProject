package in.fortrainer.admin.utilities;

import com.google.gson.JsonObject;

import java.util.List;

import in.fortrainer.admin.models.Admin;
import in.fortrainer.admin.models.Banner;
import in.fortrainer.admin.models.userLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @POST("users/sign_in")
    Call<Admin> loginin(@Body userLogin userLogin);

    @GET("apps/{id}/events")
    Call<JsonObject> getEventlist(@Path("id") int id);

    @GET("apps/{id}/orders")
    Call<JsonObject> getOrderslist(@Path("id") int id, @Query("page") int i,@Query("per_page") int perpage);
    @GET("users.json")
    Call<JsonObject> getuserslist(@Query("page") int i,@Query("per_page") int perpage);

    @GET("apps/{id}/banners")
    Call<List<Banner>> listBanner(@Path("id") int id);

    @GET("apps.json")
    Call<JsonObject> getApplist();

    @GET("apps/{id}/posts")
    Call<JsonObject> getPostlist(@Path("id") int id);

    @FormUrlEncoded
    @PUT("app/posts/{post_id}/update")
    Call<JsonObject> Updatepost( @Path("post_id") int post_id, @Field("app_post[title]") String post_title, @Field("app_post[description]") String post_desc);

    @GET("app/products")
    Call<JsonObject> getProductslist();

    @FormUrlEncoded
    @PUT("app/products/{product_id}/update")
    Call<JsonObject> Updateproduct(@Path("product_id") int product_id, @Field("app_product[name]") String product_name, @Field("app_product[price]") String product_price);

}
