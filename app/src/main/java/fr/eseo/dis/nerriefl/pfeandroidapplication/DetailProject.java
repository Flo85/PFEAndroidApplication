package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class DetailProject extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_detail_project, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Détail Projet");

        Project project = getArguments().getParcelable("project");
        ((TextView) view.findViewById(R.id.title)).setText(project.getTitle());
        ((TextView) view.findViewById(R.id.description)).setText(project.getDescription());

        DetailProjectTask detailProjectTask = new DetailProjectTask(view, project);
        detailProjectTask.execute();
    }

    public class DetailProjectTask extends AsyncTask<Void, Void, Boolean> {
        private final View view;
        private final Project project;
        private InputStream inputStream;

        public DetailProjectTask(View view, Project project) {
            this.view = view;
            this.project = project;
            this.inputStream = null;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if(project.isPosterCommited()) {
                inputStream = WebService.postr(getContext(), ((MainActivity) getActivity()).getLogin(), project.getId(),
                        ((MainActivity) getActivity()).getToken());
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
                ((ImageView) view.findViewById(R.id.thumb)).setImageBitmap(thumb);
            }
        }
    }
}
