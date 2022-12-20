package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {
    private int ID;
    private String Power;
    private String Name;
    private String Image;

    protected Mask(Parcel in) {
        ID = in.readInt();
        Power= in.readString();
        Name=in.readString();
        Image = in.readString();
    }


    public Mask(int ID, String Name, String Power, String Image) {
        this.ID=ID;
        this.Power=Power;
        this.Name=Name;
        this.Image=Image;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Name);
        parcel.writeString(Power);
        parcel.writeString(Image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPower() {
        return Power;
    }

    public void setPower(String power) {
        Power = power;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
