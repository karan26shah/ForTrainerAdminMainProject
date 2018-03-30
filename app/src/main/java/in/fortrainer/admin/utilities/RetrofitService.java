package in.fortrainer.admin.utilities;

import com.google.gson.JsonObject;

import java.util.List;

import in.fortrainer.admin.models.Admin;
import in.fortrainer.admin.models.AppUser;
import in.fortrainer.admin.models.Banner;
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

    @GET("apps/{id}/events")
    Call<JsonObject> getEventlist(@Path("id") int id);

    @GET("apps/{id}/orders")
    Call<JsonObject> getOrderslist(@Path("id") int id, @Query("page") int i,@Query("per_page") int perpage);

    @GET("apps/{id}/banners")
    Call<List<Banner>> listBanner(@Path("id") int id);

    @GET("apps.json")
    Call<JsonObject> getApplist();

    @GET("apps/{id}/posts")
    Call<JsonObject> getPostlist(@Path("id") int id);

    @GET("apps/{id}/products")
    Call<JsonObject> getProductslist(@Path("id") int id);

    @GET("apps/{app_id}/app_users")
    Call<JsonObject> getAppUserslist(@Path("app_id") int id, @Query("page") int i,@Query("per_page") int perpage);

    @GET("apps/{app_id}/app_users/{user_id}")
    Call<AppUser> getAppUsersDetails(@Path("app_id") int appid,@Path("user_id") int userid);

}
