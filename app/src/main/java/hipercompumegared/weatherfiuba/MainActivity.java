package hipercompumegared.weatherfiuba;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private TextView cityText;
    private TextView condition;
    private TextView temperature;
    private TextView press;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String code = "6559994";


        cityText = (TextView) findViewById(R.id.cityText);
        condition = (TextView) findViewById(R.id.condition);
        temperature = (TextView) findViewById(R.id.temperature);
        press = (TextView) findViewById(R.id.pressure);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    showLoadingToast();
                    JSONWeatherTask task = new JSONWeatherTask();
                    String code = "6559994";
                    task.execute(code);
                }
        });
        showLoadingToast();

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(code);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String cityCode=params[0];
            try {
                String data = ((new WeatherHttpClient()).getWeatherData(cityCode));

                try {
                    weather = JSONWeatherParser.getWeather(data, cityCode);

                    // Let's retrieve the icon
                    //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return weather;
            }catch (UnknownHostException e){
                return null;
            }

        }




        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

           /** if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }**/

           if(weather==null){
                showErrorInConnectionToast();
               return;
           }
            cityText.setText(weather.city.Name);
            condition.setText(weather.condition);
            temperature.setText("" + weather.temperature +"�C");
            press.setText("" + weather.pressure+" hPa");

        }







    }
    private void showLoadingToast(){
        Context context = getApplicationContext();
        CharSequence text = "Cargando...";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void showErrorInConnectionToast(){
        Context context = getApplicationContext();
        CharSequence text = "“No fue posible conectarse al servidor, \n" +"por favor reintente más tarde”";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
}
