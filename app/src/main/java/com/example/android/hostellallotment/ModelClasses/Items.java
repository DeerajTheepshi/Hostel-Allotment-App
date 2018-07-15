package com.example.android.hostellallotment.ModelClasses;

import java.io.Serializable;

public class Items implements Serializable {
    public final String phone;
    public final String user;
    public final int roomAlloted;
    public final int roomNeeded;
    public final int floorNeeed;
    public final String hostelName;
    public final String notes;
    public final int floorAllotted;

    public String getPhone() {
        return phone;
    }

    public String getUser() {
        return user;
    }

    public int getRoomAllotted() {
        return roomAlloted;
    }

    public int getRoomNeed() {
        return roomNeeded;
    }

    public int getFloorNeeed() {
        return floorNeeed;
    }

    public String getHostelName() {
        return hostelName;
    }

    public String getNotes() {
        return notes;
    }

    public int getFloorAllotted() {
        return floorAllotted;
    }

    public Items(String phone, String user, int roomAllotted, int roomNeed, int floorNeeed, String hostelName, String notes, int floorAllotted) {

        this.phone = phone;
        this.user = user;
        this.roomAlloted = roomAllotted;
        this.roomNeeded = roomNeed;
        this.floorNeeed = floorNeeed;
        this.hostelName = hostelName;
        this.notes = notes;
        this.floorAllotted = floorAllotted;
    }
}
