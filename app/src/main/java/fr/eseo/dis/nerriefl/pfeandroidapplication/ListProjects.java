package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListProjects extends Fragment {

    private String login;
    private String token;
    private ListView listViewProjects;

    public ListProjects (){
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ListView getListViewProjects() {
        return listViewProjects;
    }

    public void setListViewProjects(ListView listViewProjects) {
        this.listViewProjects = listViewProjects;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list_projects, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Projets");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("ListProjects", "Lecture tous les projets");
        InputStream inputStream = WebService.liprj(this.getContext(), ((MainActivity) getActivity()).getLogin(),
                ((MainActivity) getActivity()).getToken());
        HashMap<String, Object> response = null;
        if (inputStream != null) {
            Log.d("ListProjects", "inputStream != null");
            try {
                response = JSONReader.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (response != null && "LIPRJ".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                Log.d("ListProjects", "response != null");
                List<Project> projects = (List) response.get("projects");

                List<HashMap<String,String>> listItem = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> item;

                for(Project project : projects){
                    item = new HashMap<>();
                    item.put("project_title", project.getTitle());
                    listItem.add(item);
                }
                Log.d("ListProjects", "Tous les projets : " + projects.size());
                for (int i = 0; i < projects.size(); i++) {
                    Log.d("ListProjects", projects.get(i).getTitle());
                }
                listViewProjects = view.findViewById(R.id.list_projects);
                SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(),listItem, R.layout.view_project,
                        new String[]{"project_title"}, new int[]{R.id.project_title});
                listViewProjects.setAdapter(simpleAdapter);
            }
            Log.d("ListProjects", "Fin lecture tous les projets");
        }
    }
}
