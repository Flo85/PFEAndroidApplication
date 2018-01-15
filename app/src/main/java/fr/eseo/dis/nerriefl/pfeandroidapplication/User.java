package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class User implements Parcelable {
    private int id;
    private String foreName;
    private String surName;

    private String token;
    private String login;
    private String password;

    private List<Project> projectsSupervised;
    private List<Project> projectsToEvaluate;

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
        token = in.readString();
        login = in.readString();
        password = in.readString();
        projectsSupervised = in.createTypedArrayList(Project.CREATOR);
        projectsToEvaluate = in.createTypedArrayList(Project.CREATOR);
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Project> getProjectsSupervised() {
        return projectsSupervised;
    }

    public void setProjectsSupervised(List<Project> projectsSupervised) {
        this.projectsSupervised = projectsSupervised;
    }

    public List<Project> getProjectsToEvaluate() {
        return projectsToEvaluate;
    }

    public void setProjectsToEvaluate(List<Project> projectsToEvaluate) {
        this.projectsToEvaluate = projectsToEvaluate;
    }

    // Methods
    public boolean isProjectInProjectsSupervised(int projectId) {
        int i = 0;
        while(i < projectsSupervised.size()) {
            if(projectId == projectsSupervised.get(i).getId()) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean isProjectInProjectsToEvaluate(int projectId) {
        int i = 0;
        while(i < projectsToEvaluate.size()) {
            if(projectId == projectsToEvaluate.get(i).getId()) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean isProjectDetailsAvailable(int projectId) {
        return isProjectInProjectsSupervised(projectId) || isProjectInProjectsToEvaluate(projectId);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(foreName);
        dest.writeString(surName);
        dest.writeString(token);
        dest.writeString(login);
        dest.writeString(password);
        dest.writeTypedList(projectsSupervised);
        dest.writeTypedList(projectsToEvaluate);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
