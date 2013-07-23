package com.tripman;
//import android.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.transform.TransformerException;

import com.tripman.engine.BoAudioCapture;
import com.tripman.engine.BoExpenseManager;
import com.tripman.engine.BoImageCapture;
import com.tripman.engine.BoVisit;
import com.tripman.engine.BoVisitManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

public class BoVisitAddEditActivity extends Activity 
{	
	static final int DATE_DIALOG_ID = 999;
	Calendar iLocalCalender = Calendar.getInstance();

	@Override
	 protected Dialog onCreateDialog(int id) {
	  switch(id) {
	  case DATE_DIALOG_ID:
	     // set date picker as current date
		  Calendar test = Calendar.getInstance();
	 
		  if(BoVisitManager.getInstance().getSelecteditemindex() >=0)
			  test = BoVisitManager.getInstance().getVisitAt(BoVisitManager.getInstance().getSelecteditemindex()).getVisitDateTime();
		  
	     return new DatePickerDialog(this, datePickerListener,test.get(Calendar.YEAR), test.get(Calendar.MONTH),test.get(Calendar.DAY_OF_MONTH));
	  }
	  return null;
	 }
	 
	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		 public void onDateSet(DatePicker aPicker, int aYear, int aMonth, int aDay) {

				iLocalCalender.set(Calendar.YEAR, aPicker.getYear());
				iLocalCalender.set(Calendar.MONTH, aPicker.getMonth());
				iLocalCalender.set(Calendar.DAY_OF_MONTH, aPicker.getDayOfMonth());
				
				TextView tv = (TextView) findViewById(R.id.date);
				DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
				tv.setText(dateformat1.format(iLocalCalender.getTime()));
			 }
			 };
	
 	@Override
			protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//Calendar cal= Calendar.getInstance();
			setContentView(R.layout.visit_add_edit_activity);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy",Locale.US);
		    
			 EditText sd = (EditText) findViewById(R.id.date);
			 DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
			 sd.setText(dateformat1.format(Calendar.getInstance().getTime()));
			 sd.setOnFocusChangeListener(new OnFocusChangeListener() {
					///
								public void onFocusChange(View arg0, boolean hasFocus) {
									if (hasFocus) {
										showDialog(DATE_DIALOG_ID);
									}
									else
									{
										dismissDialog(DATE_DIALOG_ID);
									}
								}
				});
			 
				
			 if (BoVisitManager.getInstance().getSelecteditemindex() >= 0)
			{
				fillActivityData();
				 setAllReadOnly();
			}
		
		final Button bt=(Button)findViewById(R.id.Add);
		bt.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				boolean dataerror=false;
				
				EditText pname = (EditText) findViewById(R.id.pname);
				EditText description = (EditText) findViewById(R.id.description);
				EditText address = (EditText) findViewById(R.id.address);
				EditText phoneno = (EditText) findViewById(R.id.phoneno);
				EditText email = (EditText) findViewById(R.id.email);
				
				String pname1 = pname.getText().toString();
				String description1 = description.getText().toString();
				String address1 = address.getText().toString();
				String phoneno1=phoneno.getText().toString();
				String email1=email.getText().toString();
				
				if(pname.getText().toString().equalsIgnoreCase(""))  
				{
				   dataerror=true;
				   Toast.makeText(getBaseContext(), "name field is requried",Toast.LENGTH_SHORT).show();
				}	
				
				if(description.getText().toString().equalsIgnoreCase(""))
					   
				{
				   dataerror=true;
				   Toast.makeText(getBaseContext(), "description field is empty",Toast.LENGTH_SHORT).show();
				}	
				
				if(address.getText().toString().equalsIgnoreCase(""))
					   
				{
				   dataerror=true;
				   Toast.makeText(getBaseContext(), "address field is empty",Toast.LENGTH_SHORT).show();
				}
				
				if(phoneno.getText().toString().equalsIgnoreCase(""))
					   
				{
				   dataerror=true;
				   Toast.makeText(getBaseContext(), "Phone number field is empty",Toast.LENGTH_SHORT).show();
				}	
				
				if(email.getText().toString().equalsIgnoreCase(""))
					   
				{
				   dataerror=true;
				   Toast.makeText(getBaseContext(), "email field is empty",Toast.LENGTH_SHORT).show();
				}	

				
				BoVisit visit;
				if (BoVisitManager.getInstance().getSelecteditemindex() >= 0)
				{
					visit = BoVisitManager.getInstance().getVisitAt(BoVisitManager.getInstance().getSelecteditemindex());
				}
				else
				{
					visit=new BoVisit();
				}
				
				visit.setName(pname1);
				visit.setVisitDateTime(iLocalCalender);
				visit.setDescription(description1);
				visit.setAddr(address1);
				visit.setEmail(email1);
				visit.setPhonenumber(phoneno1);
				
				Toast.makeText(getBaseContext(), pname1+" "+description1+"" +""+ email1+""+phoneno1+"", Toast.LENGTH_LONG).show();
				
				if(false==dataerror)
				{
					if (BoVisitManager.getInstance().getSelecteditemindex() >= 0)
				{
					BoVisitManager.getInstance().modifyVisit(visit);
				}
				else
				{
					BoVisitManager.getInstance().addVisit(visit);
				}
					String filePath = getBaseContext().getFilesDir().getPath().toString();

                try {
						//BoVisitManager.getInstance().serialize(BoVisitManager.getInstance().getCurrentTripVisitFile());
                	BoVisitManager.getInstance().serialize(filePath+"BoPlaceVisit.xml");

				} catch (TransformerException e) {
					e.printStackTrace();
				}
				
				Intent listActivity = new Intent(BoVisitAddEditActivity.this,TripDetailTabActivity.class);
				startActivity(listActivity);
				//finish();
				
			}}
		});
		
		final Button mbt=(Button)findViewById(R.id.media);
		mbt.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent mediaActivity = new Intent(BoVisitAddEditActivity.this,BoMediaDisplay.class);
				BoVisit visit1 = BoVisitManager.getInstance().getVisitAt(BoVisitManager.getInstance().getSelecteditemindex());
				mediaActivity.putExtra("media_directory", BoVisitManager.getInstance().getPlaceVisitMediaDirectory(visit1.getName()));
				startActivity(mediaActivity);
			}
		});
		
			 
	   final Button button = (Button) findViewById(R.id.Cancel);
 
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				EditText pname = (EditText) findViewById(R.id.pname);
				EditText description = (EditText) findViewById(R.id.description);
				EditText address = (EditText) findViewById(R.id.address);
			    EditText phoneno = (EditText) findViewById(R.id.phoneno);
				EditText email = (EditText) findViewById(R.id.email);
				
				pname.setText(" ");
				description.setText(" ");
				address.setText(" ");
				phoneno.setText(" ");
				email.setText(" ");
				
				Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_LONG).show();
				Intent listvisit = new Intent(BoVisitAddEditActivity.this,BoVisitList.class);
				startActivity(listvisit);
			}
		});
 	}
 	
 	protected int length() {
		// TODO Auto-generated method stub
		return 0;
	}

 	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	 // MenuInflater menuInflater = getMenuInflater();
	   //     menuInflater.inflate(R.menu.detailview_menu, menu);

	     //   return super.onCreateOptionsMenu(menu);
 		getMenuInflater().inflate(R.menu.detailview_menu, menu);
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
	    case R.id.action_delete:
	    {
	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Confirm Delete");

		// set dialog message
		alertDialogBuilder
			.setMessage("Are you sure you want to DELETE")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int dia_id) {
					int id= BoVisitManager.getInstance().getVisitIdAt(BoVisitManager.getInstance().getSelecteditemindex());
			    	if(-1 !=id)
			    		BoVisitManager.getInstance().removeVisit(id);
					try {
						BoVisitManager.getInstance().serialize(BoVisitManager.getInstance().getCurrentTripVisitFile());
					} catch (TransformerException e) {
						e.printStackTrace();
					}
					
					Intent intent = new Intent(getBaseContext(), TripDetailTabActivity.class);
				    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    startActivity(intent);
				    finish();
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
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
 	
    private void fillActivityData()
	{
		BoVisit visit1 = BoVisitManager.getInstance().getVisitAt(BoVisitManager.getInstance().getSelecteditemindex());
		
		EditText visitpname=(EditText)findViewById(R.id.pname);
		visitpname.setText(visit1.getName());
		
		EditText visitdes=(EditText)findViewById(R.id.description);
		visitdes.setText(visit1.getDescription());
		
		EditText visitaddr=(EditText)findViewById(R.id.address);
		visitaddr.setText(visit1.getAddr());
		
		EditText visitmail=(EditText)findViewById(R.id.email);
		visitmail.setText(visit1.getEmail());
		
		EditText visitphone=(EditText)findViewById(R.id.phoneno);
		visitphone.setText(visit1.getPhonenumber());
		
		Button bt = (Button)findViewById(R.id.Add);
		bt.setText("Save");
		
		TextView tv = (TextView) findViewById(R.id.date);
		DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
		tv.setText(dateformat1.format(visit1.getVisitDateTime().getTime()));

		
	}
    private void setAllReadOnly()
    {
    	EditText visitpname=(EditText)findViewById(R.id.pname);
    	visitpname.setEnabled(false);
		
    	TextView date = (TextView) findViewById(R.id.date);
		date.setEnabled(false);
    	//DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
		//tv.setText(dateformat1.format(visit1.getVisitDateTime().getTime()));
    	
    	EditText visitdes=(EditText)findViewById(R.id.description);
    	visitdes.setEnabled(false);
		
		EditText visitaddr=(EditText)findViewById(R.id.address);
		visitaddr.setEnabled(false);
		
		EditText visitmail=(EditText)findViewById(R.id.email);
		visitmail.setEnabled(false);
		
		EditText visitphone=(EditText)findViewById(R.id.phoneno);
		visitphone.setEnabled(false);	
		
		Button bt = (Button)findViewById(R.id.Add);
		bt.setVisibility(Button.INVISIBLE);
		
		Button button = (Button) findViewById(R.id.Cancel);
		button.setVisibility(Button.INVISIBLE);
		 
		Button med=(Button)findViewById(R.id.media);
		med.setVisibility(Button.VISIBLE);
    }
    
    private void makeWritable()
    {
    	EditText visitpname=(EditText)findViewById(R.id.pname);
    	visitpname.setEnabled(true);
    	
    	TextView date = (TextView) findViewById(R.id.date);
		date.setEnabled(true);
    	//DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
		//tv.setText(dateformat1.format(visit1.getVisitDateTime().getTime()));
		
    	EditText visitdes=(EditText)findViewById(R.id.description);
    	visitdes.setEnabled(true);
		
		EditText visitaddr=(EditText)findViewById(R.id.address);
		visitaddr.setEnabled(true);
		
		EditText visitmail=(EditText)findViewById(R.id.email);
		visitmail.setEnabled(true);
		
		EditText visitphone=(EditText)findViewById(R.id.phoneno);
		visitphone.setEnabled(true);	
		
		Button bt = (Button)findViewById(R.id.Add);
		bt.setVisibility(Button.VISIBLE);
		
		Button button = (Button) findViewById(R.id.Cancel);
		button.setVisibility(Button.INVISIBLE);
		
		Button med=(Button)findViewById(R.id.media);
		med.setVisibility(Button.INVISIBLE);
    }
}

			