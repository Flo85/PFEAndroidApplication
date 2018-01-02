package fr.eseo.dis.nerriefl.pfeandroidapplication;

/**
 * Created by flo_n on 02/01/2018.
 */

public class User {
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
}
