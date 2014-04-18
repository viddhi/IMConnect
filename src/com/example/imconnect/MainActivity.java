package com.example.imconnect;


import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.imconnect.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;





public class MainActivity extends ListActivity {
	
	//API 
	private static String Url = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?number=14&context=default&pretty=true";
	//JSON Node Names
	private static final String Node_Posts = "posts";
	private static final String Node_Title = "title";
	private static final String Node_Excerpt = "excerpt";
	private static final String Node_Image = "featured_image";
	private static final String Node_CommentCnt = "comment_count";
	private static final String Node_LikeCnt = "like_count";
	
	JSONArray posts = null;
	//Array list of post lists
	ArrayList<HashMap<String,Object>> postLists;
	IMCShortPost _post = new IMCShortPost();
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        postLists = new ArrayList<HashMap<String,Object>>();
	        new CallAPI().execute(); 
	       // populateListView();
	    }
	
	
	private class CallAPI extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void...arg0) {
			try
			{
		
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
            String tempTitle = singleObj.getString(Node_Title);
            tempTitle = URLDecoder.decode(tempTitle,"UTF-8");
            tempTitle = tempTitle.replaceAll( "[\\u202C\\u202A]", "" );
            _post.Title = Html.fromHtml(Html.fromHtml((String) tempTitle).toString());
            
            _post.Image = singleObj.getString(Node_Image);
            //Excerpt data cleaning
            String excerptData = singleObj.getString(Node_Excerpt);
            excerptData = URLDecoder.decode(excerptData, "UTF-8");
            excerptData = excerptData.replaceAll( "[\\u202C\\u202A]", "" );
            _post.Excerpt = Html.fromHtml(Html.fromHtml((String) excerptData).toString());
            	
            _post.LikeCount = "Likes: " + singleObj.getString(Node_LikeCnt);
            _post.CommentCount = "Comments: " + singleObj.getString(Node_CommentCnt);
          
            HashMap<String,Object> post = new HashMap<String,Object>();
            post.put(Node_Title, _post.Title);
            post.put(Node_Excerpt, _post.Excerpt);
            post.put(Node_Image, _post.Image);
            post.put(Node_LikeCnt, _post.LikeCount);
            post.put(Node_CommentCnt, _post.CommentCount);
            postLists.add(post);
            }
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
            return null;
			
		}
        
		/*private void populateListView() {
			ArrayAdapter<IMCShortPost> adapter = new MyListAdapter();
			ListView lv = (ListView) findViewById(R.layout.list_item);
			lv.setAdapter(adapter);
		}*/
		/*private class MyListAdapter extends ArrayAdapter<IMCShortPost> {
			public MyListAdapter() {
				super(MainActivity.this, R.layout.list_item, postLists);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// Make sure we have a view to work with (may have been given null)
				View itemView = convertView;
				if (itemView == null) {
					itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
				}
				
				// Find the car to work with.
				IMCShortPost currentPost = _postLists.get(position);
				
				// Fill the view
				ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
				imageView.setImageResource(currentCar.getIconID());
				
				// Make:
				TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
				makeText.setText(currentCar.getMake());

				// Year:
				TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
				yearText.setText("" + currentCar.getYear());
				
				// Condition:
				TextView condionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
				condionText.setText(currentCar.getCondition());

				return itemView;
			}				
		}*/
        protected void onPostExecute(Void result) {
        	super.onPostExecute(result);
        	ListAdapter adapter = new SimpleAdapter(MainActivity.this,postLists,
        			R.layout.list_item,
        			new String[] {Node_Title,Node_Excerpt,Node_Image,Node_LikeCnt,Node_CommentCnt},
        			new int[] {R.id.Title,R.id.Excerpt,R.id.FeaturedImage,R.id.LikeCnt,R.id.CmtCnt});
        	setListAdapter(adapter);
        	/*try
        	{
	        	for(int k=0;k <= postLists.size();k++)
	        	{
		        	View header = (View)getLayoutInflater().inflate(R.layout.list_item, null);
		        	ImageView ImgView = (ImageView)header . findViewById(R.id.FeaturedImage);
		        	HashMap<String,Object> post = new HashMap<String,Object>();
		        	post = postLists.get(k);
		        	Drawable d = Drawable.createFromPath(post.get("Node_Image").toString());
		        	ImgView.setBackground(d);
	        	
	        	}
        	
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}*/
     
        }

        
    } // end CallAPI 
    

   
}




