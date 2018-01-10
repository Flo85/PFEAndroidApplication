package fr.eseo.dis.nerriefl.pfeandroidapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flo_n on 10/01/2018.
 */

public class Jury {
    private int id;
    private String date;
    private List<Project> projects;

    // Constructors
    public Jury() {
        this.projects = new ArrayList<Project>();
    }

    public Jury(int id) {
        this();
        this.id = id;
    }

    public Jury(int id, String date, List<Project> projects) {
        this(id);
        this.date = date;
        this.projects = projects;
    }

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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    // Method
    public void addProject(Project project) {
        projects.add(project);
    }
}
