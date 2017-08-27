/**
 * This is a tutorial source code
 * provided "as is" and without warranties.
 * <p>
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 * <p>
 * or write an email to
 * survivingwithandroid@gmail.com
 */
package hipercompumegared.weatherfiuba;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class WeatherHttpClient {

    private static final String TAG = "WeatherHttpClient";
    private static String BASE_URL = "http://tp0-taller.herokuapp.com/weather/cities/";


    public String getWeatherData(String cityCode) throws UnknownHostException {
        return this.getStringData(BASE_URL +  cityCode);
    }
    public String getSuggestions(String prefix) throws UnknownHostException{
        String result = this.getStringData(BASE_URL + "name/" + prefix);

        return result;
    }

    public String getStringData(String url) throws UnknownHostException {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) (new URL(url)).openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            int status = con.getResponseCode();
            Log.d(TAG, "Status: " + status);

            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (UnknownHostException t) {

            throw t;
        } catch (Throwable t) {

            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;

    }

}
