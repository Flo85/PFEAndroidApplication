package fr.eseo.dis.nerriefl.pfeandroidapplication;

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

/**
 * Created by Flo on 20/12/2017.
 */

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
                    readLiprj(reader);
                    return result;
                default:
            }
        }

        return null;
    }

    private static void readLogon(JSONObject reader) throws JSONException {
        result.put("token", reader.getString("token"));
    }

    private static void readLiprj(JSONObject reader) throws JSONException {
        List<Project> projects = new ArrayList<>();
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
