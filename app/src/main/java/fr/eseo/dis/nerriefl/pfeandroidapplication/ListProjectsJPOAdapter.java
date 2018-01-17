package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListProjectsJPOAdapter extends RecyclerView.Adapter<ListProjectsJPOAdapter.ListProjectsJPOViewHolder> {
    private ListProjectsJPO listProjectsJPO;
    private List<Project> projects;

    public ListProjectsJPOAdapter(ListProjectsJPO listProjectsJPO) {
        this.listProjectsJPO = listProjectsJPO;
        setProjects(new ArrayList<Project>());
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public ListProjectsJPOViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_project, parent, false);
        return new ListProjectsJPOViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(ListProjectsJPOAdapter.ListProjectsJPOViewHolder holder, int position) {
        final Project project = projects.get(position);
        holder.projectTitle.setText(project.getTitle());

        holder.students.setVisibility(View.GONE);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProjectsJPO.clickProject(project);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class ListProjectsJPOViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        private final TextView projectTitle;
        private final LinearLayout students;
        private final TextView confidentiality;

        public ListProjectsJPOViewHolder(View view) {
            super(view);
            this.view = view;

            projectTitle = view.findViewById(R.id.project_title);
            students = view.findViewById(R.id.etudiant_membres);
            confidentiality = view.findViewById(R.id.confidentiality);
        }
    }
}
