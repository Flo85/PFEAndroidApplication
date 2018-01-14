package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListMyJuryAdapter extends RecyclerView.Adapter<ListMyJuryAdapter.ListMyJuryViewHolder> {
    private ListMyJury listMyJury;
    private List<Jury> juries;

    public ListMyJuryAdapter(ListMyJury listMyJury) {
        this.listMyJury = listMyJury;
        setJuries(new ArrayList<Jury>());
    }

    public void setJuries(List<Jury> juries) {
        this.juries = juries;
    }

    @Override
    public ListMyJuryAdapter.ListMyJuryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_jury, parent, false);
        return new ListMyJuryAdapter.ListMyJuryViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(ListMyJuryAdapter.ListMyJuryViewHolder holder, int position) {
        final Jury jury = juries.get(position);

        holder.juryDate.setText(jury.getDate());
        if(!jury.getMembers().isEmpty()){
            String members = "";
            for(int i = 0; i < jury.getMembers().size(); i++){
                members += jury.getMembers().get(i).getForeName() + " " + jury.getMembers().get(i).getSurName();
                if(i < jury.getMembers().size() - 1){
                    members += ", ";
                }
            }
            holder.juryMembers.setText(members);
        } else {
            holder.juryMembers.setText("Aucun membre");
        }

        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listMyJury.clickJury(jury);
            }
        });
    }

    @Override
    public int getItemCount() {
        return juries.size();
    }

    class ListMyJuryViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        private final TextView juryDate;
        private final TextView juryMembers;

        public ListMyJuryViewHolder(View view) {
            super(view);
            this.view = view;

            this.juryDate = view.findViewById(R.id.jury_date_content);
            this.juryMembers = view.findViewById(R.id.jury_membres_content);
        }
    }
}
