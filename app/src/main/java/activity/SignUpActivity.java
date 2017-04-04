package activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yourapp.developer.karrierbay.R;

import java.io.IOException;

import Model.Otp;
import Model.SignUpRequest;
import Model.SignUpResponse;
import RetroGit.ApiClient;
import RetroGit.ApiInterface;
import Utilities.BaseActivity;
import Utilities.SessionManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends BaseActivity {


    private Button signUp;
    private TextView story1, terms, story2, privacy;
    private EditText fullName, phoneNumber, otp, password, email, confirmPassword;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        signUp = (Button) findViewById(R.id.sign_up_button);
        story1 = (TextView) findViewById(R.id.story1);
        terms = (TextView) findViewById(R.id.terms);
        story2 = (TextView) findViewById(R.id.story2);
        privacy = (TextView) findViewById(R.id.privacy);
        fullName = (EditText) findViewById(R.id.full_name);
        phoneNumber = (EditText) findViewById(R.id.mobile_phone_number);
        otp = (EditText) findViewById(R.id.otp);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);

        signUp.setTypeface(mTfBold);
        story1.setTypeface(mTfRegular);
        terms.setTypeface(mTfSemiBold);
        story2.setTypeface(mTfRegular);
        privacy.setTypeface(mTfSemiBold);
        fullName.setTypeface(mTfSemiBold);
        phoneNumber.setTypeface(mTfSemiBold);
        otp.setTypeface(mTfSemiBold);
        password.setTypeface(mTfSemiBold);
        email.setTypeface(mTfSemiBold);
        confirmPassword.setTypeface(mTfSemiBold);

        sessionManager = new SessionManager(getApplicationContext());

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.i("checking the i value", "i value is " + i + ".");
                if (i == 9) {
                    Call<Otp> call = apiService.getOtp("+91" + phoneNumber.getText().toString());
                    call.enqueue(new Callback<Otp>() {
                        @Override
                        public void onResponse(Call<Otp> call, Response<Otp> response) {
                            Log.d("LoginResponse", response.body().getMessage().toString());
                            Toast.makeText(getApplicationContext(), response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Otp> call, Throwable t) {
                            Log.d("failure", t.toString());

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("checking the i value", "i value is " + i + ".");
                if (i == 4) {
                    Call<Otp> call = apiService.verifyOtp(otp.getText().toString(), "+91" + phoneNumber.getText().toString());
                    call.enqueue(new Callback<Otp>() {
                        @Override
                        public void onResponse(Call<Otp> call, Response<Otp> response) {
                            if (response.code() == 200) {
                                Log.d("LoginResponse", response.body().toString());
                                Toast.makeText(getApplicationContext(), response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                            if (response.code() == 400) {

                                Log.d("LoginResponse", response.raw().request().url().toString());
                                Toast.makeText(getApplicationContext(), "bad response", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Otp> call, Throwable t) {
                            Log.d("failure", t.toString());

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validate = Validation();
                if (validate) {
                    String signinRequest = new Gson().toJson(new SignUpRequest(email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString(), phoneNumber.getText().toString(), "http://asss.com", fullName.getText().toString()));
                    Log.d("LoginResponse", signinRequest);
                    RequestBody body =
                            RequestBody.create(MediaType.parse("application/json"), signinRequest);
                    Call<SignUpResponse> call = apiService.getSignUp(body);
                    call.enqueue(new Callback<SignUpResponse>() {
                        @Override
                        public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                            if (response.errorBody() == null) {
                                Log.d("LoginResponse", response.body().getStatus().toString());
                                sessionManager.createLoginSession(response.body().getData().getEmail().toString(), response.body().getData().getName().toString(),response.headers(),response.body().getData().getPhone().toString());
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                finish();
                            } else {
                                try {
                                    // sessionManager.createLoginSession(response.errorBody().string().toString(),response.body().getData().getName().toString());
                                    // Toast.makeText(getApplicationContext(),response.errorBody().string().toString(),Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), response.errorBody().string().toString(), Toast.LENGTH_LONG).show();
                                    //  startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                    //  finish();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SignUpResponse> call, Throwable t) {
                            Log.d("failure", t.toString());

                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onResume() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        super.onResume();
    }
    public boolean Validation() {
        if (email.getText().length() == 0 || password.getText().length() == 0 || confirmPassword.getText().length() == 0 || phoneNumber.getText().length() == 0 || fullName.getText().length() == 0 || otp.getText().length() == 0) {
            if (fullName.getText().length() == 0) {
                fullName.setError("Enter your name");
            } else if (email.getText().length() == 0) {
                email.setError("Enter valid email");
            } else if (password.getText().length() == 0) {
                password.setError("Enter your password");
            } else if (confirmPassword.getText().length() == 0) {
                confirmPassword.setError("Enter your password");
            } else if (phoneNumber.getText().length() == 0) {
                phoneNumber.setError("Enter your phone number");
            } else if (otp.getText().length() == 0) {
                otp.setError("Enter valid OTP");
            }
            return false;
        } else {
            return true;
        }
    }


}

