package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListJurysAdapter extends RecyclerView.Adapter<ListJurysAdapter.ListJurysViewHolder> {
    private ListJurys listJurys;
    private List<Jury> juries;

    public ListJurysAdapter(ListJurys listJurys) {
        this.listJurys = listJurys;
        setJuries(new ArrayList<Jury>());
    }

    public void setJuries(List<Jury> juries) {
        this.juries = juries;
    }

    @Override
    public ListJurysAdapter.ListJurysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View juryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_jury, parent, false);
        return new ListJurysAdapter.ListJurysViewHolder(juryView);
    }

    @Override
    public void onBindViewHolder(ListJurysAdapter.ListJurysViewHolder holder, int position) {
        final Jury jury = juries.get(position);

        holder.juryDate.setText(jury.getDate());
        if (!jury.getMembers().isEmpty()) {
            String members = "";
            for (int i = 0; i < jury.getMembers().size(); i++) {
                members += jury.getMembers().get(i).getForeName() + " " + jury.getMembers().get(i).getSurName();
                if (i < jury.getMembers().size() - 1) {
                    members += ", ";
                }
            }
            holder.juryMembers.setText(members);
        } else {
            holder.juryMembers.setText("Aucun membre");
        }

        if (jury.isMember(((MainActivity) listJurys.getActivity()).getLogged())) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listJurys.clickJury(jury);
                }
            });
        } else {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return juries.size();
    }

    class ListJurysViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        private final TextView juryDate;
        private final TextView juryMembers;

        public ListJurysViewHolder(View view) {
            super(view);
            this.view = view;

            this.juryDate = view.findViewById(R.id.jury_date_content);
            this.juryMembers = view.findViewById(R.id.jury_membres_content);
        }
    }
}
