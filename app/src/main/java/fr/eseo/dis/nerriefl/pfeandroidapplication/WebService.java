package fr.eseo.dis.nerriefl.pfeandroidapplication;

import android.content.Context;
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
    private final String URL_SO_MANAGER = "https://192.168.4.10/www/pfe/webservice.php";

    public WebService() {

    }

    private InputStream sendRequest(URL url, Context context) throws Exception {

        try {
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
        } catch (Exception e) {
            throw new Exception("");
        }
        return null;
    }

    public InputStream getResponse(Context context, String username, String password) {

        try {

            // Envoi de la requête
            InputStream inputStream = sendRequest(new URL(URL_SO_MANAGER + "?q=LOGON&user=" + username + "&pass=" + password), context);

            // Vérification de l'inputStream
            if (inputStream != null) {
                return inputStream;
            }

        } catch (Exception e) {
            Log.e("WebService", "Impossible de rapatrier les données");
        }
        return null;
    }

    public InputStream logon(Context context, String username, String password) {

        try {

            // Envoi de la requête
            InputStream inputStream = sendRequest(new URL(URL_SO_MANAGER + "?q=LOGON&user=" + username + "&pass=" + password), context);

            // Vérification de l'inputStream
            if (inputStream != null) {
                return inputStream;
            }

        } catch (Exception e) {
            Log.e("WebService", "Impossible de rapatrier les données");
        }
        return null;
    }
}
