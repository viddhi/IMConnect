package com.example.imconnect;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;


@SuppressLint("DefaultLocale")
public class SinglePostActivity extends Activity {
	
	// JSON node keys
	
	private static final String Node_Title = "title";
	private static final String Node_Date = "date";

	
	@SuppressLint("DefaultLocale")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepost);
        Intent in = getIntent();
       
        String Title = in.getStringExtra(Node_Title);
        String DatePosted = in.getStringExtra(Node_Date);
       //Manipulating date using string as Calendar was giving me lot of issues.
        DatePosted = DatePosted.substring(0, 10);
       String Year = DatePosted.split("-")[0];
       String Month = DatePosted.split("-")[1];
       String DatePart = DatePosted.split("-")[2];
       //Title cleanup
       Title = Title.replace('-', ' ');
       
        Title = Title.toLowerCase().replace(' ', '-');
        WebView myWebView = (WebView) findViewById(R.id.SinglePostView);
        myWebView.loadUrl("http://www.indianmomsconnect.com/" + Year + "/" + Month + "/" + DatePart + "/" + Title + "/");
       
    }
}
