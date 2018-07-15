package com.example.android.hostellallotment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hostellallotment.ModelClasses.Main;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText PasswordView, UserView;
    private View ProgressView;
    private View LoginFormView;
    int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        UserView = (EditText) findViewById(R.id.email);

        PasswordView = (EditText) findViewById(R.id.password);
        PasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    state=0;
                    login();
                    return true;
                }
                return false;
            }
        });

        Button SignInButton = (Button) findViewById(R.id.email_sign_in_button);
        SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,register.class);
                startActivity(intent);
            }
        });

        LoginFormView = findViewById(R.id.login_form);
        ProgressView = findViewById(R.id.login_progress);
    }

    public void login(){
        final String userName = UserView.getText().toString();
        String password = PasswordView.getText().toString();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final Api service = retrofit.create(Api.class);

        String body1 = "username="+userName+"&&password="+password+"&&login=Send";
        Call<Main> body = service.getLoginInfo(body1);

        body.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                final String user = response.body().getItems().get(0).getUser();
                final String phone = response.body().getItems().get(0).getPhone();
                if(user!=null && state==0){
                    final String body = "Username=" + userName + "&&placeholer=dummy";
                    Call<Main> data = service.getARequest(body);
                    data.enqueue(new Callback<Main>() {
                        @Override
                        public void onResponse(Call<Main> call, Response<Main> response) {
                            String message = response.body().getMessage();
                            if (message != null) {
                                Intent intent = new Intent(LoginActivity.this,makeinitialRequest.class);
                                intent.putExtra("userName",user);
                                intent.putExtra("phone",phone);
                                state=1;
                                startActivity(intent);
                            }
                            else{
                                Intent intent  = new Intent(LoginActivity.this,welcome.class);
                                intent.putExtra("userName",userName);
                                intent.putExtra("phone",phone);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<Main> call, Throwable t) {

                        }

                    });

                    state=1;

                }
                else if(user==null){
                    String message = response.body().getMessage();
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Log.v("1234",t+"");
            }
        });
    }
}