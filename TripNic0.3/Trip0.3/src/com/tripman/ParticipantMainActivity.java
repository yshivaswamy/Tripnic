package com.tripman;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.TransformerException;

import com.tripman.R;
import com.tripman.engine.BoParticipant;
import com.tripman.engine.ParticipantManager;





import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ParticipantMainActivity extends Activity {

	static final int DATE_DIALOG_ID = 999;
	 int year = 0;
	 int month = 0;
	 int day = 0;
	 
	 @Override
	 protected Dialog onCreateDialog(int id) {
	  switch(id) {
	  case DATE_DIALOG_ID:
	     // set date picker as current date
		  
		  Calendar test = Calendar.getInstance();
	     return new DatePickerDialog(this, datePickerListener, 
	                         test.get(Calendar.YEAR), test.get(Calendar.MONTH),test.get(Calendar.DAY_OF_MONTH));
	  }
	  return null;
	 }
	 
	 private DatePickerDialog.OnDateSetListener datePickerListener 
	                = new DatePickerDialog.OnDateSetListener() {
	 

	
	public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		   year = arg1;
		   month = arg2 + 1;
		   day = arg3;
		   TextView dateText = (TextView)findViewById(R.id.DoB);
		   try
		   {
			   dateText.setText(String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year));
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	 }
	 };
	 
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_participant_main);
		
		if (ParticipantManager.getInstance().getselectedindex() >= 0)
		{
			fillParticipantData();
		}
		
		final Button add=(Button)findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boolean dataerror=false;
				BoParticipant buddy ;
				
				EditText name = (EditText) findViewById(R.id.Name);
				EditText email = (EditText) findViewById(R.id.email);
				EditText mobilenumber = (EditText) findViewById(R.id.phone);
				EditText address = (EditText) findViewById(R.id.address);
				EditText dob = (EditText) findViewById(R.id.DoB);
				
			
				
				if(name.getText().toString().equalsIgnoreCase(""))
				{	
					dataerror = true;
					Toast.makeText(getBaseContext(), "First name field is requried", Toast.LENGTH_SHORT).show();
				}
				
					
				
				if( mobilenumber.getText().toString().equalsIgnoreCase(""))
				{
					dataerror = true;
					Toast.makeText(getBaseContext(), "mobilenumber field is requried", Toast.LENGTH_SHORT).show();
				}
				
				
				
				if (ParticipantManager.getInstance().getselectedindex() >= 0)
				{
					buddy = ParticipantManager.getInstance().getParticipant(ParticipantManager.getInstance().getselectedindex());
				}
				else
				{
					buddy =new BoParticipant();
				}
				
			
				
				buddy.getPersonIdentifier().setFirstName(name.getText().toString());
				buddy.setMobileNumber(mobilenumber.getText().toString());
				buddy.setEmail(email.getText().toString());
				
				
				buddy.getAddress().setArea(address.getText().toString());
				//buddy.iDates = dob.getText().
				
				
				
				if(false==dataerror)
				{
				if (ParticipantManager.getInstance().getselectedindex() >= 0)
				{
					ParticipantManager.getInstance().modifyParticipant(buddy);
				}
				else
				{
					ParticipantManager.getInstance().addParticipant(buddy);
				}
				}
				String filePath = getBaseContext().getFilesDir().getPath().toString();
				try {
					ParticipantManager.getInstance().serialize(ParticipantManager.getInstance().getCurrentTripParticipantFile());
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent listBuddy= new Intent(ParticipantMainActivity.this,ParticipantList.class);
				startActivity(listBuddy);
			}

		
			
		});
		final Button cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				BoParticipant buddy = new BoParticipant();
				
				EditText name = (EditText) findViewById(R.id.Name);
				EditText email = (EditText) findViewById(R.id.email);
				EditText mobilenumber = (EditText) findViewById(R.id.phone);
				EditText address = (EditText) findViewById(R.id.address);
				EditText dob = (EditText) findViewById(R.id.DoB);
				
			
				

			name.setText(" ");
			email.setText(" ");
			mobilenumber.setText(" ");
			address.setText(" ");
			dob.setText(" ");
				
			Intent listBuddy= new Intent(ParticipantMainActivity.this,ParticipantList.class);
			startActivity(listBuddy);
			}
		});
		
//////////////////////
final Button chb=(Button)findViewById(R.id.change);
chb.setOnClickListener(new View.OnClickListener() {
		

public void onClick(View arg0) {
// TODO Auto-generated method stub
Dialog calenderDialog = onCreateDialog(DATE_DIALOG_ID);
calenderDialog.show();
int day = ((DatePickerDialog)calenderDialog).getDatePicker().getDayOfMonth();
int month = ((DatePickerDialog)calenderDialog).getDatePicker().getMonth();
int year = ((DatePickerDialog)calenderDialog).getDatePicker().getYear();
DateFormat dateFormat1 = SimpleDateFormat.getDateInstance();
//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
//TextView sd = (TextView) findViewById(R.id.SDvalueLabel);
//sd.setText(dateFormat1.format(iEditTrip.getStartDate().getTime()));

Toast.makeText(getBaseContext(), "SUCCESS", Toast.LENGTH_LONG).show();

}
});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.this, menu);
		return true;
	}

	private void fillParticipantData()
	{
		BoParticipant par = ParticipantManager.getInstance().getParticipant(ParticipantManager.getInstance().getselectedindex());
		
		EditText name = (EditText) findViewById(R.id.Name);
		
		name.setText(par.getPersonIdentifier().getFirstName());
		EditText email = (EditText)findViewById(R.id.email);
		email.setText(par.getEmail());
		EditText address = (EditText)findViewById(R.id.address);
		address.setText(par.getAddress().getArea());
		EditText phone = (EditText)findViewById(R.id.phone);
		phone.setText(par.getMobileNumber());
		/*EditText dob = (EditText)findViewById(R.id.DoB);
		dob.setText(par.);*/
		Button bt2=(Button)findViewById(R.id.add);
		bt2.setText("Save");
	}
	
}
