package com.example.imconnect;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;



import com.example.imconnect.ConsUtilities;

public class ServerUtility {
	
	public static int returnPostCount(String PrevCheckedDate)
	{
		int NumOfPostsFound =0;
		try
		{
		 DefaultHttpClient httpClient = new DefaultHttpClient();
         HttpEntity httpEntity = null;
         HttpResponse httpResponse = null;
         HttpGet httpGet = new HttpGet(ConsUtilities.getNumberOfPosts);
         
         httpResponse = httpClient.execute(httpGet);
         httpEntity = httpResponse.getEntity();
         String response = EntityUtils.toString(httpEntity);
         
         JSONObject jObj = new JSONObject(response);
         
         NumOfPostsFound = jObj.getInt(ConsUtilities.Node_Found);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
         
        
		return NumOfPostsFound;
	}

}
