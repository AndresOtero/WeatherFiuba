package hipercompumegared.weatherfiuba;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    public static final String city_CODE = "6559994";
    private TextView cityText;
    private TextView condition;
    private TextView temperature;
    private TextView press;
    Toolbar toolbar;
    private MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cityText = (TextView) findViewById(R.id.cityText);
        condition = (TextView) findViewById(R.id.condition);
        temperature = (TextView) findViewById(R.id.temperature);
        press = (TextView) findViewById(R.id.pressure);
        initFabButton();
        initSearchView();
        showLoadingToast();
        JSONWeatherTask task = new JSONWeatherTask();
        //task.execute(code);
    }

    private void initSearchView(){
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*if(newText.toLowerCase().startsWith("bu")){
                    String[] query_suggestions = {"Buenos Aires","Buenos Días"};
                    searchView.setSuggestions(query_suggestions);
                }*/
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        String[] query_suggestions = {"Android","Ajax","Madrid","Sevilla"};
        searchView.setSuggestions(query_suggestions);
        searchView.setEllipsize(true);

    }
    private void initFabButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingToast();
                JSONWeatherTask task = new JSONWeatherTask();
                String code = city_CODE;
                task.execute(code);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            String cityCode = params[0];
            try {
                String data = ((new WeatherHttpClient()).getWeatherData(cityCode));

                try {
                    Weather weather = JSONWeatherParser.getWeather(data, cityCode);

                    // Let's retrieve the icon
                    //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

                    return weather;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            } catch (UnknownHostException e) {
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

            if (weather == null) {
                showErrorInConnectionToast();
                return;
            }
            cityText.setText(weather.getCityName());
            condition.setText(weather.getCondition());
            temperature.setText("" + weather.getTemperature() + "°C");
            press.setText("" + weather.getPressure() + " hPa");
            setImage(ImageFinder.getImage(weather.status));
        }


    }

    private void showLoadingToast() {
        Context context = getApplicationContext();
        CharSequence text = "Cargando...";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void showErrorInConnectionToast() {
        Context context = getApplicationContext();
        CharSequence text = "“No fue posible conectarse al servidor, \n" + "por favor reintente más tarde”";
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

    public void setImage(int drawable) {
        ImageView layout = (ImageView) findViewById(R.id.background);
        //layout.setBackgroundResource(drawable);
        layout.setBackgroundColor(drawable);
    }

}
