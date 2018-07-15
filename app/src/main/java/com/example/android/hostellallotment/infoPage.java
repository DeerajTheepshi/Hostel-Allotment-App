package com.example.android.hostellallotment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.hostellallotment.ModelClasses.Items;

public class infoPage extends AppCompatActivity {

    String phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView UN = (TextView) findViewById(R.id.userNameT);
        TextView FA = (TextView) findViewById(R.id.floorAllT);
        TextView FN = (TextView) findViewById(R.id.floorNeedT);
        TextView RA = (TextView) findViewById(R.id.roomAllT);
        TextView RN = (TextView) findViewById(R.id.roomNeedT);
        TextView HN = (TextView) findViewById(R.id.hostelT);
        TextView NT = (TextView) findViewById(R.id.notesT);
        ImageView PH = (ImageView) findViewById(R.id.images);


        Items items = (Items) getIntent().getExtras().getSerializable("item");
        String userName = items.getUser();
        phone = "+91"+items.getPhone();
        String roomAll = String.valueOf(items.getRoomAllotted());
        String roomNeed = String.valueOf(items.getRoomNeed());
        String floorNeed = (map(items.getFloorNeeed()));
        String floorAll = map(items.getFloorAllotted());
        String notes = items.getNotes();
        String hostel = items.getHostelName();
        UN.setText(userName);
        FA.setText((floorAll));
        FN.setText((floorNeed));
        RN.setText((roomNeed));
        RA.setText(roomAll);
        HN.setText(hostel);
        NT.setText(notes);

        PH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber(phone);
            }
        });

    }

    public String map(int Floor) {
        switch (Floor) {
            case 0:
                return "Ground Floor";
            case 1:
                return "First Floor";
            case 2:
                return "Second Floor";
            case 3:
                return "Third Floor";
            default:
                return "No Preference";
        }
    }

    public void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(infoPage.this, new String[]{Manifest.permission.CALL_PHONE},0);
            }
            else {
                startActivity(intent);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0 : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhoneNumber(phone);
                }
            }
        }
    }
}
