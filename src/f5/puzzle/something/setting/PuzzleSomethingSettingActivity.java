package f5.puzzle.something.setting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class PuzzleSomethingSettingActivity extends Activity {
	
	
	Facebook facebook = new Facebook("166331713495110");
	AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
	
	
	//Media player
	MediaPlayer mp;
	boolean pause = true; // is media playing?!
			
	//Media player volume relatives
	AudioManager audioManager;
	int maxVolume;
	int curVolume;		
	SeekBar seekbar2;
	
	//Setting pop-window
	View popupWindowView;
	PopupWindow popupWindow;
	View popupWindowCoverView;
	PopupWindow popupWindowCover;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mediaPlayFunc(); // play the media
		defaultSetting();
	}
	
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("data" + data.toString());
        facebook.authorizeCallback(requestCode, resultCode, data);
    }	
	
	/**** facebook single sign on button *****/
    public void facebookOnClick(View view){

		//facebook relatives
        facebook.authorize(this, new DialogListener() {
            @Override
            public void onComplete(Bundle values) {
            	System.out.println("values: " + values.toString());           	
            }

            @Override
            public void onFacebookError(FacebookError error) {}

            @Override
            public void onError(DialogError e) {}

            @Override
            public void onCancel() {}
        });		
		  
    	
    }
    
    
    /**** facebook logout button *****/
    public void logoutOnClick(View view){

    	mAsyncRunner.logout(this, new RequestListener() {
    		  @Override
    		  public void onComplete(String response, Object state) {}
    		  
    		  @Override
    		  public void onIOException(IOException e, Object state) {}
    		  
    		  @Override
    		  public void onFileNotFoundException(FileNotFoundException e,
    		        Object state) {}
    		  
    		  @Override
    		  public void onMalformedURLException(MalformedURLException e,
    		        Object state) {}
    		  
    		  @Override
    		  public void onFacebookError(FacebookError e, Object state) {}
    		});    	
    	
    	
    }
	
	/****** Find the relative views and configure some default setting ******/
	public void defaultSetting() {

		//Pop-up window setting
		popupWindowView = this.getLayoutInflater().inflate(R.layout.popupwindow, null);
		popupWindowView.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupWindowCover));
		//popupWindowCoverView = this.getLayoutInflater().inflate(R.layout.popupwindowcover, null);
		//popupWindowCover = new PopupWindow(popupWindowView,500,900,true); 
		popupWindow = new PopupWindow(popupWindowView,500,400,true);
		
						
		//Find pop-up window views
		Button windowClose = (Button)popupWindowView.findViewById(R.id.closePopupWindow);
		RadioButton radioOn = (RadioButton)popupWindowView.findViewById(R.id.windowRadioOn);
		RadioButton radioOff = (RadioButton)popupWindowView.findViewById(R.id.windowRadioOff);		
		seekbar2 = (SeekBar)popupWindowView.findViewById(R.id.windowSeekBarVolume);
		
		//Media player volume setting
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		seekbar2.setMax(maxVolume);
		seekbar2.setProgress(curVolume);

		
		//Radio off button control
		seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			// @Override
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}

			// @Override
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			}

			// @Override
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
			}

		});
		
		//Radio on button control
		radioOn.setOnClickListener(new RadioButton.OnClickListener(){ 
			
			
			@Override
			public void onClick(View v){
				if (pause == true) {
					pause = false;
					mp.start();
				}// end if				
			}
			
		});

		//Set radio off
		radioOff.setOnClickListener(new RadioButton.OnClickListener(){ 
			
			
			@Override
			public void onClick(View v){
				if (pause == false) {
					pause = true;
					mp.pause();
				}// end if				
			}
			
		});
		

		
		//Close pop-up window
		windowClose.setOnClickListener(new Button.OnClickListener(){ 
			
			
			@Override
			public void onClick(View v){
				popupWindow.dismiss();
			}
			
		});		
		
		
	}

	/**** Event listener implementation by setting layout onClick ****/
	public void settingOnClick(View view) {
		
		/*
		Intent intent = new Intent();
		intent.setClass(PuzzleSomethingSettingActivity.this, Setting.class);
		// startActivity(intent); //no response
		startActivityForResult(intent, 0); // with response from another
		*/
		popupWindow.showAsDropDown(view);
		
	}

	/**** Media player construction ****/
	public void mediaPlayFunc() {
		mp = MediaPlayer.create(PuzzleSomethingSettingActivity.this,R.raw.imyours);
		pause = false;
		mp.start();
	}

		
}