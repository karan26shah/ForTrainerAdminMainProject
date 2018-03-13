package in.fortrainer.admin.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.userLogin;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.b_Login);
        mLogin.setOnClickListener((view) -> {
            userloginin();
        });
    }
    private void userloginin(){

        mUsername.setError(null);
        mPassword.setError(null);

        final String login = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        userLogin userLogin = new userLogin();
        userLogin.setPassword(mPassword.getText().toString());
        userLogin.setLogin(mUsername.getText().toString());
        Call<JsonObject> jsonObjectCall = RetrofitHelper.getRetrofitService(context).loginin(userLogin);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                int statusCode = response.code();
                JsonObject user = response.body();
                if ( response.isSuccessful()){
                    JsonObject jsonObject = response.body();
                   // String authKey = jsonObject.get("auth_key").getAsString();
                    launchActivity();
                    Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"fail",Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void launchActivity() {
        Intent profileintent1 = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(profileintent1);
        finish();


    }


}
