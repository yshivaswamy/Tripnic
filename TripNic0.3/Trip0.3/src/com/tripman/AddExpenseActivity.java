package com.tripman;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import com.tripman.engine.BoExpense;
import com.tripman.engine.BoExpenseManager;




import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpenseActivity extends Activity {
	
	static final int DATE_DIALOG_ID = 999;
	Calendar iLocalCalender = Calendar.getInstance();
	 
	 @Override
	 protected Dialog onCreateDialog(int id) {
	  switch(id) {
	  case DATE_DIALOG_ID:
	     // set date picker as current date
		  Calendar test = Calendar.getInstance();
		  
		  if(BoExpenseManager.getInstance().getselectedindex() >=0)
			  test = BoExpenseManager.getInstance().getExpenseAt(BoExpenseManager.getInstance().getselectedindex()).getExpenseDateTime();
		  
	     return new DatePickerDialog(this, datePickerListener, 
	                         test.get(Calendar.YEAR), test.get(Calendar.MONTH),test.get(Calendar.DAY_OF_MONTH));
	  }
	  return null;
	 }
	 
	 private DatePickerDialog.OnDateSetListener datePickerListener 
	                = new DatePickerDialog.OnDateSetListener() {
	 

	
	public void onDateSet(DatePicker aPicker, int aYear, int aMonth, int aDay) {

		iLocalCalender.set(Calendar.YEAR, aPicker.getYear());
		iLocalCalender.set(Calendar.MONTH, aPicker.getMonth());
		iLocalCalender.set(Calendar.DAY_OF_MONTH, aPicker.getDayOfMonth());
		
		TextView tv = (TextView) findViewById(R.id.datetext);
		DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
		tv.setText(dateformat1.format(iLocalCalender.getTime()));
	 }
	 };
	 
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calendar cal= Calendar.getInstance();
		setContentView(R.layout.add_expense_activity);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy",Locale.US);
	    
		EditText sd = (EditText) findViewById(R.id.datetext);
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
		//tv.setText(String.valueOf(cal.DATE)+"/"+String.valueOf(cal.MONTH)+"/"+String.valueOf(cal.YEAR));
		
		
		if(BoExpenseManager.getInstance().getselectedindex() >=0)
		{
			fillActivityData();
			 setAllReadOnly();
		}
		
	
		final Button bt=(Button)findViewById(R.id.add);
		bt.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) {
				boolean dataerror=false;
				// TODO Auto-generated method stub
				BoExpense exp=new BoExpense();
				
				EditText name = (EditText) findViewById(R.id.name);
				EditText amount = (EditText) findViewById(R.id.exp_amt);
				EditText spentByPar = (EditText) findViewById(R.id.spent_by_parti);
				EditText placespent = (EditText) findViewById(R.id.place_spent);
		
				
				
				
				
				String name1 = name.getText().toString();
				String amount1 = amount.getText().toString();
			    String spentByPar1 = spentByPar.getText().toString();
				String placespent1 = placespent.getText().toString();
				if(name.getText().toString().equalsIgnoreCase(""))  
				{
				   dataerror=true;
				  Toast.makeText(getBaseContext(), "name field is requried",Toast.LENGTH_SHORT).show();
				}	
				if(amount.getText().toString().equalsIgnoreCase(""))
				{
				   dataerror=true;
				  Toast.makeText(getBaseContext(), "description field is empty",Toast.LENGTH_SHORT).show();
				}	
				if(spentByPar.getText().toString().equalsIgnoreCase(""))					   
				{
				   dataerror=true;
				   Toast.makeText(getBaseContext(), "address field is empty",Toast.LENGTH_SHORT).show();
				}
				
				if(placespent.getText().toString().equalsIgnoreCase(""))
					   
				{
				   dataerror=true;
				   Toast.makeText(getBaseContext(), "Phone number field is empty",Toast.LENGTH_SHORT).show();
				}	
				
			
				//BoExpense exp=new BoExpense();
				if(BoExpenseManager.getInstance().getselectedindex()>=0)
				{
					exp=BoExpenseManager.getInstance().getExpenseAt(BoExpenseManager.getInstance().getselectedindex());
					
				}
				else
				{
					exp=new BoExpense();
				}

				exp.setiDescription( name1);
				
				
				exp.setExpenseDateTime(iLocalCalender);
				
				Float f= Float.parseFloat(amount1);
				
				exp.setiAmount(f.floatValue()); //amount1.toString();
				exp.setiSpent_by_participant(spentByPar1);
				exp.setiPlace_spent(placespent1);
				

				CheckBox chkbox = (CheckBox) findViewById(R.id.exp_shared);
				if(chkbox.isChecked())		
				{
					exp.setiIsshared(true);
				}
				else
					exp.setiIsshared(false);
				
				Toast.makeText(getBaseContext(), name1+" "+amount1+""+spentByPar1 +""+ placespent1+"", Toast.LENGTH_LONG).show();
				if(false==dataerror)
				{
					if(BoExpenseManager.getInstance().getselectedindex()>=0)
				{
					BoExpenseManager.getInstance().modifyExpence(exp);
					
				}
				else
				{
					BoExpenseManager.getInstance().addExpence(exp);
				}
					
				
			
					//BoExpenseManager.getInstance().addExpence(bo);
					String filePath = getBaseContext().getFilesDir().getPath().toString();
					try {
						BoExpenseManager.getInstance().serialize(filePath+"BoTripExpense.xml");
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				
				Intent listexp = new Intent(AddExpenseActivity.this,TripDetailTabActivity.class);
				startActivity(listexp);
				}
			}
		});
		
		final Button rb=(Button)findViewById(R.id.clear);
		rb.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			
			EditText name = (EditText) findViewById(R.id.name);
			//CheckBox
			EditText amount = (EditText) findViewById(R.id.exp_amt);
			EditText spentByPar = (EditText) findViewById(R.id.spent_by_parti);
			EditText placespent = (EditText) findViewById(R.id.place_spent);
			EditText chk = (EditText) findViewById(R.id.exp_shared);
			
			name.setText("");
			amount.setText("");
			spentByPar.setText("");
			placespent.setText("");
			chk.setText("");
			Intent listexp = new Intent(AddExpenseActivity.this,TripDetailTabActivity.class);
			startActivity(listexp);	
		}
		});	
	}
	
	
	

	protected int length() {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expensemenu, menu);
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
		   
		    	
		    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
		 
					// set title
					alertDialogBuilder.setTitle("Your Title");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Click yes to DELETE")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int dia_id) {
								int id= BoExpenseManager.getInstance().getexpenseIdAt(BoExpenseManager.getInstance().getselectedindex());
						    	if(-1 !=id)
						    		BoExpenseManager.getInstance().deleteExpence(id);
								
						    	try {
									
									BoExpenseManager.getInstance().serialize(BoExpenseManager.getInstance().getCurrentTripExpenseFile());
								
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
	BoExpense exp=BoExpenseManager.getInstance().getExpenseAt(BoExpenseManager.getInstance().getselectedindex());
	
	EditText name = (EditText) findViewById(R.id.name);
	name.setText(exp.getiDescription());
	//CheckBox
	//EditText amount = (EditText) findViewById(R.id.exp_amt);
	//EditText amount1 = (EditText) findViewById(R.id.spent_by_parti);
	//amount1.f.floatValue().setText(exp.iAmount);
	EditText amount = (EditText) findViewById(R.id.exp_amt);
	amount.setText(String.valueOf(exp.getiAmount()));

	EditText spentByPar = (EditText) findViewById(R.id.spent_by_parti);
	spentByPar.setText(exp.getiSpent_by_participant());
	EditText  placespent = (EditText) findViewById(R.id.place_spent);
	placespent.setText(exp.getiPlace_spent());
	Button bt=(Button)findViewById(R.id.add);
	bt.setText("SAVE");
	
	TextView tv = (TextView) findViewById(R.id.datetext);
	DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
	tv.setText(dateformat1.format(exp.getExpenseDateTime().getTime()));
	
	CheckBox chkbox = (CheckBox) findViewById(R.id.exp_shared);
	chkbox.setChecked(exp.getiIsshared());
	

	}
	

private void setAllReadOnly()
{
	EditText expname=(EditText)findViewById(R.id.name);
	expname.setEnabled(false);
	
	//EditText expdate=(EditText)findViewById(R.id.datetext);
	//expdate.setEnabled(false);
	
	EditText expamount=(EditText)findViewById(R.id.exp_amt);
	expamount.setEnabled(false);
	
	EditText expspent_by_part=(EditText)findViewById(R.id.spent_by_parti);
	expspent_by_part.setEnabled(false);
	
	EditText expplace_spent=(EditText)findViewById(R.id.place_spent);
	expplace_spent.setEnabled(false);
	
	Button btchk = (Button)findViewById(R.id.exp_shared);
	btchk.setVisibility(Button.VISIBLE);
	
	Button bt = (Button)findViewById(R.id.add);
	bt.setVisibility(Button.INVISIBLE);
	
	Button button = (Button) findViewById(R.id.clear);
	button.setVisibility(Button.INVISIBLE);
	
	TextView tv = (TextView) findViewById(R.id.datetext);
	tv.setEnabled(false);
	
	CheckBox chkbox=(CheckBox)findViewById(R.id.exp_shared);
	chkbox.setEnabled(false);
	
	
	
}
	
private void makeWritable()
{
	EditText expname=(EditText)findViewById(R.id.name);
	expname.setEnabled(true);
	
	EditText expamount=(EditText)findViewById(R.id.exp_amt);
	expamount.setEnabled(true);
	
	EditText expspent_by_part=(EditText)findViewById(R.id.spent_by_parti);
	expspent_by_part.setEnabled(true);
	
	EditText expplace_spent=(EditText)findViewById(R.id.place_spent);
	expplace_spent.setEnabled(true);
	
	Button btchk = (Button)findViewById(R.id.exp_shared);
	btchk.setVisibility(Button.VISIBLE);
	
	Button bt = (Button)findViewById(R.id.add);
	bt.setVisibility(Button.VISIBLE);
	
	Button button = (Button) findViewById(R.id.clear);
	button.setVisibility(Button.INVISIBLE);
	
    TextView tv = (TextView) findViewById(R.id.datetext);
	tv.setEnabled(true);
	
	CheckBox chkbox=(CheckBox)findViewById(R.id.exp_shared);
	chkbox.setEnabled(true);
	
	
	
	
}
	

}
