package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flo_n on 10/01/2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private ListProjects listProjects;
    private Fragment fragment;
    private List<Project> projects;

    public ProjectAdapter(ListProjects listProjects) {
        this.listProjects = listProjects;
        setProjects(new ArrayList<Project>());
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_project, parent, false);
        return new ProjectViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        final Project project = projects.get(position);
        holder.projectTitle.setText(project.getTitle());
        holder.confidentiality.setText("Confidentialité : " + project.getConfidentiality());

        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listProjects.clickProject(project);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        private final TextView projectTitle;
        private final TextView confidentiality;
        private final ImageView posterThumb;

        public ProjectViewHolder(View view) {
            super(view);
            this.view = view;

            projectTitle = view.findViewById(R.id.project_title);
            confidentiality = view.findViewById(R.id.project_confidentiality);
            posterThumb = view.findViewById(R.id.poster_thumb);
        }
    }

    /*public class ProjectAdapterTask extends AsyncTask<Void, Void, Boolean> {

        private final ProjectViewHolder holder;
        private final Project project;
        private InputStream inputStream;

        public ProjectAdapterTask(ProjectViewHolder holder, Project project) {
            this.holder = holder;
            this.project = project;
            this.inputStream = null;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if(project.isPosterCommited()) {
                inputStream = WebService.postr(listProjects.getContext(), ((MainActivity) listProjects.getActivity()).getLogin(),
                        project.getId(), "THUMB", ((MainActivity) listProjects.getActivity()).getToken());
                if(inputStream != null) {
                    return true;
                }
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Bitmap thumb = BitmapFactory.decodeStream(inputStream);
                holder.posterThumb.setImageBitmap(thumb);
            }
        }
    }*/
}
