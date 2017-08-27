/**
 * This is a tutorial source code 
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */
package hipercompumegared.weatherfiuba;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class JSONWeatherParser {

    private static final String TAG = "JSONWeatherParser";

	public static Weather getWeather(String data, String code) throws JSONException  {

		// We create out JSONObject from the data
		JSONObject jObj = new JSONObject(data);

		Float pressure= getFloat("pressure",jObj);
		Float temperature= getFloat("temperature",jObj);
		String condition= getString("weather",jObj);


		String name =getString("city",jObj);
        Weather weather = new Weather(condition,pressure,temperature,name,code);
		Log.d(TAG, weather.status.toString());

		return weather;
	}

	public static String[] getCityNames(String data)throws JSONException  {
        List<String> cityNames = new ArrayList<String>();
        JSONArray jArray = new JSONArray(data);
        for (int i=0; i<jArray.length(); i++) {
            JSONObject cityJSON = jArray.getJSONObject(i);
            String name = cityJSON.getString("name");
            cityNames.add(name);
        }
        String[] result = new String[cityNames.size()];
        result = cityNames.toArray(result);
        return result;
    }
	
	

	
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}
	

	
}
