package com.tripman;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.transform.TransformerConfigurationException;

import com.tripman.engine.BoTrip;
import com.tripman.engine.BoTripManager;
import com.tripman.engine.BoVisitManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
//import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditTripActivity extends Activity
{
	BoTrip iEditTrip;
	static final int SDATE_DIALOG_ID = 999;
	static final int EDATE_DIALOG_ID = 998;
	DatePickerDialog iStartDateDialog;
	DatePickerDialog iEndDateDialog;
	
	private DatePickerDialog.OnDateSetListener iDatePickerListener 
    = new DatePickerDialog.OnDateSetListener() {
	public void onDateSet(DatePicker aPicker, int aYear, int aMonth, int aDay) {

		
		if(iStartDateDialog.getDatePicker() == aPicker)
		{
			iEditTrip.getStartDate().set(Calendar.YEAR, aPicker.getYear());
			iEditTrip.getStartDate().set(Calendar.MONTH, aPicker.getMonth());
			iEditTrip.getStartDate().set(Calendar.DAY_OF_MONTH, aPicker.getDayOfMonth());
			
			DateFormat dateFormat1 = SimpleDateFormat.getDateInstance();
			TextView sd = (TextView) findViewById(R.id.SDvalueLabel);
		    sd.setText(dateFormat1.format(iEditTrip.getStartDate().getTime()));
		}
		else if(iEndDateDialog.getDatePicker() == aPicker)
		{
			iEditTrip.getEndDate().set(Calendar.YEAR, aPicker.getYear());
			iEditTrip.getEndDate().set(Calendar.MONTH, aPicker.getMonth());
			iEditTrip.getEndDate().set(Calendar.DAY_OF_MONTH, aPicker.getDayOfMonth());
			
			DateFormat dateFormat1 = SimpleDateFormat.getDateInstance();
			TextView ed = (TextView) findViewById(R.id.EDvalueLabel);
		    ed.setText(dateFormat1.format(iEditTrip.getEndDate().getTime()));
		}
	}
	};

	private OnClickListener iSaveButtonClickListener = new OnClickListener() {
        public void onClick(View v) {
        // BoTrip ti=new BoTrip();
        	try {
        		EditText name = (EditText) findViewById(R.id.tripNameText);
        		
        		if (0 != iEditTrip.getTripName().compareTo(name.getText().toString()))
        		{
        			if (BoTripManager.getInstance().getslectedindex()>=0)
        				BoTripManager.getInstance().renameTrip(iEditTrip.getTripName(), name.getText().toString());
        			
        			iEditTrip.setTripName(name.getText().toString());
        		}
        	    
				BoTripManager.getInstance().serializeTrip(iEditTrip);
				BoTripManager.getInstance().addTrip(iEditTrip);
				
				Intent listActivity= new Intent(AddEditTripActivity.this, TripManListActivity.class);	
				startActivity(listActivity);
				
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
        }
    };
    
   
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.add_modify_trip);
	    
	    iEditTrip = BoTripManager.getInstance().getEditingTrip();
	    EditText name = (EditText) findViewById(R.id.tripNameText);
	    name.setText(iEditTrip.getTripName());
	    DateFormat dateFormat1 = SimpleDateFormat.getDateInstance();
	    
	    //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy",Locale.US);
	    
	    EditText sd = (EditText) findViewById(R.id.SDvalueLabel);
	    sd.setText(dateFormat1.format(iEditTrip.getStartDate().getTime()));
	    // 
	    sd.setOnFocusChangeListener(new OnFocusChangeListener() {
///
			public void onFocusChange(View arg0, boolean hasFocus) {
				if (hasFocus) {
					showDialog(SDATE_DIALOG_ID);
				}
				else
				{
					dismissDialog(SDATE_DIALOG_ID);
				}
			}
	    
	    });
	    
	    if(BoTripManager.getInstance().getslectedindex()>=0)
	    {
	    	//fillActivityData();
	    	makeViewReadOnly();
	    	//makeWritable();
	    }
	    EditText ed = (EditText) findViewById(R.id.EDvalueLabel);
	    ed.setText(dateFormat1.format(iEditTrip.getEndDate().getTime()));
	    ed.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View arg0, boolean hasFocus) {
				if (hasFocus) {
					showDialog(EDATE_DIALOG_ID);
				}
				else
				{
					dismissDialog(EDATE_DIALOG_ID);
				}
			}
	    
	    });
	    //R.id.tripNameText.
	    

	    Button saveButton = (Button)findViewById(R.id.Savebutton);
	    
	    saveButton.setOnClickListener(iSaveButtonClickListener);
	    
	    iStartDateDialog = new DatePickerDialog(this, iDatePickerListener, 
	    		iEditTrip.getStartDate().get(Calendar.YEAR),
	    		iEditTrip.getStartDate().get(Calendar.MONTH),
	    		iEditTrip.getStartDate().get(Calendar.DAY_OF_MONTH));
	    
	    iEndDateDialog = new DatePickerDialog(this, iDatePickerListener, 
	    		iEditTrip.getEndDate().get(Calendar.YEAR),
	    		iEditTrip.getEndDate().get(Calendar.MONTH),
	    		iEditTrip.getEndDate().get(Calendar.DAY_OF_MONTH));
	}
	
	@Override
	 protected Dialog onCreateDialog(int id) {
		
		Dialog dateDialog = null;
	  switch (id) {
	  case SDATE_DIALOG_ID:
	     // set date picker as current date
		  dateDialog = iStartDateDialog;

		  break;
	     
	  case EDATE_DIALOG_ID:
		     // set date picker as current date
		  dateDialog = iEndDateDialog;
		  break;
	  }
	  
	  return dateDialog;
	}

	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	 // MenuInflater menuInflater = getMenuInflater();
	   //     menuInflater.inflate(R.menu.detailview_menu, menu);

	     //   return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.trip_edit_menu, menu);
		return true;
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId())
	    {
	    case R.id.action_edit:
		{				
			makeWritable();
		}
		break;	
	    }
		return true;
		}
	
	private void makeViewReadOnly()
	{
		EditText name = (EditText) findViewById(R.id.tripNameText);
	    name.setEnabled(false);
	    
	    EditText sd = (EditText) findViewById(R.id.SDvalueLabel);
	    sd.setEnabled(false);
	    
	    EditText ed = (EditText) findViewById(R.id.EDvalueLabel);
	    ed.setEnabled(false);
	    
	    Button saveButton = (Button)findViewById(R.id.Savebutton);
	    saveButton.setVisibility(Button.INVISIBLE);
	}
	
	
	 private void fillActivityData()
	 {
		    BoTrip trip= BoTripManager.getInstance().getTripAt(BoVisitManager.getInstance().getSelecteditemindex());
            EditText name = (EditText) findViewById(R.id.tripNameText);
		    name.setText(trip.getTripName());
		    
		      
		    TextView sd = (TextView) findViewById(R.id.SDvalueLabel);
			DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
			sd.setText(dateformat1.format(trip.getStartDate().getTime()));

		    
		   // EditText ed = (EditText) findViewById(R.id.EDvalueLabel);
		    //ed.setEnabled(true);
		    
		    TextView ed = (TextView) findViewById(R.id.EDvalueLabel);
			DateFormat dateformat2 = SimpleDateFormat.getDateInstance();
			ed.setText(dateformat2.format(trip.getEndDate().getTime()));

			//Button Rbt = (Button) findViewById(R.id.radioButton1);
			  //Rbt.setClickable(trip.getType());

		    
		    Button saveButton = (Button)findViewById(R.id.Savebutton);
		    saveButton.setVisibility(Button.VISIBLE);
	 }
	 
	
	 private void makeWritable()
	 {
		 EditText name = (EditText) findViewById(R.id.tripNameText);
		    name.setEnabled(true);
		    
		    EditText sd = (EditText) findViewById(R.id.SDvalueLabel);
		    sd.setEnabled(true);
		    
		    EditText ed = (EditText) findViewById(R.id.EDvalueLabel);
		    ed.setEnabled(true);
		    
		    Button saveButton = (Button)findViewById(R.id.Savebutton);
		    saveButton.setVisibility(Button.VISIBLE);
	 }
	 
}


