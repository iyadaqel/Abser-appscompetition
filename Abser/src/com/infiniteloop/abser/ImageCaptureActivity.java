package com.infiniteloop.abser;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImageCaptureActivity extends Activity {

	Button takePictureButton;
	EditText nameInput;
	SharedPreferences prefs;
	String imageUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_capture);
		
	    nameInput = (EditText) findViewById(R.id.name_input);
//	    nameInput.setText("");
		takePictureButton = (Button) findViewById(R.id.take_picture_button);
		
		final AbserApp appUserName = (AbserApp)getApplicationContext();
	    String name = appUserName.getName();
	    
	    if(name.equals("")){
	    	// Button action listener
			takePictureButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(nameInput.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(), "√œŒ· «”„ «·„” Œœ„", Toast.LENGTH_LONG).show();
					}
					else{
						appUserName.setName(nameInput.getText().toString());
						Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intent, 0);
					}
				}
			});
	    }
	    else{
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 0);
	    }
	    
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_capture_layout, menu);
		return true;
	}
	
	
	@Override
    protected void onPause() {
    	super.onPause();
    	
    	prefs = getSharedPreferences("ImagePrefs", 0);
    	Editor editor = prefs.edit();
    	editor.putString("ImageUri", imageUri);
    	editor.commit();
    	
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 0){
    		if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
    			imageUri = ""+data.getData();
//                Toast.makeText(this, "Image saved to:\n" + imageUri, Toast.LENGTH_LONG).show();
//                testtext.setText("IN: " + data.getData());
//                image.setImageURI(data.getData());
                
                
                Intent intent = new Intent(getApplicationContext(), RecordMessageActivity.class);
				startActivity(intent);
                
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
    		
    		
//    		capturedImage = (Bitmap) data.getExtras().get("data");
//    		image.setImageBitmap(capturedImage);
//    		Toast.makeText(getApplicationContext(), "back from camera", Toast.LENGTH_SHORT).show();
    	}
    }
	
	
}
