package com.example.android.hostellallotment.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.hostellallotment.Api;
import com.example.android.hostellallotment.CustomAdapter;
import com.example.android.hostellallotment.ModelClasses.Items;
import com.example.android.hostellallotment.ModelClasses.Main;
import com.example.android.hostellallotment.R;
import com.example.android.hostellallotment.infoPage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class match_fragment extends Fragment {

    CustomAdapter adapter;

    public match_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_match,container,false);

        final ListView matchList = (ListView) root.findViewById(R.id.matchList);

        matchList.setEmptyView(root.findViewById(R.id.emptyView));

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final Api service = retrofit.create(Api.class);
        String userName = getActivity().getIntent().getExtras().getString("userName");
        final String body = "Username=" + userName + "&&placeholer=dummy";
        Call<Main> floorRequest = service.getARequest(body);
        floorRequest.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                int floorNeed = response.body().getItems().get(0).getFloorNeeed();
                String message = response.body().getMessage();
                if(message==null) {
                    final Call<Main> matchCall = service.findMatch("match/" + floorNeed);
                    matchCall.enqueue(new Callback<Main>() {
                        @Override
                        public void onResponse(Call<Main> call, Response<Main> response) {
                            List<Items> itemsList = response.body().getItems();
                            adapter = new CustomAdapter(getActivity(), itemsList);
                            matchList.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<Main> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {

            }
        });

        matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Items curItem = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), infoPage.class);
                intent.putExtra("item",curItem);
                startActivity(intent);
            }
        });



        return root;
    }
}
