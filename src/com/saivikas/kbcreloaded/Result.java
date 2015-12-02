package com.saivikas.kbcreloaded;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		TextView comment = (TextView) findViewById(R.id.comment);
		TextView score = (TextView) findViewById(R.id.score);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		String comm = (String) b.get("comment");
		String scor = (String) b.get("score");
		if(comm!=null && comm == "win")
			comment.setText("Congratulations! You WIN!!");
		else
			comment.setText("Better luck next time!!");
		
		score.setText(scor);
		
	}
	
	@Override
	public void onBackPressed() {
		//close the app
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	    alertDialogBuilder.setMessage("Please choose an option.");
	    alertDialogBuilder.setPositiveButton("Restart", 
	      new DialogInterface.OnClickListener() {
			
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	            startActivity(new Intent(Result.this, MainActivity.class));
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
