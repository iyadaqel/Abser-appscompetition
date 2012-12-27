package com.infiniteloop.abser;

import android.app.Application;

public class AbserApp extends Application{
	private String name = "";
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
}