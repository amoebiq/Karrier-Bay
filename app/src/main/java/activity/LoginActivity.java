package activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yourapp.developer.karrierbay.R;

import Model.LoginRequest;
import Model.LoginResponse;
import RetroGit.ApiClient;
import RetroGit.ApiInterface;
import Utilities.BaseActivity;
import Utilities.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {


    private TextView SignUp, haveAccount, forgotPassword;
    private Button signIn;
    private EditText email, password;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        SignUp = (TextView) findViewById(R.id.sign_up);
        haveAccount = (TextView) findViewById(R.id.account);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signIn = (Button) findViewById(R.id.email_sign_in_button);
        email = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);

        signIn.setTypeface(mTfBold);
        haveAccount.setTypeface(mTfRegular);
        SignUp.setTypeface(mTfSemiBold);
        forgotPassword.setTypeface(mTfRegular);
        email.setTypeface(mTfSemiBold);
        password.setTypeface(mTfRegular);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.checkLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean validate = Validation();
                if (validate) {
                    Call<LoginResponse> call = apiService.getLogin(new LoginRequest(email.getText().toString(), password.getText().toString()));
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            if (response.code() == 200) {
                                Log.d("LoginResponse", response.body().getData().getEmail().toString());

                                // Log.d("Error",response.body().getErrors().toString());
                                sessionManager.createLoginSession(response.body().getData().getEmail().toString(),
                                        response.body().getData().getName().toString(), response.headers(), response.body().getData().getPhone().toString());
                                Toast.makeText(getApplicationContext(), response.body().getData().getEmail().toString(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        // Set up the login form.

    }

    @Override
    protected void onResume() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        super.onResume();
    }

    public boolean Validation() {
        if (email.getText().length() == 0 || password.getText().length() == 0) {
            if (email.getText().length() == 0) {
                email.setError("Enter valid email");
            } else {
                password.setError("Enter your password");
            }
            return false;
        } /*else if (email.getText().length() == 0) {
            email.setError("Enter valid email");
            return false;
        } else if (password.getText().length() == 0) {
            password.setError("Enter your password");
            return false;
        }*/ else {
            return true;
        }
    }

}

