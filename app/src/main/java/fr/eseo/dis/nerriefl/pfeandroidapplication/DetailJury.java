package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class DetailJury extends Fragment {

    private DetailJuryProjectsAdapter detailJuryProjectsAdapter;
    private Jury jury;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_jury_details, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Détails Jury");

        jury = getArguments().getParcelable("jury");
        ((TextView) view.findViewById(R.id.jury_details_date)).setText(jury.getDate());
        String members = "";
        for (int i = 0; i < jury.getMembers().size(); i++) {
            members += jury.getMembers().get(i).getForeName() + " " + jury.getMembers().get(i).getSurName();
            if (i < jury.getMembers().size() - 1) {
                members += ", ";
            }
        }
        ((TextView) view.findViewById(R.id.jury_details_members)).setText(members);

        RecyclerView recyclerView = view.findViewById(R.id.jury_details_list_projects);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        detailJuryProjectsAdapter = new DetailJuryProjectsAdapter(this);
        recyclerView.setAdapter(detailJuryProjectsAdapter);
        DetailJuryTask detailJuryTask = new DetailJuryTask();
        detailJuryTask.execute();
    }

    public void clickProject(Project project) {
        DetailProject detailProject = new DetailProject();
        Bundle bundle = new Bundle();
        bundle.putParcelable("project", project);
        detailProject.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(detailProject);
    }

    private class DetailJuryTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            InputStream inputStream = WebService.jyinf(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                    jury.getId(), ((MainActivity) getActivity()).getLogged().getToken());
            HashMap<String, Object> response = null;
            if (inputStream != null) {
                try {
                    response = JSONReader.read(inputStream);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                if (response != null && "JYINF".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                    List<Project> projects = (List) response.get("projects");
                    jury.setProjects(projects);
                    detailJuryProjectsAdapter.setProjects(jury.getProjects());
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            super.onPostExecute(success);
            if (success) {
                detailJuryProjectsAdapter.notifyDataSetChanged();
            }
        }
    }
}
