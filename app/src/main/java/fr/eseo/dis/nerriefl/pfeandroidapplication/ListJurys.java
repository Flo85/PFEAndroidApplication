package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListJurys extends Fragment {
    private List<Jury> juries;
    private ListJurysAdapter listJurysAdapter;

    public ListJurys() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Les Jurys");

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        listJurysAdapter = new ListJurysAdapter(this);
        recyclerView.setAdapter(listJurysAdapter);
        ListJurys.ListJurysTask listJurysTask = new ListJurys.ListJurysTask();
        listJurysTask.execute();
    }

    public void clickJury(Jury jury) {
        DetailJury detailJury = new DetailJury();
        Bundle bundle = new Bundle();
        bundle.putParcelable("jury", jury);
        detailJury.setArguments(bundle);
        ((MainActivity) getActivity()).displayFragment(detailJury);
    }

    private class ListJurysTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream inputStream = WebService.lijur(getContext(), ((MainActivity) getActivity()).getLogged().getLogin(),
                    ((MainActivity) getActivity()).getLogged().getToken());
            HashMap<String, Object> response = null;
            if (inputStream != null) {
                try {
                    response = JSONReader.read(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (response != null && "LIJUR".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                    juries = (List) response.get("juries");
                }
            }
            listJurysAdapter.setJuries(juries);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listJurysAdapter.notifyDataSetChanged();
        }
    }
}
