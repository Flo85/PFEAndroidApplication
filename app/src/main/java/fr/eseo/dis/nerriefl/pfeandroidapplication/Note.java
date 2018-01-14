package fr.eseo.dis.nerriefl.pfeandroidapplication;

public class Note {
    private User user;
    private double myNote;
    private double averageNote;

    // Constructors
    public Note() {

    }

    public Note(User user, int myNote, int averageNote) {
        this.user = user;
        this.myNote = myNote;
        this.averageNote = averageNote;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getMyNote() {
        return myNote;
    }

    public void setMyNote(double myNote) {
        this.myNote = myNote;
    }

    public double getAverageNote() {
        return averageNote;
    }

    public void setAverageNote(double averageNote) {
        this.averageNote = averageNote;
    }
}
