package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListProjects extends Fragment {
    private List<Project> projects;
    private ListProjectsAdapter listProjectsAdapter;

    public ListProjects () {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Projets");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InputStream inputStream = WebService.liprj(this.getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                ((MainActivity) getActivity()).getLogged().getToken());
        HashMap<String, Object> response = null;
        if (inputStream != null) {
            try {
                response = JSONReader.read(inputStream);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            if (response != null && "LIPRJ".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                projects = (List) response.get("projects");

                RecyclerView recyclerView = view.findViewById(R.id.list);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                listProjectsAdapter = new ListProjectsAdapter(this);
                recyclerView.setAdapter(listProjectsAdapter);
                ListProjectsTask listProjectsTask = new ListProjectsTask();
                listProjectsTask.execute();
            }
        }
    }

    public void clickProject(Project project) {
        DetailProject detailProject = new DetailProject();
        Bundle bundle = new Bundle();
        bundle.putParcelable("project", project);
        detailProject.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(detailProject);
    }

    private class ListProjectsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listProjectsAdapter.setProjects(projects);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listProjectsAdapter.notifyDataSetChanged();
        }
    }
}
