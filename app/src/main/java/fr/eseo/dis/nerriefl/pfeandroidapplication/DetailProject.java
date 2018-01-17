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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class DetailProject extends Fragment {
    private Bitmap poster;
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
        ((TextView) view.findViewById(R.id.project_details_confidentiality)).setText("" + project.getConfidentiality());
        if (project.getSupervisor() != null) {
            ((TextView) view.findViewById(R.id.project_details_supervisor_content)).setText(project.getSupervisor().getForeName() + " "
                    + project.getSupervisor().getSurName());
            view.findViewById(R.id.project_details_supervisor).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.project_details_supervisor).setVisibility(View.GONE);
        }
        if (project.getStudents() != null && !project.getStudents().isEmpty()) {
            String students = "";
            for (User student : project.getStudents()) {
                if (!student.equals(project.getStudents().get(0))) {
                    students += ", ";
                }
                students += student.getForeName() + " " + student.getSurName();
            }
            ((TextView) view.findViewById(R.id.project_details_students_content)).setText(students);
            view.findViewById(R.id.project_details_students).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.project_details_students).setVisibility(View.GONE);
        }
        ((TextView) view.findViewById(R.id.project_details_description)).setText(project.getDescription());

        view.findViewById(R.id.thumb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poster != null) {
                    clickPoster(poster);
                }
            }
        });

        if (((MainActivity) getActivity()).getLogged().isProjectInProjectsToEvaluate(project.getId())) {
            view.findViewById(R.id.project_details_students).setVisibility(View.GONE);
            view.findViewById(R.id.project_details_notes).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.project_details_students).setVisibility(View.VISIBLE);
            view.findViewById(R.id.project_details_notes).setVisibility(View.GONE);
        }

        if (project.getConfidentiality() == 0 || ((MainActivity) getActivity()).getLogged().isProjectDetailsAvailable(project.getId())) {
            view.findViewById(R.id.thumb).setVisibility(View.VISIBLE);
            view.findViewById(R.id.project_details_description_label).setVisibility(View.VISIBLE);
            view.findViewById(R.id.project_details_description).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.thumb).setVisibility(View.GONE);
            view.findViewById(R.id.project_details_description_label).setVisibility(View.GONE);
            view.findViewById(R.id.project_details_description).setVisibility(View.GONE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.project_details_notes);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        noteStudentAdapter = new NoteStudentAdapter(this);
        recyclerView.setAdapter(noteStudentAdapter);

        DetailProjectTaskPoster detailProjectTaskPoster = new DetailProjectTaskPoster(view);
        detailProjectTaskPoster.execute();
        DetailProjectTaskNotes detailProjectTaskNotes = new DetailProjectTaskNotes();
        detailProjectTaskNotes.execute();
    }

    private void clickPoster(Bitmap poster) {
        DetailPoster detailPoster = new DetailPoster();
        Bundle bundle = new Bundle();
        bundle.putParcelable("poster", poster);
        detailPoster.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(detailPoster);
    }

    public void saveNote(User student, double note) {
        DetailProjectTaskNotesEdit detailProjectTaskNotesEdit = new DetailProjectTaskNotesEdit(student, note);
        detailProjectTaskNotesEdit.execute();
    }

    public class DetailProjectTaskPoster extends AsyncTask<Void, Void, Boolean> {
        private final View view;
        private Bitmap thumb;

        public DetailProjectTaskPoster(View view) {
            this.view = view;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if(project.getPoster() != null && !"".equals(project.getPoster())){
                byte[] bytesImage = Base64.decode(project.getPoster(), Base64.DEFAULT);
                thumb = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
                return true;
            } else if (project.isPosterCommited()) {
                InputStream inputStream = WebService.postr(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(), project.getId(),
                        ((MainActivity) getActivity()).getLogged().getToken());
                if (inputStream != null) {
                    thumb = BitmapFactory.decodeStream(inputStream);
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                if (thumb != null && view.findViewById(R.id.thumb) != null) {
                    ((ImageView) view.findViewById(R.id.thumb)).setImageBitmap(thumb);
                    poster = thumb;
                }
            }
        }
    }

    public class DetailProjectTaskNotes extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            InputStream inputStream = WebService.notes(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                    project.getId(), ((MainActivity) getActivity()).getLogged().getToken());
            HashMap<String, Object> response = null;
            if (inputStream != null) {
                try {
                    response = JSONReader.read(inputStream);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                if (response != null && "NOTES".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                    List<Note> notes = (List) response.get("notes");
                    noteStudentAdapter.setNotes(notes);
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                noteStudentAdapter.notifyDataSetChanged();
            }
        }
    }

    public class DetailProjectTaskNotesEdit extends AsyncTask<Void, Void, Boolean> {
        private final double note;
        private final User etudiant;

        public DetailProjectTaskNotesEdit(User etudiant, double note) {
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
                            List<Note> notes = (List) response.get("notes");
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
                noteStudentAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Note sauvergardée !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
