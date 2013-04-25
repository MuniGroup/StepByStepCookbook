package cz.muni.muniGroup.cookbook.entities;

import java.util.Date;


public class DownloadRecipe extends Recipe {

	private User user;
	private Date download_time;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDownload_time() {
		return download_time;
	}
	public void setDownload_time(Date download_time) {
		this.download_time = download_time;
	}

	
	
}