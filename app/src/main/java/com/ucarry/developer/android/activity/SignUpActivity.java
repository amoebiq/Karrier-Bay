package com.ucarry.developer.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
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

import com.ucarry.developer.android.Model.Otp;
import com.ucarry.developer.android.Model.SignUpRequest;
import com.ucarry.developer.android.Model.SignUpResponse;
import com.ucarry.developer.android.RetroGit.ApiClient;
import com.ucarry.developer.android.RetroGit.ApiInterface;
import com.ucarry.developer.android.Utilities.BaseActivity;
import com.ucarry.developer.android.Utilities.SessionManager;
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

    private AppCompatDelegate delegate;

    private final String TAG = "SGINUP";

    boolean passwordsMatches = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        delegate = AppCompatDelegate.create(this,this);
//        delegate.onCreate(savedInstanceState);
//        delegate.setContentView(R.layout.activity_main);
//
//
//        //actionBarSetup();
//        System.out.println("here in main Activity");
//
      // android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.myToolbar);
//        delegate.setSupportActionBar(toolbar);




        //requestWindowFeature(Window.FEATURE_NO_TITLE);
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

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Log.d(TAG,charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String pass = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                if(pass.equals(confirmPass)) {
                    passwordsMatches = true;
                    //Toast.makeText(getApplicationContext(),"Password Matches",Toast.LENGTH_LONG).show();
                }
                else {
                    passwordsMatches = false;
                    //Toast.makeText(getApplicationContext(),"Password doesn't match",Toast.LENGTH_LONG).show();
                }
                Log.d(TAG,confirmPass);


            }
        });



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

        signUp.setEnabled(true);

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
                                phoneNumber.setEnabled(false);

                                signUp.setEnabled(true);

                            }
                            if (response.code() == 400) {

                                Log.d("LoginResponse", response.raw().request().url().toString());
                                Toast.makeText(getApplicationContext(), "Wrong OTP . Please try again", Toast.LENGTH_LONG).show();
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
        if (email.getText().length() == 0 || password.getText().length() == 0 || confirmPassword.getText().length() == 0 || phoneNumber.getText().length() == 0 || fullName.getText().length() == 0 || otp.getText().length() == 0 || !passwordsMatches) {
            if (fullName.getText().length() == 0) {
                fullName.setError("Enter your name");
            } if (email.getText().length() == 0) {
                email.setError("Enter valid email");
            } if (password.getText().length() == 0) {
                password.setError("Enter your password");
            } if (confirmPassword.getText().length() == 0) {
                confirmPassword.setError("Enter your password");
            } if (phoneNumber.getText().length() == 0) {
                phoneNumber.setError("Enter your phone number");
            } if (otp.getText().length() == 0) {
                otp.setError("Enter valid OTP");
            }
            if(!passwordsMatches) {
                confirmPassword.setError("Password Doesn't match");
            }
            return false;
        } else {
            return true;
        }
    }


}

