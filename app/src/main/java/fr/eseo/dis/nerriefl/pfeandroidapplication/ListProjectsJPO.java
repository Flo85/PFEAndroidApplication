package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class ListProjectsJPO extends Fragment {
    private List<Project> projects;
    private ListProjectsJPOAdapter listProjectsJPOAdapter;

    public ListProjectsJPO () {

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

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        listProjectsJPOAdapter = new ListProjectsJPOAdapter(this);
        recyclerView.setAdapter(listProjectsJPOAdapter);
        ListProjectsJPOTask listProjectsJPOTask = new ListProjectsJPOTask();
        listProjectsJPOTask.execute();
    }

    public void clickProject(Project project) {
        DetailProject detailProject = new DetailProject();
        Bundle bundle = new Bundle();
        bundle.putParcelable("project", project);
        detailProject.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(detailProject);
    }

    private class ListProjectsJPOTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = WebService.porte(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                    ((MainActivity) getActivity()).getLogged().getToken());
            HashMap<String, Object> response = null;
            if (inputStream != null) {
                try {
                    response = JSONReader.read(inputStream);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                if (response != null && "PORTE".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                    projects = (List) response.get("projects");
                }
            }
            listProjectsJPOAdapter.setProjects(projects);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listProjectsJPOAdapter.notifyDataSetChanged();
        }
    }
}
