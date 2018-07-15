package com.example.android.hostellallotment.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.hostellallotment.Api;
import com.example.android.hostellallotment.ModelClasses.Items;
import com.example.android.hostellallotment.ModelClasses.Main;
import com.example.android.hostellallotment.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class makeARequest extends Fragment {

    String userName, phoneNumber, hostel, notes, roomAll, roomNeed,floorAll, floorNeed;

    public makeARequest() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userName = getActivity().getIntent().getExtras().getString("userName");
        phoneNumber = getActivity().getIntent().getExtras().getString("phone");
        final View root = inflater.inflate(R.layout.fragment_welcome, container, false);
        final TextView UN = (TextView) root.findViewById(R.id.userNameT);
        final TextView PH = (TextView) root.findViewById(R.id.phoneNumberT);
        final TextView FA = (TextView) root.findViewById(R.id.floorAllT);
        final TextView FN = (TextView) root.findViewById(R.id.floorNeedT);
        final TextView RA = (TextView) root.findViewById(R.id.roomAllT);
        final TextView RN = (TextView) root.findViewById(R.id.roomNeedT);
        final TextView HN = (TextView) root.findViewById(R.id.hostelT);
        final TextView NT = (TextView) root.findViewById(R.id.notesT);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api service = retrofit.create(Api.class);
        final String body = "Username=" + userName + "&&placeholer=dummy";
        Call<Main> data = service.getARequest(body);
        data.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                String message = response.body().getMessage();
                if (message == null) {
                    Items items = response.body().getItems().get(0);
                    roomAll = String.valueOf(items.getRoomAllotted());
                    roomNeed = String.valueOf(items.getRoomNeed());
                    floorNeed = (map(items.getFloorNeeed()));
                    floorAll = map(items.getFloorAllotted());
                    notes = items.getNotes();
                    Log.v("1234","ROOMS: "+items.getRoomAllotted());
                    hostel = items.getHostelName();
                    UN.setText(userName);
                    PH.setText(phoneNumber);
                    FA.setText((floorAll));
                    FN.setText((floorNeed));
                    RN.setText((roomNeed));
                    RA.setText(roomAll);
                    HN.setText(hostel);
                    NT.setText(notes);

                }
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {

            }

        });

        return root;
    }

    public String map(int Floor){
        switch (Floor){
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
}
