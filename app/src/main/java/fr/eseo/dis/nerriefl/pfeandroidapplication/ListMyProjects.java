package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class ListMyProjects extends Fragment {
    private ListView listViewMyProjects;

    public ListMyProjects (){
    }

    public ListView getListViewProjects() {
        return listViewMyProjects;
    }

    public void setListViewProjects(ListView listViewProjects) {
        this.listViewMyProjects = listViewMyProjects;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(" Mes Projets");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InputStream inputStream = WebService.myprj(this.getContext(), ((MainActivity) getActivity()).getLogin(),
                ((MainActivity) getActivity()).getToken());
        HashMap<String, Object> response = null;
        if (inputStream != null) {
            try {
                response = JSONReader.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (response != null && "MYPRJ".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                List<Project> projects = (List) response.get("projects");

                List<HashMap<String,String>> listItem = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> item;

                for(Project project : projects){
                    item = new HashMap<>();
                    item.put("project_title", project.getTitle());
                    listItem.add(item);
                }
                listViewMyProjects = view.findViewById(R.id.list);
                SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(),listItem, R.layout.view_project,
                        new String[]{"project_title"}, new int[]{R.id.project_title});
                listViewMyProjects.setAdapter(simpleAdapter);
            }
        }
    }
}
