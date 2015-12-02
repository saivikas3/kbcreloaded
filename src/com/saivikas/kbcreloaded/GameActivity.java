package com.saivikas.kbcreloaded;





import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	Cursor c=null;
	DatabaseHelper myDbHelper;
	final static int QUES = 12;
	TextView ques;
	int idval;
	static int counter = 0;
	RadioButton r4,r1,r2,answered, r3;
	TextView count,mTextField;
	String questionString = "b";
	static String ans = null;
	static int over[] = new int[QUES];
	static int i = 0;
	CountDownTimer ct;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        counter = 0;
        
        ques = (TextView) findViewById(R.id.question);
        mTextField = (TextView) findViewById(R.id.timeleft);
        count = (TextView) findViewById(R.id.count);
        r1 = (RadioButton) findViewById(R.id.radioButton1);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);
        r4 = (RadioButton) findViewById(R.id.radioButton4);
        
        r1.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
        r4.setOnClickListener(this);
        
        connectToDatabase();
        idval = generateRandomNumber(counter);
       
        //Toast.makeText(getApplicationContext(), Integer.toString(idval), 5).show();
        if(idval>0 && idval<30)
        	ans = updateQuestion(idval);
        
        

        
               
        
    }
    
    public String updateQuestion(int id) {
    	
    	try {
    	    
	 	
        	c = myDbHelper.rawquery("select * from thequestions where _id="+Integer.toString(id), null);
        }catch(Exception e){
        	Toast.makeText(getApplicationContext(), e.toString(), 5).show();
        }
       	c.moveToFirst();
      	count.setText(Integer.toString(counter+1)+". ");
       	questionString = c.getString(1);
       	String answer = c.getString(6);
        ques.setText(questionString);
        r1.setText(c.getString(2));
        r2.setText(c.getString(3));
        r3.setText(c.getString(4));
        r4.setText(c.getString(5));
        
        //set check false
        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);
        
        ct = new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("Seconds left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
            	Toast.makeText(getApplicationContext(), "Time up!!", 5).show();
            	finish();
                gameOver();
            }
         }.start();
        
        return answer;
        
    }
    
    public int generateRandomNumber(int counter) {
		int rand = 0;
		boolean present = false;
		SecureRandom srand = new SecureRandom();
		if(counter<4) {
			rand = srand.nextInt(10) + 1;
		}
		else if(counter >= 4 && counter<8) {
			rand = srand.nextInt(10) + 10;
		}
		else if(counter>=8 && counter<12) {
			rand = srand.nextInt(10) + 20;
		}
		
		
    	
    	
    	return rand;
    }
    
    private void connectToDatabase() {
    	myDbHelper = new DatabaseHelper(GameActivity.this);
        try {
 
        	myDbHelper.createDataBase();
 
        } catch (IOException ioe) {
 
        	throw new Error("Unable to create database");
 
        }	
 
        try {
 
        	myDbHelper.openDataBase();
 
        }catch(SQLException sqle){
 
        	throw sqle;
 
        }
    }
    
    public void gameOver() {
    	
    	Intent nextClass = new Intent(GameActivity.this, Result.class);
		nextClass.putExtra("comment", "Better luck next time!!");
		nextClass.putExtra("score", Integer.toString(counter));
		startActivity(nextClass);
		finish();
    }    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String userans = null;
		switch(v.getId()) {
		case R.id.radioButton1: 
			userans = (String) r1.getText();
			break;
		case R.id.radioButton2: 
			userans = (String) r2.getText();
			break;
		case R.id.radioButton3: 
			userans = (String) r3.getText();
			break;
		case R.id.radioButton4: 
			userans = (String) r4.getText();
			break;
		}		
		
        if(userans.equalsIgnoreCase(ans)) {
        	if(counter<11) {
        		counter++;
        		Toast.makeText(getApplicationContext(), "Correct!",5).show();
        		//prevent repitition
        		while(true) {
        		boolean present = false;
        		idval = generateRandomNumber(counter);
        		for(int j=0;j<QUES;j++) {
        			if(over[j] == idval)
        				present = true;
        		}
        		
        		if(present == true)
        			continue;
        		else {
        			over[i++] = idval;
        			break;
        		}
        			
        		}	
        		ct.cancel();
        		ans = updateQuestion(idval);
        	}
        	else if(counter>=11) {
        		//go to result class
        		ct.cancel();
        		Intent nextClass = new Intent(GameActivity.this, Result.class);
        		nextClass.putExtra("comment", "You Win!!");
        		nextClass.putExtra("score", "12");
        		startActivity(nextClass);
        		finish();
        	}
        }
        
        else {
        	//go to result class
        	ct.cancel();
        	Toast.makeText(getApplicationContext(),"Sorry, wrong answer!",5).show();
        	gameOver();
        }
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
	            startActivity(new Intent(GameActivity.this, MainActivity.class));
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


