package com.example.imconnect;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    public void FetchIMCPosts(View view) {
    	 
        String domainName = "http://www.indianmomsconnect.com/"; 
        new CallAPI().execute(domainName); 
 
    }
   
    
    
   class CallAPI extends AsyncTask<String, String, String> {
   	 
        protected String doInBackground(String domain) {
     
        
            
            String resultToDisplay = "";
      
            InputStream in = null;
      
            // HTTP Get
            try {
            	
                URL url = new URL("https://public-api.wordpress.com/rest/v1/sites/" + domain + "/posts/");
      
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      
                in = new BufferedInputStream(urlConnection.getInputStream());
      
             } catch (Exception e ) {
      
                System.out.println(e.getMessage());
      
                return e.getMessage();
      
             }    
      
             return resultToDisplay;      
     
        }
     
        protected void onPostExecute(String result) {
        	Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        	 
        	  intent.putExtra("TEST", result);
        	  
        	  startActivity(intent);
     
        }

		
        
    } // end CallAPI 
    
    
    
}




