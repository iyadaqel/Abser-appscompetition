package com.infiniteloop.abser;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RecordMessageActivity extends Activity{

	Button startButton;
	Button stopButton;
	ImageView imagePreview;
	SharedPreferences prefs;
	String imageUri;
	String soundUrl;
	
	MediaRecorder recorder;
	File audiofile = null;
	private static final String TAG = "SoundRecordingActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_message);
		
		// Button action listener
		startButton = (Button) findViewById(R.id.record_message_button);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startRecording();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		stopButton = (Button) findViewById(R.id.stop_upload_button);
		stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopRecording();
			}
		});
		
		imagePreview = (ImageView) findViewById(R.id.image_preview);
		prefs = getSharedPreferences("ImagePrefs", 0);
		imageUri = prefs.getString("ImageUri", "Image not found");

        Toast.makeText(this, "Image saved to:\n" + imageUri, Toast.LENGTH_SHORT).show();
        imagePreview.setImageURI(Uri.parse(imageUri));
	}
	
	
	@Override
    protected void onPause() {
    	super.onPause();
    	
    	Editor editor = prefs.edit();
    	editor.putString("ImageUri", imageUri);
    	editor.putString("soundUrl", soundUrl);
    	editor.commit();
    	
    }
	
	
	
	public void startRecording() throws IOException {

	    startButton.setEnabled(false);
	    stopButton.setEnabled(true);

	    File sampleDir = Environment.getExternalStorageDirectory();
	    
	    try {
	      audiofile = File.createTempFile("sound", ".mp3", sampleDir);
	    } catch (IOException e) {
	      Log.e(TAG, "sdcard access error");
	      return;
	    }
	    recorder = new MediaRecorder();
	    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(audiofile.getAbsolutePath());
	    recorder.prepare();
	    recorder.start();
	  }

	  public void stopRecording() {
	    startButton.setEnabled(true);
	    stopButton.setEnabled(false);
	    recorder.stop();
	    recorder.release();
	    addRecordingToMediaLibrary();
	    
	  }

	  protected void addRecordingToMediaLibrary() {
	    ContentValues values = new ContentValues(4);
	    long current = System.currentTimeMillis();
	    values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
	    values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
	    values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
	    values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
	    ContentResolver contentResolver = getContentResolver();

	    Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	    Uri newUri = contentResolver.insert(base, values);

	    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
	    Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
	  
	    String soundName = "sound-" +  System.currentTimeMillis();
	    Intent intent = new Intent("com.soundcloud.android.SHARE")
	      .putExtra(Intent.EXTRA_STREAM, newUri)
	      .putExtra("com.soundcloud.android.extra.title", soundName);

	    try {
	        // takes the user to the SoundCloud sharing screen
	        startActivityForResult(intent, 0);
	    } catch (ActivityNotFoundException e) {
	        // SoundCloud Android app not installed, show a dialog etc.
	    }

	    AbserApp appUserName = (AbserApp)getApplicationContext();
	    String name = appUserName.getName();
	    soundUrl = "soundcloud.com/"+ name + "/" + soundName ;   
	    
	  
	  }
	
	
	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
		  startActivity(intent);
	  }
	  
	 

	    
}
