package com.tripman;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.transform.TransformerException;

import com.tripman.engine.BoActivityManager;
import com.tripman.engine.BoExpenseManager;
import com.tripman.engine.BoVisit;
import com.tripman.engine.BoVisitManager;


import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
//import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tripman.engine.*;

public class BoActivity_add_modify extends Activity 
{
	static final int DATE_DIALOG_ID = 999;
	Calendar iLocalCalender = Calendar.getInstance();

	  
	  @Override
	  protected Dialog onCreateDialog(int id) 
	  {
	      switch (id) {
	         case DATE_DIALOG_ID:
	    	     // set date picker as current date
	    		  Calendar test = Calendar.getInstance();
	    	 
	    		  if(BoActivityManager.getInstance().getselectedindex() >=0)
	    			  test = BoActivityManager.getInstance().getActivityAt(BoActivityManager.getInstance().getselectedindex()).getActivityDateTime();
	    		  
	    	     return new DatePickerDialog(this, datePickerListener,test.get(Calendar.YEAR), test.get(Calendar.MONTH),test.get(Calendar.DAY_OF_MONTH));
	    	  }
	    	  return null;
	    	 }
	  
	  private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() 
	  {
		  public void onDateSet(DatePicker aPicker, int aYear, int aMonth, int aDay) 
		  {
                iLocalCalender.set(Calendar.YEAR, aPicker.getYear());
				iLocalCalender.set(Calendar.MONTH, aPicker.getMonth());
				iLocalCalender.set(Calendar.DAY_OF_MONTH, aPicker.getDayOfMonth());
				
				TextView tv = (TextView) findViewById(R.id.date);
				DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
				tv.setText(dateformat1.format(iLocalCalender.getTime()));
		  }
	  };

       @Override
		protected void onCreate(Bundle savedInstanceState) 
       {
		super.onCreate(savedInstanceState);
		Calendar cal= Calendar.getInstance();
		setContentView(R.layout.activity_add_modify);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy",Locale.US);

		
		EditText sd = (EditText) findViewById(R.id.date);
		DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
		sd.setText(dateformat1.format(Calendar.getInstance().getTime()));
		sd.setOnFocusChangeListener(new OnFocusChangeListener() 
		{
				            public void onFocusChange(View arg0, boolean hasFocus) 
							{
								if (hasFocus) 
								{
									showDialog(DATE_DIALOG_ID);
								}
								else
								{
									dismissDialog(DATE_DIALOG_ID);
								}
							}
		});	
		
		if (BoActivityManager.getInstance().getselectedindex() >= 0)
		{
			fillActivityData();
			makeViewReadOnly();
		}
		
		final Button bt=(Button)findViewById(R.id.add);
		bt.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) 
			{
            boolean dataerror=false;
				
				EditText name = (EditText) findViewById(R.id.activityname);
				EditText description = (EditText) findViewById(R.id.activitydescription);
				EditText duration = (EditText) findViewById(R.id.activityduration);
				EditText placeofactivity = (EditText) findViewById(R.id.placeofactivity);
				
				String name1 = name.getText().toString();
				String description1 = description.getText().toString();
				String duration1 = duration.getText().toString();
				String placeofactivity1 = placeofactivity.getText().toString();
				
				if(name.getText().toString().equalsIgnoreCase(""))
					   
				{
					dataerror=true;
					Toast.makeText(getBaseContext(), "name field is requried", Toast.LENGTH_SHORT).show();
				}	
				
				if(description.getText().toString().equalsIgnoreCase(""))
					   
				{
					dataerror=true;
					Toast.makeText(getBaseContext(), "description field is empty", Toast.LENGTH_SHORT).show();
				}	
				
				if(duration.getText().toString().equalsIgnoreCase(""))
					   
				{
					dataerror=true;
					Toast.makeText(getBaseContext(), "duration field is empty", Toast.LENGTH_SHORT).show();
				}	
				
				if(placeofactivity.getText().toString().equalsIgnoreCase(""))
					   
				{
					dataerror=true;
					Toast.makeText(getBaseContext(), "place field is empty", Toast.LENGTH_SHORT).show();
				}	
										 
				BoActivity bo;
				if (BoActivityManager.getInstance().getselectedindex() >= 0)
				{
					bo = BoActivityManager.getInstance().getActivityAt(BoActivityManager.getInstance().getselectedindex());
				}
				else
				{
					bo=new BoActivity();
				}
				int test = Integer.parseInt(duration1);
				bo.setActivityDateTime(iLocalCalender);
				bo.setName(name1);
				bo.setDescription(description1);
				bo.setDuration(Integer.parseInt(duration1));
				bo.setPlace(placeofactivity1);
				
				Toast.makeText(getBaseContext(), name1+"  "+description1+"" +""+ duration1+""+placeofactivity1+"", Toast.LENGTH_LONG).show();
				if(false==dataerror)
				{
				if (BoActivityManager.getInstance().getselectedindex() >= 0)
				{
					BoActivityManager.getInstance().modifyActivity(bo);
				}
				else
				{
					BoActivityManager.getInstance().addActivity(bo);
				}
				
							
				String filePath = getBaseContext().getFilesDir().getPath().toString();
				try 
				{
					//BoActivityManager.getInstance().serialize(BoActivityManager.getInstance().getCurrentTripActivityFile());
					BoActivityManager.getInstance().serialize(filePath+"BoActivity.xml");

				}
				catch (TransformerException e) 
				{
					e.printStackTrace();
				}
			
			/* code to link list activity page */
				
				
				Intent listActivity = new Intent(BoActivity_add_modify.this,TripDetailTabActivity.class);
				startActivity(listActivity);
			}
				
			}
		});
		
		final Button mbt=(Button)findViewById(R.id.media);
		mbt.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) 
			{
			Intent mediaActivity = new Intent(BoActivity_add_modify.this,BoMediaDisplay.class);
			BoActivity bo= BoActivityManager.getInstance().getActivityAt(BoActivityManager.getInstance().getselectedindex());
			mediaActivity.putExtra("media_directory", BoActivityManager.getInstance().getActivityMediaDirectory(bo.getName()));
			startActivity(mediaActivity);
			}
		});
				
		/* Cancel button listener*/	
		final Button bt1=(Button)findViewById(R.id.cancel);
		bt1.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) 
			{
				BoActivity act=new BoActivity();
				EditText name = (EditText) findViewById(R.id.activityname);
				EditText description = (EditText) findViewById(R.id.activitydescription);
				EditText duration = (EditText) findViewById(R.id.activityduration);
				EditText placeofactivity = (EditText) findViewById(R.id.placeofactivity);
				
				
				name.setText(" ");
				description.setText( " ");
				duration.setText(" ");
				placeofactivity.setText(" ");
				 
				Intent listActivity = new Intent(BoActivity_add_modify.this,BoActivityList.class);
				startActivity(listActivity);
			}
		
			
		});
		
	}
	
       protected int length() 
       {
   	    return 0;
   	   }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailview_menu, menu);
		return true;
	}

	
	private void fillActivityData()
	{
		BoActivity act = BoActivityManager.getInstance().getActivityAt(BoActivityManager.getInstance().getselectedindex());
		
		EditText name = (EditText) findViewById(R.id.activityname);
		name.setText(act.getName());
		EditText description = (EditText)findViewById(R.id.activitydescription);
		description.setText(act.getDescription());
		EditText duration = (EditText) findViewById(R.id.activityduration);
		duration.setText(String.valueOf(act.getDuration()));
		EditText place = (EditText)findViewById(R.id.placeofactivity);
		place.setText(act.getPlace());
		Button bt2=(Button)findViewById(R.id.add);
		bt2.setText("SAVE");
		TextView tv = (TextView) findViewById(R.id.date);
		DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
		tv.setText(dateformat1.format(act.getActivityDateTime().getTime()));

	}
	
	private void makeViewReadOnly()
	{
		EditText name = (EditText) findViewById(R.id.activityname);
		name.setEnabled(false);
		EditText description = (EditText)findViewById(R.id.activitydescription);
		description.setEnabled(false);
		EditText duration = (EditText)findViewById(R.id.activityduration);
		duration.setEnabled(false);
        EditText place = (EditText)findViewById(R.id.placeofactivity);
		place.setEnabled(false);
        Button bt2=(Button)findViewById(R.id.add);
		bt2.setVisibility(Button.INVISIBLE);
		Button bt1=(Button)findViewById(R.id.cancel);
		bt1.setVisibility(Button.INVISIBLE);
		TextView tv = (TextView) findViewById(R.id.date);
		tv.setEnabled(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		
		switch(item.getItemId())
	    {
	    case R.id.action_edit:
		{				
			makeWritable();
		}
		break;	
	    case R.id.action_delete:
	    {
	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			this);

		// set title
		alertDialogBuilder.setTitle("Confirm Delete");

		// set dialog message
		alertDialogBuilder
			.setMessage("Are you sure you want to DELETE")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog,int dia_id) 
				{
					int id= BoActivityManager.getInstance().getActivityIdAt(BoActivityManager.getInstance().getselectedindex());
			    	if(-1 !=id)
			    		BoActivityManager.getInstance().removeActivity(id);
					
					try 
					{
						BoActivityManager.getInstance().serialize(BoActivityManager.getInstance().getCurrentTripActivityFile());
					} 
					catch (TransformerException e) 
					{
						e.printStackTrace();
					}
					
					 Intent intent = new Intent(getBaseContext(), TripDetailTabActivity.class);
				     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				     startActivity(intent);
				     finish();
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog,int id) 
				{
					// if this button is clicked, just close
					// the dialog box and do nothing
					Toast.makeText(getBaseContext(), "NO", Toast.LENGTH_LONG).show();
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
   }
   break;
}
return true;
	}
	private void makeWritable()
    {
		EditText name = (EditText) findViewById(R.id.activityname);
		name.setEnabled(true);
        EditText description = (EditText)findViewById(R.id.activitydescription);
		description.setEnabled(true);
		EditText duration = (EditText)findViewById(R.id.activityduration);
		duration.setEnabled(true);
        EditText place = (EditText)findViewById(R.id.placeofactivity);
		place.setEnabled(true);
		Button bt2=(Button)findViewById(R.id.add);
		bt2.setVisibility(Button.VISIBLE);
		Button bt1=(Button)findViewById(R.id.cancel);
	    bt1.setVisibility(bt1.VISIBLE);
	    Button mbt=(Button)findViewById(R.id.media);
	    mbt.setVisibility(Button.INVISIBLE);
	    TextView tv = (TextView) findViewById(R.id.date);
		tv.setEnabled(true);
    }
}
	
