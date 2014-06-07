package br.edu.fa7.heppyhour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashScreen extends Activity {

	 Handler handler;
	    Runnable runnable;
	     
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_splash_screen);
	         
	        handler = new Handler();
	         
	    }
	     
	    @Override
	    protected void onResume() {
	         
	        runnable = new Runnable() {
	             
	            @Override
	            public void run() {
	                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
	                startActivity(intent);
	                finish();
	            }
	        };
	        handler.postDelayed(runnable, 3000);
	        super.onResume();
	    }
	 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	/**
	 * A placeholder fragment containing a simple view.
	 *
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_splash_screen,
					container, false);
			return rootView;
		}
	}*/

}
