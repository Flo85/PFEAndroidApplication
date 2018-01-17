package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Jury implements Parcelable {
    private int id;
    private String date;
    private List<User> members;
    private List<Project> projects;

    // Constructors
    public Jury() {
        this.members = new ArrayList<User>();
        this.projects = new ArrayList<Project>();
    }

    public Jury(int id) {
        this();
        this.id = id;
    }

    public Jury(int id, String date, List<User> members, List<Project> projects) {
        this(id);
        this.date = date;
        this.members = members;
        this.projects = projects;
    }

    protected Jury(Parcel in) {
        id = in.readInt();
        date = in.readString();
        members = in.createTypedArrayList(User.CREATOR);
        projects = in.createTypedArrayList(Project.CREATOR);
    }

    public static final Creator<Jury> CREATOR = new Creator<Jury>() {
        @Override
        public Jury createFromParcel(Parcel in) {
            return new Jury(in);
        }

        @Override
        public Jury[] newArray(int size) {
            return new Jury[size];
        }
    };

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    // Methods
    public void addMember(User member) {
        members.add(member);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public boolean isMember(User user) {
        int i = 0;
        while(i < members.size()) {
            if(members.get(i).getForeName().equals(user.getForeName()) && members.get(i).getSurName().equals(user.getSurName())) {
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeTypedList(members);
        dest.writeTypedList(projects);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
