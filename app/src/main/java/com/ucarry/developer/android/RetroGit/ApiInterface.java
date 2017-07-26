package com.ucarry.developer.android.RetroGit;

import java.util.List;

import com.ucarry.developer.android.Model.AcceptOrderResponse;
import com.ucarry.developer.android.Model.AcceptResponse;
import com.ucarry.developer.android.Model.CarrierScheduleRequest;
import com.ucarry.developer.android.Model.CarrierSchedules;
import com.ucarry.developer.android.Model.ContactUs;
import com.ucarry.developer.android.Model.FCMRequest;
import com.ucarry.developer.android.Model.GenericResponse;
import com.ucarry.developer.android.Model.ImageUploadResponse;
import com.ucarry.developer.android.Model.LoginRequest;
import com.ucarry.developer.android.Model.LoginResponse;
import com.ucarry.developer.android.Model.Notifications;
import com.ucarry.developer.android.Model.OrderCompletion;
import com.ucarry.developer.android.Model.OrdersOfUser;
import com.ucarry.developer.android.Model.Otp;
import com.ucarry.developer.android.Model.QuoteRequest;
import com.ucarry.developer.android.Model.QuoteResponse;
import com.ucarry.developer.android.Model.SenderOrder;
import com.ucarry.developer.android.Model.SenderOrderRequest;
import com.ucarry.developer.android.Model.SenderOrderResponse;
import com.ucarry.developer.android.Model.SignUpResponse;
import com.ucarry.developer.android.Model.UpdateOrderRequest;
import com.ucarry.developer.android.Model.User;
import com.ucarry.developer.android.Model.UserUpdateRequest;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by skadavath on 8/26/2016.
 */
public interface ApiInterface {
    // @FormUrlEncoded
    @POST("auth/sign_in")
    Call<LoginResponse> getLogin(@Body LoginRequest loginRequest);

    @POST("auth")
    Call<SignUpResponse> getSignUp(@Body RequestBody body);

    @POST("auth/send_otp/{phone}")
    Call<Otp> getOtp(@Path("phone") String phoneNumber);

    @POST("auth/verify/{otp}/phone_number/{phone}")
    Call<Otp> verifyOtp(@Path("otp") String otp, @Path("phone") String phoneNumber);

    @POST("{flowtype}/{flowtypeParam}")
    Call<SenderOrderResponse> postSenderOrder(@Path("flowtype") String flowtype, @Path("flowtypeParam") String flowtypeParam, @Body SenderOrderRequest senderOrderRequest);

    @POST("carrier/schedule")
    Call<CarrierScheduleRequest > createSchedule(@Body CarrierScheduleRequest carrierScheduleRequest);


    @POST("sender/order")
    Call<SenderOrderResponse > createSenderOrder(@Body SenderOrderRequest senderOrderRequesteduleRequest);

    @GET("{flowtype}/{flowtypeParam1}/{flowtypeParam2}")
    Call<List<SenderOrder>> getAllSenderCarrierList(@Path("flowtype") String flowtype, @Path("flowtypeParam1") String flowtypeParam1 , @Path("flowtypeParam2") String flowtypeParam2 , @Query("from_loc") String from_loc , @Query(("to_loc")) String to_loc);

    @GET("{flowtype}/{flowtypeParam1}/{flowtypeParam2}")
    Call<List<CarrierSchedules>> getAllCarrierList(@Path("flowtype") String flowtype, @Path("flowtypeParam1") String flowtypeParam1 , @Path("flowtypeParam2") String flowtypeParam2, @Query("from_loc") String from_loc , @Query("to_loc") String to_loc, @Query("from_filter_date") String from_date , @Query("to_filter_date") String to_date);



    @GET("{flowtype}/{flowtypeParam}")
    Call<List<SenderOrder>> getSenderOrCarrierOrder(@Path("flowtype") String flowtype, @Path("flowtypeParam") String flowtypeParam , @Query("my_bay") String myBay , @Query("my_bay_completed") String myBayCompleted);

    @GET("/sender/orders/accepted_transactions")
    Call<List<SenderOrder>> getOrdersInProgress(@Query("my_bay") String myBay, @Query("my_bay_completed") String myBayCompleted);

    @GET("{flowtype}/{flowtypeParam}")
    Call<List<SenderOrder>> getSenderOrCarrierOrderForMyBay(@Path("flowtype") String flowtype, @Path("flowtypeParam") String flowtypeParam  , @Query("status") String status);


    @POST("orchestrator/quote")
    Call<QuoteResponse> getQuote(@Body QuoteRequest quoteRequest);

    @Multipart
    @PUT("orchestrator/image")
    Call<ImageUploadResponse> uploadFile(@Part MultipartBody.Part file, @Part("name")String name);


    @PUT("orchestrator/order/{orderId}/accept")
    Call<AcceptResponse>  acceptOrders(@Path("orderId") String orderId);

    @PUT("orchestrator/user/update")
    Call<User>  editUserDetails(@Body UserUpdateRequest request);

    @PUT("orchestrator/order/update")
    Call<JSONObject> updateOrder(@Body UpdateOrderRequest request);

    @PUT("/carrier/schedule/{schedule_id}/cancel")
     Call<JSONObject> cancelSchedule(@Path("schedule_id") String scheduleId);

    @PUT("/sender/order/{order_id}/cancel")
    Call<JSONObject> cancelOrder(@Path("order_id") String orderId);


    @GET("sender/orders}")
    Call<List<SenderOrder>> getMyBayHistory();

    @GET("orchestrator/user/detail")
    Call<User> getUserDetails();

    @PUT("orchestrator/order/{orderId}/accept")
    Call<AcceptOrderResponse> acceptOrder(@Path("orderId") String orderId);

    @POST("orchestrator/notify_carrier/{scheduleId}/order/{orderId}")
    Call<GenericResponse> notifyCarrier(@Path("scheduleId") String scheduleId , @Path("orderId") String orderId);

    @POST("auth/update_fcm")
    Call<User> updateFCM(@Body FCMRequest fcmRequest);

    @GET("auth/mobile/login")
    Call<User> doFbLogin(@Query("token") String token);

    @GET("orchestrator/notifications")
    Call<List<Notifications>> getNotifications();

    @POST("orchestrator/order/verify_completion")
    Call<JSONObject> completeOrder(@Body OrderCompletion completion);

    @POST("orchestrator/customer_support")
    Call<JSONObject> support(@Body ContactUs contactUs);

    @GET ("orchestrator/sender/orders")
    Call<List<OrdersOfUser>> getSenderOrders();

    @GET("sender/order/{orderId}")
    Call<SenderOrder> getOrderDetails(@Path("orderId") String orderId);

}
