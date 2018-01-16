package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class DetailProject extends Fragment {
    private Bitmap poster;
    private View view;
    private Project project;
    private NoteStudentAdapter noteStudentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_project_details, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Détails Projet");

        view = view;
        project = getArguments().getParcelable("project");
        ((TextView) view.findViewById(R.id.title)).setText(project.getTitle());
        for (User etudiant : project.getStudents()) {
            ((TextView) view.findViewById(R.id.etudiant_membres)).setText(etudiant.getForeName() + " " + etudiant.getSurName());
        }
        ((TextView) view.findViewById(R.id.project_details_description)).setText(project.getDescription());

        ((ImageView) view.findViewById(R.id.thumb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poster != null) {
                    clickPoster(poster);
                }
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        noteStudentAdapter = new NoteStudentAdapter(this);
        recyclerView.setAdapter(noteStudentAdapter);
        /*((MainActivity) getActivity()).getLogged();
        for (Project projectToEvaluate : user.getProjectsToEvaluate()) {
            if (project.getId() == projectToEvaluate.getId()) {
            }
        }*/


        DetailProjectTask detailProjectTask = new DetailProjectTask(view, project);
        detailProjectTask.execute();
    }

    private void clickPoster(Bitmap poster) {
        DetailPoster detailPoster = new DetailPoster();
        Bundle bundle = new Bundle();
        bundle.putParcelable("poster", poster);
        detailPoster.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(detailPoster);
    }

    public void saveNote(User student, double note) {
        DetailProjectTaskEdit detailProjectTaskEdit = new DetailProjectTaskEdit(view, project, student, note);
        detailProjectTaskEdit.execute();
    }

    public class DetailProjectTask extends AsyncTask<Void, Void, Boolean> {
        private final View view;
        private final Project project;
        private InputStream inputStream;
        private List<Note> notes;

        public DetailProjectTask(View view, Project project) {
            this.view = view;
            this.project = project;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (project.isPosterCommited()) {
                inputStream = WebService.postr(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(), project.getId(),
                        ((MainActivity) getActivity()).getLogged().getToken());
                if (inputStream != null) {
                    inputStream = WebService.notes(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                            project.getId(), ((MainActivity) getActivity()).getLogged().getToken());
                    HashMap<String, Object> response = null;
                    if (inputStream != null) {
                        try {
                            response = JSONReader.read(inputStream);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                        if (response != null && "NOTES".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                            notes = (List) response.get("notes");
                            noteStudentAdapter.setNotes(notes);
                            return true;
                        }
                    }
                }
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Bitmap thumb = BitmapFactory.decodeStream(inputStream);
                if (view.findViewById(R.id.thumb) != null) {
                    ((ImageView) view.findViewById(R.id.thumb)).setImageBitmap(thumb);
                }
                poster = thumb;
                /*if (view.findViewById(R.id.project_note_moyenne) != null) {
                    ((TextView) view.findViewById(R.id.project_note_moyenne)).setText("" + notes.get(0).getAverageNote());
                }*/
                noteStudentAdapter.notifyDataSetChanged();
            }
        }
    }

    public class DetailProjectTaskEdit extends AsyncTask<Void, Void, Boolean> {
        private final View view;
        private final Project project;
        private final double note;
        private final User etudiant;
        private List<Note> notes;

        public DetailProjectTaskEdit(View view, Project project, User etudiant, double note) {
            this.view = view;
            this.project = project;
            this.note = note;
            this.etudiant = etudiant;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            InputStream inputStream = WebService.newnt(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                    project.getId(), etudiant.getId(), note,
                    ((MainActivity) getActivity()).getLogged().getToken());
            HashMap<String, Object> response = null;
            if (inputStream != null) {
                try {
                    response = JSONReader.read(inputStream);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                if (response != null && "NEWNT".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                    inputStream = WebService.notes(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                            project.getId(), ((MainActivity) getActivity()).getLogged().getToken());
                    response = null;
                    if (inputStream != null) {
                        try {
                            response = JSONReader.read(inputStream);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                        if (response != null && "NOTES".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                            notes = (List) response.get("notes");
                            noteStudentAdapter.setNotes(notes);
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                /*if (view.findViewById(R.id.project_note_moyenne) != null) {
                    ((TextView) view.findViewById(R.id.project_note_moyenne)).setText("" + notes.get(0).getAverageNote());
                }*/
                noteStudentAdapter.notifyDataSetChanged();
            }
        }
    }
}
