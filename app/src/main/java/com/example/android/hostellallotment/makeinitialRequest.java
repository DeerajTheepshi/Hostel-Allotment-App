package com.example.android.hostellallotment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.hostellallotment.ModelClasses.Main;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class makeinitialRequest extends AppCompatActivity {
    Spinner hostelSpinner, floorSpinner1, floorSpinner2;
    EditText roomAllo, roomNeeded, notesTxt;
    String userName, phoneNumber, hostel, notes, roomAll, roomNeed;
    int floorAll, floorNeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reques);

        roomAllo = (EditText) findViewById(R.id.roomAll);
        roomNeeded = (EditText) findViewById(R.id.roomNeed);
        notesTxt = (EditText) findViewById(R.id.notes);
        hostelSpinner = (Spinner) findViewById(R.id.hostelSpinner);
        floorSpinner1 = (Spinner) findViewById(R.id.FloorGiven);
        floorSpinner2 = (Spinner) findViewById(R.id.FloorNeed);

        ArrayAdapter floorAdapter1 = ArrayAdapter.createFromResource(this, R.array.floorAll, android.R.layout.simple_spinner_item);
        ArrayAdapter floorAdapter2 = ArrayAdapter.createFromResource(this, R.array.floorNeed, android.R.layout.simple_spinner_item);
        ArrayAdapter hostelAdapter = ArrayAdapter.createFromResource(this, R.array.hostel, android.R.layout.simple_spinner_item);

        floorAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        floorAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        hostelAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        hostelSpinner.setAdapter(hostelAdapter);
        floorSpinner1.setAdapter(floorAdapter1);
        floorSpinner2.setAdapter(floorAdapter2);



        userName = getIntent().getExtras().getString("userName");
        phoneNumber = getIntent().getExtras().getString("phone");
        makeRequestInitial();
    }

    public void putIntoServer(String body) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api service = retrofit.create(Api.class);
        Call<Main> data = service.makeARequest(body);
        data.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                String message = response.body().getMessage();
                Toast.makeText(makeinitialRequest.this, message, Toast.LENGTH_LONG).show();
                Intent intent  = new Intent(makeinitialRequest.this,welcome.class);
                intent.putExtra("userName",userName);
                intent.putExtra("phone",phoneNumber);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {

            }
        });
    }

    public void makeRequestInitial() {
        Button but = (Button) findViewById(R.id.reqButton);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                userName = extras.getString("userName");
                phoneNumber = extras.getString("phone");
                floorAll = mapS(floorSpinner1.getSelectedItem().toString());
                floorNeed = mapS(floorSpinner2.getSelectedItem().toString());
                roomAll = roomAllo.getText().toString();
                roomNeed = roomNeeded.getText().toString();
                hostel = hostelSpinner.getSelectedItem().toString();
                notes = notesTxt.getText().toString();

                final String body = "userName=" + userName + "&&phone=" + phoneNumber
                        + "&&roomAll=" + roomAll + "&&roomNeed=" + roomNeed
                        + "&&floorNeed=" + floorNeed + "&&hostel=" + hostel
                        + "&&notes=" + notes + "&&floorAll=" + floorAll;

                Log.v("1234",body);
                putIntoServer(body);
            }
        });
    }

    public int mapS(String floor){
        switch (floor){
            case "Ground Floor":
                return 0;
            case "First Floor":
                return 1;
            case "Second Floor":
                return 2;
            case "Third Floor":
                return 3;
            default:
                return -1;

        }
    }
}

