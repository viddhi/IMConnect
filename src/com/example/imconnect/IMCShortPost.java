package com.example.imconnect;

import android.text.Spanned;

public class IMCShortPost {
	public String Title;
	public String Image ;
	public Spanned Excerpt;
	public String CommentCount;
	public String LikeCount;
	
	public String getTitle(String _title)
	{
		return _title;
	}
	public void setTitle (String _title){
		this.Title = _title;
	}
	public String getImage(String _image)
	{
		return _image;
	}
	public String getCommentCount(String _commentCount)
	{
			return _commentCount;
	}
	public void setCommentCount(String _commentCount)
	{
			this.CommentCount = _commentCount;
	}
	public String getlikeCount(String _likeCount)
	{
		return _likeCount;
	}
	public void setLikeCount(String _likeCount)
	{
		this.LikeCount = _likeCount;
	}
	public void setImage (String _image){
		this.Image = _image;
	}
	public Spanned getExcerpt(Spanned _excerpt)
	{
		return _excerpt;
	}
	public void setExcerpt (Spanned _excerpt){
		this.Excerpt = _excerpt;
	}

}
