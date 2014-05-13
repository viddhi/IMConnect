package com.example.imconnect;



import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.example.imconnect.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;







public class MainActivity extends ListActivity  {
	
	//API 
	private static String Url = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?number=14&context=default&pretty=true";
	//JSON Node Names
	private static final String Node_Posts = "posts";
	private static final String Node_Title = "title";
	private static final String Node_Excerpt = "excerpt";
	private static final String Node_Image = "featured_image";
	private static final String Node_CommentCnt = "comment_count";
	private static final String Node_LikeCnt = "like_count";
	private static final String Node_ID = "ID";
	private static final String Node_Date = "date";
	private static final String Node_Content = "content";
	private static final String Node_Author = "author";
	
	JSONArray posts = null;
	JSONArray authorDetails = null;
	//Array list of post lists
	ArrayList<HashMap<String,Object>> postLists;
	IMCShortPost _post = new IMCShortPost();
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        //Shared preference
	        SharedPreferences prefs = this.getSharedPreferences("com.example.imconnect", Context.MODE_PRIVATE);
	        String dateTimeKey = "com.example.imconnect.datetime";
	     // use a default value using new Date()
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Calendar cal = Calendar.getInstance();
	        System.out.println();
	        prefs.edit().putString(dateTimeKey, dateFormat.format(cal.getTime())).commit();
	      
	        
	        setContentView(R.layout.activity_main);
	        postLists = new ArrayList<HashMap<String,Object>>();
	        ListView lv = getListView();
		       lv.setOnItemClickListener(new OnItemClickListener() {
		        	@SuppressLint("SimpleDateFormat")
					@Override
		        	public void onItemClick(AdapterView<?> parent, View view,int position, long id){
		        		String PostID = ((TextView) view.findViewById(R.id.ID)).getText().toString();
		        		String Title = ((TextView) view.findViewById(R.id.Title)).getText().toString();
		        		String DatePosted = ((TextView) view.findViewById(R.id.DatePosted)).getText().toString();
		        		String Content = ((TextView)view.findViewById(R.id.Content)).getText().toString();
		        		String Author = ((TextView)view.findViewById(R.id.Author)).getText().toString();
		        		String Image = ((TextView)view.findViewById(R.id.FeaturedImage)).getText().toString();
		        		Intent in = new Intent(getApplicationContext(),SinglePostActivity.class);
		        		in.putExtra(Node_ID, PostID);
		        		in.putExtra(Node_Title, Title);
		        		in.putExtra(Node_Date, DatePosted);
		        		in.putExtra(Node_Content, Content);
		        		in.putExtra(Node_Author, Author);
		        		in.putExtra(Node_Image, Image);
		        		startActivity(in);
		        		
		        	}
		        	
		        });
	        new CallAPI().execute(); 
	      
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
            //Title Data Cleaning
            String tempTitle = singleObj.getString(Node_Title);
            tempTitle = URLDecoder.decode(tempTitle,"UTF-8");
            tempTitle = tempTitle.replaceAll( "[\\u202C\\u202A]", "" );
            _post.Title = Html.fromHtml(Html.fromHtml((String) tempTitle).toString());
            
           
            //Excerpt data cleaning
            String excerptData = singleObj.getString(Node_Excerpt);
            excerptData = URLDecoder.decode(excerptData, "UTF-8");
            excerptData = excerptData.replaceAll( "[\\u202C\\u202A]", "" );
            _post.Excerpt = Html.fromHtml(Html.fromHtml((String) excerptData).toString());
            _post.Image = singleObj.getString(Node_Image).toString();
            _post.LikeCount = "Likes: " + singleObj.getString(Node_LikeCnt);
            _post.PostID = singleObj.getString(Node_ID);
            _post.CommentCount = "Comments: " + singleObj.getString(Node_CommentCnt);
            _post.dateposted = singleObj.getString(Node_Date);
            _post.Content = singleObj.getString(Node_Content);
            JSONObject authorObj  = singleObj.getJSONObject(Node_Author);
            
            //JSONObject authorObj = authorDetails.getJSONObject(0);
            _post.Author = authorObj.getString("name").toString();
           
          
            HashMap<String,Object> post = new HashMap<String,Object>();
            post.put(Node_Title, _post.Title);
            post.put(Node_Excerpt, _post.Excerpt);
            post.put(Node_Image, _post.Image);
            post.put(Node_LikeCnt, _post.LikeCount);
            post.put(Node_CommentCnt, _post.CommentCount);
            post.put(Node_ID, _post.PostID);
            post.put(Node_Date, _post.dateposted);
            post.put(Node_Content, _post.Content);
            post.put(Node_Author,_post.Author);
           
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
        			R.layout.list_item,
        			new String[] {Node_Title,Node_Excerpt,Node_Image,Node_LikeCnt,Node_CommentCnt,Node_ID,Node_Date,Node_Content,Node_Author},
        			new int[] {R.id.Title,R.id.Excerpt,R.id.FeaturedImage,R.id.LikeCnt,R.id.CmtCnt,R.id.ID,R.id.DatePosted,R.id.Content,R.id.Author});
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




