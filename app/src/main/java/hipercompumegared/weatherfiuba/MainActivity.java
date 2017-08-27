package hipercompumegared.weatherfiuba;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted, OnSuggestionsTaskCompleted {

    public static final String city_CODE = "6559994";
    private static final String TAG = "MainActivity";
    private TextView cityText;
    private TextView condition;
    private TextView temperature;
    private TextView press;
    Toolbar toolbar;
    private MaterialSearchView searchView;
    private ImageView backgroundImageView;
    private JSONSuggestionsTask suggestionsTask;

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
        backgroundImageView = (ImageView) findViewById(R.id.background);
        backgroundImageView.setImageResource(R.drawable.playa_5_dia_soleado);


        initFabButton();
        initSearchView();
        showLoadingToast();
        JSONWeatherTask task = new JSONWeatherTask(this);
        //suggestionsTask = new JSONSuggestionsTask(this);
        //suggestionsTask.execute("collado ");
        //suggestionsTask.cancel(true);

        task.execute(city_CODE);

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
                if(newText.length() >= 4){
                    if(suggestionsTask != null){
                        suggestionsTask.cancel(true);
                    }
                    suggestionsTask = new JSONSuggestionsTask(MainActivity.this);
                    suggestionsTask.execute(newText);
                }
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
                JSONWeatherTask task = new JSONWeatherTask(MainActivity.this);
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

    @Override
    public void OnSuggestionsTaskCompleted(Map<String,String>  suggestions) {
        String[] keys = new String[suggestions.size()];
        Object[] values = new Object[suggestions.size()];
        int index = 0;
        for (Map.Entry<String, String> mapEntry : suggestions.entrySet()) {
            keys[index] = mapEntry.getKey();
            values[index] = mapEntry.getValue();
            index++;
        }

        for(String s : keys){
            Log.d(TAG,s);
        }
        searchView.setSuggestions(keys);
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
        private OnTaskCompleted listener;

        public JSONWeatherTask(OnTaskCompleted listener){
            this.listener=listener;
        }

        @Override
        protected Weather doInBackground(String... params) {
            String cityCode = params[0];
            try {
                String data = ((new WeatherHttpClient()).getWeatherData(cityCode));

                try {
                    Weather weather = JSONWeatherParser.getWeather(data, cityCode);

                    return weather;
                } catch (Exception e) {
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
            listener.onTaskCompleted(weather);
        }

    }

    private class JSONSuggestionsTask extends AsyncTask<String, Void,  Map<String,String>> {
        private OnSuggestionsTaskCompleted listener;

        public JSONSuggestionsTask(OnSuggestionsTaskCompleted listener){
            this.listener=listener;
        }

        @Override
        protected Map<String,String> doInBackground(String... params) {
            String prefix = params[0];
            try {
                String citiNames = ((new WeatherHttpClient()).getSuggestions(prefix));
                Log.d(TAG,citiNames);
                try {
                    return JSONWeatherParser.getCityNames(citiNames);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            } catch (UnknownHostException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute( Map<String,String> citiNames) {
            super.onPostExecute(citiNames);
            if (!isCancelled()){
                listener.OnSuggestionsTaskCompleted(citiNames);;
            }
        }

    }


    @Override
    public void onTaskCompleted(Weather weather) {
        if (weather == null) {
            showErrorInConnectionToast();
            return;
        }
        cityText.setText(weather.getCityName());

        condition.setText(weather.getCondition());

        temperature.setText("" + weather.getTemperature() + "°C");
        Log.d(TAG, weather.getTemperature() + "°C");

        press.setText("" + weather.getPressure() + " hPa");
        Log.d(TAG, weather.getPressure() + " hPa");

        Log.d(TAG, weather.status.toString());
        backgroundImageView.setImageResource(ImageFinder.getImage(weather.status));
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

}
