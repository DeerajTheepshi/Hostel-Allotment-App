package com.example.android.hostellallotment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.hostellallotment.ModelClasses.Items;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Items> {
    Context context;
    List<Items> objects;

    public CustomAdapter(Context context, List<Items> objects) {
        super(context,0, objects);
        this.context=context;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.user_list,parent,false);
        }
        Items curItem = objects.get(position);

        TextView userName = (TextView)view.findViewById(R.id.userNameL);
        TextView roomNo = (TextView)view.findViewById(R.id.roomL);
        TextView floorNo = (TextView)view.findViewById(R.id.floorL);

        userName.setText(curItem.getUser());
        roomNo.setText(String.valueOf(curItem.getRoomAllotted()));
        floorNo.setText(map(curItem.getFloorAllotted()));

        return view;
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
