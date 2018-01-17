package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PIOU Marion on 16/01/2018.
 */

public class NoteStudentAdapter extends RecyclerView.Adapter<NoteStudentAdapter.NoteStudentViewHolder> {
    private DetailProject detailProject;
    private List<Note> notes;

    public NoteStudentAdapter(DetailProject detailProject) {
        this.detailProject = detailProject;
        setNotes(new ArrayList<Note>());
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public NoteStudentAdapter.NoteStudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View noteStudentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_note_student, parent, false);
        return new NoteStudentAdapter.NoteStudentViewHolder(noteStudentView);
    }

    @Override
    public void onBindViewHolder(final NoteStudentAdapter.NoteStudentViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.projectNotationLabel.setText(note.getUser().getForeName() + " " + note.getUser().getSurName());
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        holder.projectNoteMoyenne.setText("" + decimalFormat.format(note.getAverageNote()));
        holder.projectNoteJury.setText("" + note.getMyNote());
        holder.projectSauvegardeNoteJury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String givenNote = holder.projectNoteJury.getText().toString();
                if (!TextUtils.isEmpty(givenNote)) {
                    try {
                        detailProject.saveNote(note.getUser(), Double.parseDouble(givenNote));
                    } catch (NumberFormatException e) {
                        Log.e("NoteStudentAdapter", "Erreur de conversion en décimal !");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteStudentViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        private final TextView projectNotationLabel;
        private final TextView projectNoteMoyenne;
        private final EditText projectNoteJury;
        private final Button projectSauvegardeNoteJury;

        public NoteStudentViewHolder(View view) {
            super(view);
            this.view = view;

            projectNotationLabel = view.findViewById(R.id.project_notation_label);
            projectNoteMoyenne = view.findViewById(R.id.project_note_moyenne);
            projectNoteJury = view.findViewById(R.id.project_note_jury);
            projectSauvegardeNoteJury = view.findViewById(R.id.project_sauvegarde_note_jury);
        }
    }
}
