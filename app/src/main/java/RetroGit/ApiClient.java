package RetroGit;

import android.content.Context;

import java.io.IOException;

import Utilities.SessionManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by narasinga_m on 8/26/2016.
 */
public class ApiClient {

   // public static final String BASE_URL = "http://35.162.42.110:3000/";
   // public static final String BASE_URL = "http://52.27.131.145:3000/";
   // public static final String BASE_URL = "http://35.161.197.248:3000/";
    public static final String BASE_URL = "http://52.42.79.56:3000/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {

//        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
//        }
        return retrofit;
    }
   public static Retrofit getClientWithHeader(Context c) {
       final SessionManager sessionManager = new SessionManager(c);

       if (sessionManager.checkLogin()) {

//            OkHttpClient httpClient = new OkHttpClient();
//
//            httpClient.networkInterceptors().add(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request().newBuilder().addHeader("Uid", sessionManager.getUserDetails().get(SessionManager.KEY_NAME)).
//                            addHeader(SessionManager.CLIENT,sessionManager.sharedPreferences.getString(SessionManager.CLIENT,""))
//                            .addHeader(SessionManager.ACCESS_TOKEN,sessionManager.sharedPreferences.getString(SessionManager.ACCESS_TOKEN,"")).build();
//                    return chain.proceed(request);
//                }
//            });
//             retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).client(httpClient).build();
//
//
//
//        }


           OkHttpClient okClient = new OkHttpClient.Builder()
                   .addInterceptor(
                           new Interceptor() {
                               @Override
                               public Response intercept(Interceptor.Chain chain) throws IOException {

                                       Request request = chain.request().newBuilder().addHeader("Uid", sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL)).
                                               addHeader(SessionManager.CLIENT, sessionManager.sharedPreferences.getString(SessionManager.CLIENT, ""))
                                               .addHeader(SessionManager.ACCESS_TOKEN, sessionManager.sharedPreferences.getString(SessionManager.ACCESS_TOKEN, "")).build();
//                                Request original = chain.request();
//
//                                // Request customization: add request headers
//                                Request.Builder requestBuilder = original.newBuilder()
//                                        .header("Authorization", token)
//                                        .method(original.method(), original.body());

                                   //    Request request = requestBuilder.build();
                                   return chain.proceed(request);

                               }
                           })
                   .build();

           retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).client(okClient).build();

       }
       return retrofit;
   }
}
