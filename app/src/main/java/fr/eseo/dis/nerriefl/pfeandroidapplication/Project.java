package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flo_n on 02/01/2018.
 */

public class Project implements Parcelable {
    private int id;
    private String title;
    private String description;
    private int confidentiality;
    private boolean posterCommited;

    private User supervisor;
    private List<User> students;
    private String poster;

    // Constructors
    public Project() {
        students = new ArrayList<User>();
    }

    public Project(int id) {
        this();
        this.id = id;
    }

    public Project(int id, String title, String description, int confidentiality, boolean posterCommited) {
        this(id);
        this.title = title;
        this.description = description;
        this.confidentiality = confidentiality;
        this.posterCommited = posterCommited;
    }

    protected Project(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        confidentiality = in.readInt();
        posterCommited = in.readByte() != 0;
        supervisor = in.readParcelable(User.class.getClassLoader());
        students = in.createTypedArrayList(User.CREATOR);
        poster = in.readString();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(int confidentiality) {
        this.confidentiality = confidentiality;
    }

    public boolean isPosterCommited() {
        return posterCommited;
    }

    public void setPosterCommited(boolean posterCommited) {
        this.posterCommited = posterCommited;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    // Method
    public void addStudent(User student) {
        students.add(student);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(confidentiality);
        dest.writeByte((byte) (posterCommited ? 1 : 0));
        dest.writeParcelable(supervisor, flags);
        dest.writeList(students);
        dest.writeString(poster);
    }
}
