package com.cookstory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TimelineActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {         

       super.onCreate(savedInstanceState);    
       Intent intent = getIntent();
       String message = intent.getStringExtra(AddDishActivity.TEST);
       setContentView(R.layout.timeline);
   
       TextView test = (TextView)this.findViewById(R.id.test);
       
       test.setText(message);
       //rest of the code
        
      /* ListView list = (ListView) findViewById(R.id.timeline_objects);
       String json = "[\"Country1\",\"Country2\",\"Country3\"]";
       try {
               JSONArray array = (JSONArray) new JSONTokener(json).nextValue();

               String[] stringarray = new String[array.length()];
               for (int i = 0; i < array.length(); i++) {
                   stringarray[i] = array.getString(i);
               }
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringarray); 
               list.setAdapter(adapter);
       } catch (JSONException e) {
               // handle JSON parsing exceptions...
       }*/
   }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
