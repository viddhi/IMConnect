package com.example.imconnect;

public class IMCShortPost {
	public String Title;
	public String Image ;
	public String Excerpt;
	
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
	public void setImage (String _image){
		this.Image = _image;
	}
	public String getExcerpt(String _excerpt)
	{
		return _excerpt;
	}
	public void setExcerpt (String _excerpt){
		this.Excerpt = _excerpt;
	}

}
