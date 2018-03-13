package in.fortrainer.admin.utilities;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import in.fortrainer.admin.BuildConfig;
import in.fortrainer.admin.R;
import in.fortrainer.admin.models.Admin;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HBB20 on 7/3/2016.
 */
public class RetrofitHelper {
    private static RetrofitHelper retrofitHelper;
    private static Retrofit retrofit;
    private static RetrofitService retrofitService;
    private static String TAG = "<<RETRO CONTROLLER>> ";

    private static  String activeAuthToken = null;

    private RetrofitHelper() {

    }

    /**
     * returns the retrofit service to make http launchEditActivity.
     *
     * @param context
     * @return
     */
    public static RetrofitService getRetrofitService(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url) + context.getString(R.string.api_version) + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLocalhostHttpInterceptedClient(context))
              // .client(getInterceptedClient(context))
                .build();

        return retrofit.create(RetrofitService.class);
    }

    private static void setupAuthToken(Context context) {
        //set auth token
        if (Admin.getCurrentUser(context) != null) {
            activeAuthToken = "Token token=\"" + Admin.getCurrentUser(context).getAuthKey() + "\""; // config will have plain token e.g. "da384eb1e7775f3d3023ca1ec0873cd2", but in header we need to pass in different format, Token token="da384eb1e7775f3d3023ca1ec0873cd2"
        }
    }

    private static String getActiveAuthToken(Context context){
        setupAuthToken(context);
        return activeAuthToken;
    }

    /**
     * returns the retrofit service to make http launchEditActivity.
     *
     * @param context
     * @return
     */
    public static RetrofitService getRetrofitServiceForPrimitiveLogin(Context context, int userID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url) + context.getString(R.string.api_version) + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptedClientForPrimitiveLogin(context, userID))
                .build();

        return retrofit.create(RetrofitService.class);
    }

    /**
     * returns the retrofit service to make http launchEditActivity.
     *
     * @param context
     * @return
     */
    public static RetrofitService getRetrofitServiceForYouTube(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.youtube_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        return retrofit.create(RetrofitService.class);
    }

    private static OkHttpClient getLocalhostHttpInterceptedClient(final Context context) {
        try {

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder builder  = original.newBuilder().addHeader("platform-code","android")
                            .method(original.method(), original.body());
                    Admin currentAdmin = Admin.getCurrentUser(context);
                    if( currentAdmin != null){
                        if(Admin.isLoggedIn(context)) {
                            builder.addHeader("user-id", currentAdmin.getId().toString())
                                    .addHeader(context.getString(R.string.authHeader), getActiveAuthToken(context))
                                    .method(original.method(), original.body());
                        }
                    }else{
                       Log.d("TAG","curret admin null") ;
                    }


               //     builder.addHeader("platform", "Android");
              //      builder.addHeader("version-code", BuildConfig.VERSION_CODE + "");
                    Request request = builder.build();
                    return chain.proceed(request);
                }
            });

            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

            return okHttpClientBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static OkHttpClient getInterceptedClient(final Context context) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .addHeader(context.getString(R.string.authHeader), context.getString(R.string.accessToken))
                            .method(original.method(), original.body());

                    builder.addHeader("platform", "Android");
                    builder.addHeader("version-code", BuildConfig.VERSION_CODE + "");

                    Request request = builder.build();
                    return chain.proceed(request);
                }
            });

            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

            //conncetion specs
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .allEnabledTlsVersions()
                    .allEnabledCipherSuites()
                    .build();
            okHttpClientBuilder.connectionSpecs(Collections.singletonList(spec));


            return okHttpClientBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static OkHttpClient getInterceptedClientForPrimitiveLogin(final Context context, final int userID) {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .addHeader(context.getString(R.string.authHeader), context.getString(R.string.accessToken))
                            .method(original.method(), original.body());

                    builder.addHeader("platform", "Android");
                    builder.addHeader("version-code", BuildConfig.VERSION_CODE + "");
                    builder.addHeader("appUser-id", userID + "");

                    Request request = builder.build();
                    return chain.proceed(request);
                }
            });

            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

            //conncetion specs
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .allEnabledTlsVersions()
                    .allEnabledCipherSuites()
                    .build();
            okHttpClientBuilder.connectionSpecs(Collections.singletonList(spec));


            return okHttpClientBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .addHeader(context.getString(R.string.authHeader), context.getString(R.string.accessToken))
                        .method(original.method(), original.body());

                builder.addHeader("platform", "Android");
                builder.addHeader("appUser-id", userID + "");
                Request request = builder.build();
                return chain.proceed(request);
            }
        }).readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build();
        */
    }
}
