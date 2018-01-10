package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ListMyJury extends Fragment {

    private ListView listViewMyJury;

    public ListMyJury(){
    }

    public ListView getListViewJurys() {
        return listViewMyJury;
    }

    public void setListViewJurys(ListView listViewJury) {
        this.listViewMyJury = listViewMyJury;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Mes Jurys");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InputStream inputStream = WebService.myjur(this.getContext(), ((MainActivity) getActivity()).getLogin(),
                ((MainActivity) getActivity()).getToken());
        HashMap<String, Object> response = null;
        if (inputStream != null) {
            try {
                response = JSONReader.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (response != null && "MYJUR".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                List<Jury> jurys = (List) response.get("juries");

                List<HashMap<String, Integer>> listItem = new ArrayList<>();
                HashMap<String, Integer> item;

                for(Jury jury : jurys){
                    item = new HashMap<>();
                    item.put("jury_id", jury.getId());
                    listItem.add(item);
                }
                listViewMyJury = view.findViewById(R.id.list);
                SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(),listItem, R.layout.view_jury,
                        new String[]{"jury_id"}, new int[]{R.id.jury_id});
                listViewMyJury.setAdapter(simpleAdapter);
            }
        }
    }
}
