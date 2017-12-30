package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.media.session.MediaSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Flo on 20/12/2017.
 */

public class JSONReader {

    private static HashMap<String, String> result;

    public static HashMap<String, String> read(InputStream inputStream) throws IOException, JSONException {
        JSONObject reader = new JSONObject(convertStreamToString(inputStream));

        result = new HashMap<String, String>();
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
                default:
                    break;
            }
        }

        return null;
    }

    private static void readLogon(JSONObject reader) throws JSONException {
        result.put("token", reader.getString("token"));
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
