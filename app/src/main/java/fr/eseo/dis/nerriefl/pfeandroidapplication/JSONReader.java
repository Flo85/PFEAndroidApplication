package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class JSONReader {

    private static HashMap<String, Object> result;

    public static HashMap<String, Object> read(InputStream inputStream) throws IOException, JSONException {
        JSONObject reader = new JSONObject(convertStreamToString(inputStream));

        result = new HashMap<String, Object>();
        result.put("result", reader.getString("result"));
        result.put("api", reader.getString("api"));

        if ("KO".equals(reader.getString("result"))) {
            result.put("error", reader.getString("error"));
            return result;
        } else {
            switch (reader.getString("api")) {
                case "LOGON":
                    readLogon(reader);
                    return result;
                case "LIPRJ":
                    readLiprjMyprjJyinf(reader);
                    return result;
                case "MYPRJ":
                    readLiprjMyprjJyinf(reader);
                    return result;
                case "LIJUR":
                    readLijurMyjur(reader);
                    return result;
                case "MYJUR":
                    readLijurMyjur(reader);
                    return result;
                case "JYINF":
                    readLiprjMyprjJyinf(reader);
                    return result;
                case "NOTES":
                    readNotes(reader);
                    return result;
                case "NEWNT":
                    return result;
                case "PORTE":
                    readPorte(reader);
                    return result;
            }
            return null;
        }
    }

    private static void readLogon(JSONObject reader) throws JSONException {
        result.put("token", reader.getString("token"));
    }

    private static void readLiprjMyprjJyinf(JSONObject reader) throws JSONException {
        List<Project> projects = new ArrayList<Project>();
        JSONArray liprj = reader.getJSONArray("projects");
        for (int i = 0; i < liprj.length(); i++) {
            Project project = new Project();
            JSONObject prj = liprj.getJSONObject(i);

            project.setId(prj.getInt("projectId"));
            project.setTitle(prj.getString("title"));
            project.setDescription(prj.getString("descrip"));
            project.setPosterCommited(prj.getBoolean("poster"));
            project.setConfidentiality(prj.getInt("confid"));

            JSONObject supervisor = prj.getJSONObject("supervisor");
            project.setSupervisor(new User(supervisor.getString("forename"), supervisor.getString("surname")));

            JSONArray students = prj.getJSONArray("students");
            for (int j = 0; j < students.length(); j++) {
                JSONObject student = students.getJSONObject(j);

                project.addStudent(new User(student.getInt("userId"), student.getString("forename"), student.getString("surname")));
            }

            projects.add(project);
        }
        result.put("projects", projects);
    }

    private static void readLijurMyjur(JSONObject reader) throws JSONException {
        List<Jury> juries = new ArrayList<Jury>();
        JSONArray lijur = reader.getJSONArray("juries");
        for (int i = 0; i < lijur.length(); i++) {
            Jury jury = new Jury();
            JSONObject jur = lijur.getJSONObject(i);

            jury.setId(jur.getInt("idJury"));
            jury.setDate(jur.getString("date"));

            JSONObject info = jur.getJSONObject("info");
            JSONArray members = info.getJSONArray("members");
            for(int j = 0; j < members.length(); j++){
                JSONObject member = members.getJSONObject(j);
                jury.addMember(new User(member.getString("forename"), member.getString("surname")));
            }

            JSONArray projects = info.getJSONArray("projects");
            for (int j = 0; j < projects.length(); j++) {
                JSONObject project = projects.getJSONObject(j);
                Project prj = new Project();

                prj.setId(project.getInt("projectId"));
                prj.setTitle(project.getString("title"));
                prj.setPosterCommited(project.getBoolean("poster"));
                prj.setConfidentiality(project.getInt("confid"));

                JSONObject supervisor = project.getJSONObject("supervisor");
                prj.setSupervisor(new User(supervisor.getString("forename"), supervisor.getString("surname")));

                jury.addProject(prj);
            }

            juries.add(jury);
        }
        result.put("juries", juries);
    }

    private static void readNotes(JSONObject reader) throws JSONException {
        List<Note> notes = new ArrayList<Note>();
        JSONArray linotes = reader.getJSONArray("notes");
        for (int i = 0; i < linotes.length(); i++) {
            Note note = new Note();
            JSONObject notation = linotes.getJSONObject(i);

            note.setUser(new User(notation.getInt("userId"), notation.getString("forename"), notation.getString("surname")));
            if(notation.get("mynote") instanceof Double){
                note.setMyNote(notation.getDouble("mynote"));
            } else if(notation.get("mynote") instanceof Long) {
                note.setMyNote(notation.getLong("mynote"));
            }
            if(notation.get("avgNote") instanceof Double){
                note.setAverageNote(notation.getDouble("avgNote"));
            } else if(notation.get("avgNote") instanceof Long) {
                note.setAverageNote(notation.getLong("avgNote"));
            }

            notes.add(note);
        }
        result.put("notes", notes);
    }

    private static void readPorte(JSONObject reader) throws JSONException {
        List<Project> projects = new ArrayList<Project>();
        JSONArray liprj = reader.getJSONArray("projects");
        for (int i = 0; i < liprj.length(); i++) {
            Project project = new Project();
            JSONObject prj = liprj.getJSONObject(i);

            project.setId(prj.getInt("idProject"));
            project.setTitle(prj.getString("title"));
            project.setDescription(prj.getString("description"));
            project.setPoster(prj.getString("poster"));

            projects.add(project);
        }
        result.put("seed", reader.getInt("seed"));
        result.put("projects", projects);
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
