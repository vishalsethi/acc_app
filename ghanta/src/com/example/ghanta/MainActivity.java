package com.example.ghanta;

//import com.example.accelerometer.R;

//import java.io.IOException;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageView;
//import android.view.View;
//import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements SensorEventListener {

	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized; private SensorManager mSensorManager; private Sensor mAccelerometer; private final float NOISE = (float) 2.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
        }
        public void onSensorChanged(SensorEvent event) {
        	
        	ImageView iv = (ImageView)findViewById(R.id.imageView1);
        	MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.audio1);
        	TextView disp = (TextView) findViewById(R.id.head);
        	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        	
        	//long [] pattern = {0,1000,100};
        	
        	float x = event.values[0];
        	float y = event.values[1];
        	float z = event.values[2];
        	if (!mInitialized) {
        	mLastX = x;
        	mLastY = y;
        	mLastZ = z;
        	disp.setText("GHANTA");
        	mInitialized = true;
        	} else {
        	float deltaX = Math.abs(mLastX - x);
        	float deltaY = Math.abs(mLastY - y);
        	float deltaZ = Math.abs(mLastZ - z);
        	if (deltaX < NOISE) deltaX = (float)0.0;
        	if (deltaY < NOISE) deltaY = (float)0.0;
        	if (deltaZ < NOISE) deltaZ = (float)0.0;
        	
        	mLastX = x;
        	mLastY = y;
        	mLastZ = z;
   
        	
        	iv.setImageResource(R.drawable.bells);
        	if((deltaX + deltaY + deltaZ)>= 5 || (deltaX + deltaY + deltaZ)<=-5 ){
        		try {
					mp.start();
					iv.setVisibility(View.VISIBLE);
					//v.vibrate(pattern, 0);
					v.vibrate(4000);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					
				} 
        	}
        	if((deltaX+deltaY+deltaZ)<5 && (deltaX + deltaY + deltaZ)>-5){
        		try{
        			mp.stop();
        			mp.reset();
        			mp.prepare();
        			iv.setVisibility(View.INVISIBLE);
        			v.cancel();
        		}
        		catch(IllegalStateException a)
        		{
  
        		}
        		catch (IOException e) {
					// TODO Auto-generated catch block
					
				}
        	}
        	}
        }	
        	
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
