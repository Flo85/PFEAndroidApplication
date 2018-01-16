package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailJury extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_jury_details, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Détails Jury");

        Jury jury = getArguments().getParcelable("jury");
        ((TextView) view.findViewById(R.id.jury_details_date)).setText(jury.getDate());
        String members = "";
        for(int i = 0; i < jury.getMembers().size(); i++){
            members += jury.getMembers().get(i).getForeName() + " " + jury.getMembers().get(i).getSurName();
            if(i < jury.getMembers().size() - 1){
                members += ", ";
            }
        }
        ((TextView) view.findViewById(R.id.jury_details_members)).setText(members);

        String projects = "";
        for(int i = 0; i < jury.getProjects().size(); i++){
            projects += jury.getProjects().get(i).getTitle();
            if(i < jury.getProjects().size() - 1){
                projects += ", ";
            }
        }

        ((TextView) view.findViewById(R.id.jury_details_list_projects)).setText(projects);
    }
}
