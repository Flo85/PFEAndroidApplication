package fr.eseo.dis.nerriefl.pfeandroidapplication;

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
    private static final int MODIFY_NAME_RESULT_CODE = 0;

    Button connect;
    Button close;
    EditText loginText;
    EditText passwordText;

    TextView login;
    TextView password;

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

        //Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).show();

        //login = findViewById(R.id.login);
        //password = findViewById(R.id.password);

        /*login.setText("Votre email est : " + getIntent().getExtras().getString(LOGIN));
        login.setVisibility(View.VISIBLE);
        password.setText("Votre mot de passe est : " + getIntent().getExtras().getString(PASSWORD));
        password.setVisibility(View.VISIBLE);*/

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("MainActivity", "Lecture tous les projets");
        InputStream inputStream = WebService.liprj(this, getIntent().getExtras().getString(LOGIN), getIntent().getExtras().getString(TOKEN));
        HashMap<String, Object> response = null;
        if (inputStream != null) {
            try {
                response = JSONReader.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (response != null && "LIPRJ".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                List<Project> projects = (List) response.get("projects");
                Log.d("MainActivity", "Tous les projets : " + projects.size());
                for (int i = 0; i < projects.size(); i++) {
                    Log.d("MainActivity", projects.get(i).getTitle());
                }
            }
        }
        Log.d("MainActivity", "Fin lecture tous les projets");

        Log.d("MainActivity", "Lecture mes projets");
        inputStream = WebService.myprj(this, getIntent().getExtras().getString(LOGIN), getIntent().getExtras().getString(TOKEN));
        response = null;
        if (inputStream != null) {
            try {
                response = JSONReader.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (response != null && "LIPRJ".equals(response.get("api")) && "OK".equals(response.get("result"))) {
                List<Project> projects = (List) response.get("projects");
                Log.d("MainActivity", "Mes projets : " + projects.size());
                for (int i = 0; i < projects.size(); i++) {
                    Log.d("MainActivity", projects.get(i).getTitle());
                }
            }
        }
        Log.d("MainActivity", "Fin lecture mes projets");
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
            case R.id.nav_jurys:

                break;
            case R.id.nav_projets:
                fragment = new ListProjects();
                break;
            case R.id.nav_eleves:

                break;
            case R.id.nav_notifications:

                break;
            default:
                fragment = new Home();
        }

        Log.d("MainActivity","Fragment : " + fragment);

        //replacing the fragment
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
}
