package in.fortrainer.admin.utilities;

import com.google.gson.JsonObject;

import java.util.List;

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
    Call<JsonObject> loginin(@Body userLogin userLogin);

    @GET("app-events.json")
    Call<JsonObject> getEventlist();

    @GET("users.json")
    Call<JsonObject> getuserslist(@Query("page") int i,@Query("per_page") int perpage);

    @GET("app-orders.json")
    Call<JsonObject> getOrderslist(@Query("page") int i,@Query("per_page") int perpage);

    @GET("banners/index.json")
    Call<List<Banner>> listBanner();

    @GET("apps.json")
    Call<JsonObject> getApplist();


}
