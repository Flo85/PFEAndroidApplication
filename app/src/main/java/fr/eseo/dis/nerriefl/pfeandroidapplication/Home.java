package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;

public class Home extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Accueil");

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("MainActivity", "Début lecture");
        InputStream inputStream = WebService.postr(this.getContext(), ((MainActivity) getActivity()).getLogin(), 3,
                ((MainActivity) getActivity()).getToken());

        Bitmap image = BitmapFactory.decodeStream(inputStream);
        ImageView test = view.findViewById(R.id.image_test);
        test.setImageBitmap(image);

        Log.d("MainActivity", "Fin lecture");*/
    }
}
