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

    private Button connect;
    private Button close;
    private EditText loginText;
    private EditText passwordText;

    private TextView viewLogin;
    private TextView viewPassword;

    private String login;
    private String password;
    private String token;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        login = getIntent().getExtras().getString(LOGIN);
        password = getIntent().getExtras().getString(PASSWORD);
        token = getIntent().getExtras().getString(TOKEN);

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

        Log.d("MainActivity", "Début lecture");
        InputStream inputStream = WebService.myjur(this, login, token);
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
                List<Jury> juries = (List) response.get("juries");
                Log.d("MainActivity", "Mes jurys : " + juries.size());
                for (int i = 0; i < juries.size(); i++) {
                    Log.d("MainActivity", "" + juries.get(i).getId());
                }
            }
        }
        Log.d("MainActivity", "Fin lecture");
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
            case R.id.nav_jurys:
                fragment = new ListJurys();
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
