package com.saivikas.kbcreloaded;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(getApplicationContext(), "App Developed by Sai Vikas",5).show();
		b=(Button) findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, GameActivity.class);
				startActivity(intent);
				
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		//close the app
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	    alertDialogBuilder.setMessage("Please choose an option.");
	    alertDialogBuilder.setPositiveButton("Play", 
	      new DialogInterface.OnClickListener() {
			
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	            startActivity(new Intent(MainActivity.this, MainActivity.class));
				finish();
	         }
	      });
	      
	      alertDialogBuilder.setNegativeButton("Exit", 
	      new DialogInterface.OnClickListener() {
				
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	 Intent homeIntent = new Intent(Intent.ACTION_MAIN);
	     	     homeIntent.addCategory( Intent.CATEGORY_HOME );
	     	     homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	     	     startActivity(homeIntent);
	     	     finish();
	     	    
			 }
	      });
		    
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
		
		
		
		
		
	}
	
	
}
