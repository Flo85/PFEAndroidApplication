package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOGIN = "Login";
    private static final String PASSWORD = "Password";
    private static final String TOKEN = "Token";
    private static final String FORENAME = "Forename";
    private static final String SURNAME = "Surname";
    private static final int MODIFY_NAME_RESULT_CODE = 0;

    private User logged;

    public User getLogged() {
        return logged;
    }

    public void setLogged(User logged) {
        this.logged = logged;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(0);

        if(logged == null) {
            logged = new User();
            logged.setLogin(getIntent().getExtras().getString(LOGIN));
            logged.setPassword(getIntent().getExtras().getString(PASSWORD));
            logged.setToken(getIntent().getExtras().getString(TOKEN));
            logged.setForeName(getIntent().getExtras().getString(FORENAME));
            logged.setSurName(getIntent().getExtras().getString(SURNAME));

            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.logged_name)).setText(logged.getForeName() + " "
                    + logged.getSurName());
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.logged_email)).setText(logged.getForeName().toLowerCase()
                    + "." + logged.getSurName().toLowerCase() + "@eseo.fr");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_my_projets:
                fragment = new ListMyProjects();
                break;
            case R.id.nav_my_jurys:
                fragment = new ListMyJury();
                break;
            case R.id.nav_projets:
                fragment = new ListProjects();
                break;
            case R.id.nav_jurys:
                fragment = new ListJurys();
                break;
            case R.id.nav_eleves:

                break;
            case R.id.nav_notifications:

                break;
            default:
                fragment = new Home();
        }

        displayFragment(fragment);
    }

    public void displayFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private class MainActivityTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
