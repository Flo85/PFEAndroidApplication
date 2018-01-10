package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Flo on 20/12/2017.
 */

public class WebService {
    private static final String URL_SO_MANAGER = "https://192.168.4.10/www/pfe/webservice.php";

    public WebService() {

    }

    private static InputStream sendRequest(URL url, Context context) throws Exception {
        // Load CAs from an InputStream
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        InputStream caInput = new BufferedInputStream(context.getResources().openRawResource(R.raw.root));
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
        } finally {
            caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        // Open connection
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        // Connect to url
        urlConnection.connect();

        // If the server return with a HTTP 200
        if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            return urlConnection.getInputStream();
        }
        return null;
    }

    public static InputStream logon(Context context, String userName, String password) {
        try {

            // Send request
            return sendRequest(new URL(URL_SO_MANAGER + "?q=LOGON&user=" + userName + "&pass=" + password), context);

        } catch (Exception e) {
            Log.e("WebService", "LOGON error");
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream liprj(Context context, String userName, String token) {
        try {

            // Send request
            return sendRequest(new URL(URL_SO_MANAGER + "?q=LIPRJ&user=" + userName + "&token=" + token), context);

        } catch (Exception e) {
            Log.e("WebService", "LIPRJ error");
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream myprj(Context context, String userName, String token) {
        try {

            // Send request
            return sendRequest(new URL(URL_SO_MANAGER + "?q=MYPRJ&user=" + userName + "&token=" + token), context);

        } catch (Exception e) {
            Log.e("WebService", "MYPRJ error");
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream lijur(Context context, String userName, String token) {
        try {

            // Send request
            return sendRequest(new URL(URL_SO_MANAGER + "?q=LIJUR&user=" + userName + "&token=" + token), context);

        } catch (Exception e) {
            Log.e("WebService", "LIJUR error");
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream myjur(Context context, String userName, String token) {
        try {

            // Send request
            return sendRequest(new URL(URL_SO_MANAGER + "?q=MYJUR&user=" + userName + "&token=" + token), context);

        } catch (Exception e) {
            Log.e("WebService", "LIJUR error");
            e.printStackTrace();
        }
        return null;
    }
}
