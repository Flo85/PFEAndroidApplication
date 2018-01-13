package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flo_n on 13/01/2018.
 */

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
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_project, parent, false);
        return new ListJurysAdapter.ListJurysViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(ListJurysAdapter.ListJurysViewHolder holder, int position) {
        final Jury jury = juries.get(position);

        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listJurys.clickJury(jury);
            }
        });
    }

    @Override
    public int getItemCount() {
        return juries.size();
    }

    class ListJurysViewHolder extends RecyclerView.ViewHolder {
        private final View view;

        public ListJurysViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }
}
