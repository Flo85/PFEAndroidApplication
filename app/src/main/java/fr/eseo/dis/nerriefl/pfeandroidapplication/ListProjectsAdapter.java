package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListProjectsAdapter extends RecyclerView.Adapter<ListProjectsAdapter.ListProjectsViewHolder> {
    private ListProjects listProjects;
    private List<Project> projects;

    public ListProjectsAdapter(ListProjects listProjects) {
        this.listProjects = listProjects;
        setProjects(new ArrayList<Project>());
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public ListProjectsAdapter.ListProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_project, parent, false);
        return new ListProjectsAdapter.ListProjectsViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(ListProjectsAdapter.ListProjectsViewHolder holder, int position) {
        final Project project = projects.get(position);
        holder.projectTitle.setText(project.getTitle());
        if(project.getConfidentiality() == 0
                || ((MainActivity) listProjects.getActivity()).getLogged().isProjectDetailsAvailable(project.getId())) {
            holder.confidentiality.setText("Détails accessibles");

            holder.view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listProjects.clickProject(project);
                }
            });
        } else {
            holder.confidentiality.setText("Confidentiel");
        }
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class ListProjectsViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        private final TextView projectTitle;
        private final TextView confidentiality;
        private final ImageView posterThumb;

        public ListProjectsViewHolder(View view) {
            super(view);
            this.view = view;

            projectTitle = view.findViewById(R.id.project_title);
            confidentiality = view.findViewById(R.id.project_details_confidentiality);
            posterThumb = view.findViewById(R.id.thumb);
        }
    }
}
