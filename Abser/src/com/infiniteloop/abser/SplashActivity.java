package com.infiniteloop.abser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends Activity{
	
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		StartAnimations();
		
		
		// TODO Auto-generated method stub
		Thread timer = new Thread(){
			@Override
			public void run() {
				try {
					for (int i = 0; i < 3000; i++) {
						sleep(1);
					}
					Intent intent = new Intent(getApplicationContext(), ImageCaptureActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
				}
				finally{
					finish();
				}
			}
		};
		timer.start();
	}
	
	private void StartAnimations() {
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        anim.reset();
//        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
//        l.clearAnimation();
//        l.startAnimation(anim);
 
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
//        anim.reset();
//        ImageView iv = (ImageView) findViewById(R.id.abser_logo);
//        iv.clearAnimation();
//        iv.startAnimation(anim);
        
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        LinearLayout ll = (LinearLayout) findViewById(R.id.moving_layout);
        ll.clearAnimation();
        ll.startAnimation(anim);
        
        anim = AnimationUtils.loadAnimation(this, R.anim.translate2);
        anim.reset();
        ImageView im = (ImageView) findViewById(R.id.imageView1);
        im.clearAnimation();
        im.startAnimation(anim);
        
//        anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        anim.reset();
//        TextView tv = (TextView) findViewById(R.id.abser_text);
//        tv.clearAnimation();
//        tv.startAnimation(anim);
 
    }
	
	
}
