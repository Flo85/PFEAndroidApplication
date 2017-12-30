package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String LOGIN = "Login";
    private static final String PASSWORD = "Password";
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
        setContentView(R.layout.activity_main);

        Log.d("Main", "Début de MainActivity");

        Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).show();

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);

        login.setText("Votre email est : " + getIntent().getExtras().getString(LOGIN));
        login.setVisibility(View.VISIBLE);
        password.setText("Votre mot de passe est : " + getIntent().getExtras().getString(PASSWORD));
        password.setVisibility(View.VISIBLE);

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        WebService webService = new WebService();
        InputStream inputStream = webService.getResponse(this, "chauvnat", "NoY3xNno7QZn");

        if (inputStream != null) {
            password.setText("Ok");
            HashMap<String, String> response = null;
            try {
                response = JSONReader.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (response != null && "LOGON".equals(response.get("api"))) {
                password.setText(response.get("token"));
            }
        } else {
            password.setText("Fail");
        }*/

        /*connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginText.getText().toString().equals("admin") &&
                        passwordText.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra(LOGIN, loginText.getText().toString());
                    intent.putExtra(PASSWORD, passwordText.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        connect.setEnabled(false);
                    }
                }
            }
        });*/
    }
}
