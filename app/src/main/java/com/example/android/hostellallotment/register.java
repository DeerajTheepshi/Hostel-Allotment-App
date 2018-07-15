package com.example.android.hostellallotment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.hostellallotment.ModelClasses.Main;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register extends AppCompatActivity {
    EditText UN,PASS,PASS1,PHONE;
    Button register;
    int state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        UN = (EditText) findViewById(R.id.username);
        PASS = (EditText) findViewById(R.id.password);
        PASS1 = (EditText) findViewById(R.id.password1);
        PHONE = (EditText) findViewById(R.id.phone);
        register = (Button) findViewById(R.id.email_sign_in_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=0;
                register();
            }
        });
    }

    public void register(){
        String userName = UN.getText().toString();
        String passWord = PASS.getText().toString();
        String passWord_check = PASS1.getText().toString();
        if(!passWord.equals(passWord_check) ){
            Toast.makeText(this, "PASSWORDS DO NOT MATCH",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(state==0){
            String phone = PHONE.getText().toString();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            Api service = retrofit.create(Api.class);
            String body = "username="+userName+"&&password="+passWord+"&&phone="+phone+"&&login=Send";
            Call<Main> message = service.registerANewUser(body);
            message.enqueue(new Callback<Main>() {
                @Override
                public void onResponse(Call<Main> call, Response<Main> response) {
                    String message = response.body().getMessage();
                    if(message.equals("REGISTRATION SUCCESS")){
                        UN.setText("");
                        PASS.setText("");
                        PASS1.setText("");
                        PHONE.setText("");
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.Coord),"Registration Success",Snackbar.LENGTH_LONG);
                        snackbar.setAction("Login Now", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent  = new Intent(register.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Call<Main> call, Throwable t) {
                    Log.v("1234",t+"");
                }
            });
            state=1;
        }
    }
}
