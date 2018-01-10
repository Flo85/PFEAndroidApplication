package fr.eseo.dis.nerriefl.pfeandroidapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flo_n on 02/01/2018.
 */

public class Project {
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
}
