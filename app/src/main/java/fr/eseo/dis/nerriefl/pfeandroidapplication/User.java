package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by flo_n on 02/01/2018.
 */

public class User implements Parcelable {
    private int id;
    private String foreName;
    private String surName;

    // Constructors
    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String foreName, String surName) {
        this.foreName = foreName;
        this.surName = surName;
    }

    public User(int id, String foreName, String surName) {
        this(id);
        this.foreName = foreName;
        this.surName = surName;
    }

    protected User(Parcel in) {
        id = in.readInt();
        foreName = in.readString();
        surName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Getters ans setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForeName() {
        return foreName;
    }

    public void setForeName(String foreName) {
        this.foreName = foreName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(foreName);
        dest.writeString(surName);
    }
}
