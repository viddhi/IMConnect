package com.example.imconnect;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.imconnect.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.os.Build;


public class MainActivity extends ListActivity {
	
	//API 
	private static String Url = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?number=2&context=default&pretty=true";
	//JSON Node Names
	private static final String Node_Posts = "posts";
	private static final String Node_Title = "title";
	private static final String Node_Excerpt = "excerpt";
	JSONArray posts = null;
	ArrayList<HashMap<String,String>> postLists;
	IMCShortPost _post = new IMCShortPost();
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        postLists = new ArrayList<HashMap<String,String>>();
	        //ListView lv = getListView();
	       /* lv.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View view,int position, long id){
	        		String title = ((TextView) view.findViewById(R.id.Title)).getText().toString();
	        		String excerpt =((TextView) view.findViewById(R.id.Excerpt)).getText().toString();
	        		Intent in = new Intent(getApplicationContext(),DisplayPostActivity.class);
	        		in.putExtra(Node_Title, title);
	        		in.putExtra(Node_Excerpt, excerpt);
	        		startActivity(in);
	        		
	        	}
	        });*/
	        new CallAPI().execute(); 
	    }
	
	
	private class CallAPI extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void...arg0) {
			try
			{
			URL url = new URL(Url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            HttpGet httpGet = new HttpGet(Url);
            
            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity);
            
            JSONObject jObj = new JSONObject(response);
            
            posts = jObj.getJSONArray(Node_Posts);
            for (int i=0;i<posts.length();i++)
            {
            JSONObject singleObj = posts.getJSONObject(i);
            _post.Title = singleObj.getString("title");
            _post.Image = singleObj.getString("featured_image");
            _post.Excerpt = singleObj.getString("excerpt");
            HashMap<String,String> post = new HashMap<String,String>();
            post.put(Node_Title, _post.Title);
            post.put(Node_Excerpt, _post.Excerpt);
            postLists.add(post);
            }
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
            return null;
			
		}
        
     
        protected void onPostExecute(Void result) {
        	super.onPostExecute(result);
        	ListAdapter adapter = new SimpleAdapter(MainActivity.this,postLists,
        			R.layout.list_item,new String[] {Node_Title,Node_Excerpt},new int[] {R.id.Title,R.id.Excerpt});
        	setListAdapter(adapter);
     
        }

        
    } // end CallAPI 
    

   
}




