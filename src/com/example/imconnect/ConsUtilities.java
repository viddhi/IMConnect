package com.example.imconnect;

public class ConsUtilities {
	
	//API 
		public static String Url = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?number=14&context=default&pretty=true";
		public static String getNumberOfPosts = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?after={0}&pretty=1";
		//JSON Node Names
		public static final String Node_Found = "found";
		public static final String Node_Posts = "posts";
		public static final String Node_Title = "title";
		public static final String Node_Excerpt = "excerpt";
		public static final String Node_Image = "featured_image";
		public static final String Node_CommentCnt = "comment_count";
		public static final String Node_LikeCnt = "like_count";
		public static final String Node_ID = "ID";
		public static final String Node_Date = "date";
		public static final String Node_Content = "content";
		public static final String Node_Author = "author";

}
