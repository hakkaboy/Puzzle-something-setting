package f5.puzzle.something.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;


public class Setting extends Activity {

	SeekBar seekbar;
	AudioManager audioManager;
	int maxVolume;
	int curVolume;
	MediaPlayer mp;	
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        findViews();
        setListensers();
    }
    

    private void findViews(){
    	seekbar = (SeekBar)findViewById(R.id.seekBar1);
    	audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    	maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    	curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    	seekbar.setMax(maxVolume);
    	seekbar.setProgress(curVolume);
    	
    }
    
    
    //Setting Cancel Button Event Handler 
    public void onSettingCancel(View view){
    	Intent intent = new Intent();
    	Bundle bundle = new Bundle();
    	bundle.putString("switch", "other");
    	intent.putExtras(bundle);
    	setResult(RESULT_OK,intent);  
    	finish();
    }
    
    //Setting On Radio Button Event Handler
    public void onVolume(View v){
    	Intent i=new Intent();
    	Bundle b=new Bundle();
    	b.putString("switch", "on");
    	i.putExtras(b);
    	setResult(RESULT_OK,i);
    	finish();    	
    	//audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
    
    
    //Setting Off Radio Button Event Handler
    public void offVolume(View v){
    	Intent i=new Intent();
    	Bundle b=new Bundle();
    	b.putString("switch", "off");
    	i.putExtras(b);
    	setResult(RESULT_OK,i);    	
    	finish();    	
    	//audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }    
    
        
    //Seekbar Event Handler and Listener
    private void setListensers() {
    	seekbar.setOnSeekBarChangeListener(settingButton);
    }          
    private SeekBar.OnSeekBarChangeListener settingButton = new SeekBar.OnSeekBarChangeListener(){
    	
    	 //@Override
    	 public void onStopTrackingTouch(SeekBar arg0) {
    		 // TODO Auto-generated method stub
    		 System.out.println(arg0.toString());
    	 }

    	 //@Override
    	 public void onStartTrackingTouch(SeekBar arg0) {
    	  // TODO Auto-generated method stub
    		 System.out.println(arg0.toString());

    	 }

    	 //@Override
    	 public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
    	  // TODO Auto-generated method stub
    		 System.out.println(arg0.toString());
    		 audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
    	 }
    	 
    };    

       
}