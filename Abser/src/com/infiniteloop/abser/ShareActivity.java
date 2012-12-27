package com.infiniteloop.abser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ShareActivity extends Activity{
	
	Button shareButton;
	SharedPreferences prefs;
	String imageUri;
	String soundUrl;
	ImageView imagePreview;
	Bitmap capturedImage;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share);
		
		imagePreview = (ImageView) findViewById(R.id.image_preview1);
		prefs = getSharedPreferences("ImagePrefs", 0);
		imageUri = prefs.getString("ImageUri", "Image not found");
		soundUrl = prefs.getString("soundUrl", "No sound URL found");
		
        imagePreview.setImageURI(Uri.parse(imageUri));
		
		
		shareButton = (Button) findViewById(R.id.share_button);
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				//This code views the share chooser.. works fine by itself
//				Intent shareIntent = new Intent(Intent.ACTION_SEND);
//				shareIntent.setType("image/jpeg");
//				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//				capturedImage = BitmapFactory.decodeFile(getRealPathFromURI(Uri.parse(imageUri)));
//				capturedImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//				shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri));
//				startActivity(Intent.createChooser(shareIntent, "Share Image"));
				
				//This code takes you directly to the tweet sender
				try{
				    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//				    shareIntent.putExtra(Intent.EXTRA_TEXT, "TEST TEST TEST");
				    shareIntent.setType("image/jpeg");
				    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				    capturedImage = BitmapFactory.decodeFile(getRealPathFromURI(Uri.parse(imageUri)));
				    capturedImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri));
				    
				    final PackageManager pm = getApplicationContext().getPackageManager();
				    final List activityList = pm.queryIntentActivities(shareIntent, 0);
				        int len =  activityList.size();
				    for (int i = 0; i < len; i++) {
				        final ResolveInfo app = (ResolveInfo) activityList.get(i);
				        if ("com.twitter.android.PostActivity".equals(app.activityInfo.name)) {
				            final ActivityInfo activity=app.activityInfo;
				            final ComponentName name=new ComponentName(activity.applicationInfo.packageName, activity.name);
				            shareIntent=new Intent(Intent.ACTION_SEND);
				            shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				            shareIntent.setComponent(name);
				            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri));
				            shareIntent.putExtra(Intent.EXTRA_TEXT, soundUrl+" #ÃÈÕÑ");
				            getApplicationContext().startActivity(shareIntent);
				            break;
				        }
				    }
				}
				catch(Exception e) {
				}
				
				
				
//				String tweetUrl = "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url="+ "https://www.google.com";
////				String tweetUrl ="https://twitter.com/intent/retweet?tweet_id=51113028241989632"
//				Uri tweetUri = Uri.parse(tweetUrl);
//				startActivity(new Intent(Intent.ACTION_VIEW, tweetUri));
				
				Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
				startActivity(intent);
				
				
			}
		});
		
		
		
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	
	
}
