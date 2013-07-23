package com.tripman;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.transform.TransformerException;

import com.tripman.R;
import com.tripman.engine.BoExpenseManager;
import com.tripman.engine.BoRequisite;
import com.tripman.engine.BoRequisiteManger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

public class AddRequisiteActivity extends Activity {
	
	static final int DATE_DIALOG_ID = 999;
	Calendar iLocalCalender = Calendar.getInstance();
	  
	  @Override
	  protected Dialog onCreateDialog(int id) {
	   switch (id) {
	   case DATE_DIALOG_ID:
	      // set date picker as current date
		   Calendar test = Calendar.getInstance();
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
	     
	     

     protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calendar cal= Calendar.getInstance();
		setContentView(R.layout.add_requisite_activity);
		
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy",Locale.US);
		EditText sd = (EditText) findViewById(R.id.date);

		if (BoRequisiteManger.getInstance().getslectedindex() >= 0)
		  {
		   fillActivityData();
			 setAllReadOnly();
		  }
		
		else
		{
		 DateFormat dateFormat1 = SimpleDateFormat.getDateInstance();
		 sd.setText(dateFormat1.format(Calendar.getInstance().getTime()));
		}
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
		addListenerOnButton1();
		addListenerOnButton2();
        
	}
     
            public void addListenerOnButton1() {
			 
			Button button = (Button) findViewById(R.id.add);
	 
			button.setOnClickListener(new OnClickListener() {
	 
		
			public void onClick(View arg0) {
	 
					Boolean dataerror=false;
					BoRequisite req;
					 if (BoRequisiteManger.getInstance().getslectedindex() >= 0)
					  {
						req= BoRequisiteManger.getInstance().getRequisiteAt(BoRequisiteManger.getInstance().getslectedindex());
					  }
					 else
					 {
						 req=new BoRequisite();
					 }
					req.setRequisiteDateTime(iLocalCalender);
					EditText Itemnametext = (EditText) findViewById(R.id.itemname);
					EditText Itemtypetext = (EditText) findViewById(R.id.itemtype);
					
					 CheckBox checkBox = (CheckBox) findViewById(R.id.Myes);
			         if (checkBox.isChecked()) {
			             req.setiIsmanditory(true);
			         }

					 CheckBox checkBox1 = (CheckBox) findViewById(R.id.Ryes);
			         if (checkBox1.isChecked()) {
			             req.setiPendingRequisite(true);
			         }
			           
			         
			           
			         
					
					String Itemname = Itemnametext.getText().toString();
					String Itemtype = Itemtypetext.getText().toString();
					
					 if(Itemnametext.getText().toString().equalsIgnoreCase(""))
					        
					    {
					    dataerror=true;
					      Toast.makeText(getBaseContext(), "name field is requried", 
					                   Toast.LENGTH_SHORT).show();
					    }
					 if(Itemtypetext.getText().toString().equalsIgnoreCase(""))
					        
					    {
					    dataerror=true;
					      Toast.makeText(getBaseContext(), "type field is requried",Toast.LENGTH_SHORT).show();
					    }
					 
					 
					 
					 req.setiItemname(Itemname);
					 req.setiItemtype(Itemtype);
			    	  if(false==dataerror)
		                {
		                	if (BoRequisiteManger.getInstance().getslectedindex() >= 0)
						    {
						     BoRequisiteManger.getInstance().modifyRequisite(req);
						    }
						    else
						    {
						     BoRequisiteManger.getInstance().addRequisite(req);
						    }
		                	
		             req.setiItemname(Itemname);
					 req.setiItemtype(Itemtype);
	               
	                
	            
					 
	             String filePath = getBaseContext().getFilesDir().getPath().toString();
	             try {
	            	 	BoRequisiteManger.getInstance().serialize(filePath+"Requisite.xml");
	               
					}
	             catch (TransformerException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
					}
	             
	                Intent listRequisite=new Intent(AddRequisiteActivity.this,TripDetailTabActivity.class);
	                startActivity(listRequisite);
	                finish();
	                }
	                }
			});
		
}	
		
		public void addListenerOnButton2() {
			 
			Button button = (Button) findViewById(R.id.cancel);
	 
			button.setOnClickListener(new OnClickListener() {
	 
				
				public void onClick(View arg0) {					
			
					EditText Itemnametext = (EditText) findViewById(R.id.itemname);
					EditText Itemtypetext = (EditText) findViewById(R.id.itemtype);
					
				    Itemnametext.setText("");
					Itemtypetext.setText("");
					
					Intent listRequisite=new Intent(AddRequisiteActivity.this,TripDetailTabActivity.class);
	                startActivity(listRequisite);
					
			}
			});
		}
		
		protected int length() {
			// TODO Auto-generated method stub
			return 0;
		}

		
		@Override
		 public boolean onCreateOptionsMenu(Menu menu) {
		  /*MenuInflater menuInflater = getMenuInflater();
		        menuInflater.inflate(R.menu.detailview_menu, menu);

		        return super.onCreateOptionsMenu(menu);
		 }
		
		*/
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
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
		 
					// set title
					alertDialogBuilder.setTitle("Confirm DELETE");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Are you sure you want to DELETE")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int dia_id) {
								
								int id = BoRequisiteManger.getInstance().getRequsiteIdAt(BoRequisiteManger.getInstance().getslectedindex());
							    if (-1 != id)
							    	BoRequisiteManger.getInstance().removeRequisite(id);
							 
							  try {
								  BoRequisiteManger.getInstance().serialize(BoRequisiteManger.getInstance().getCurrentRequisiteFile());
								 } catch (TransformerException e) {
							   // TODO Auto-generated catch block
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
		  BoRequisite req = BoRequisiteManger.getInstance().getRequisiteAt(BoRequisiteManger.getInstance().getslectedindex());
		  
		  EditText itemname = (EditText) findViewById(R.id.itemname);
		  itemname.setText(req.getiItemname());
		  EditText itemtype = (EditText)findViewById(R.id.itemtype);
		  itemtype.setText(req.getiItemtype());
		  
		  CheckBox mandatoryCB = (CheckBox) findViewById(R.id.Myes);
		  mandatoryCB.setChecked(req.getiIsmanditory());

		  CheckBox pendingCB = (CheckBox) findViewById(R.id.Ryes);
		  pendingCB.setChecked(req.getiPendingRequisite()); 
		  
		  Button savebutton = (Button) findViewById(R.id.add);
		  savebutton.setText("Save");
		  
		  TextView tv = (TextView) findViewById(R.id.date);
		  DateFormat dateformat1 = SimpleDateFormat.getDateInstance();
		  tv.setText(dateformat1.format(req.getRequisiteDateTime().getTime()));
		  
		  
		  
		 
		 }
		  
		  private void setAllReadOnly()
			{
				EditText ItemName=(EditText)findViewById(R.id.itemname);
				ItemName.setEnabled(false);
				
		    	EditText Itemtype=(EditText)findViewById(R.id.itemtype);
		    	Itemtype.setEnabled(false);
		    	
		    	CheckBox mandatoryCB=(CheckBox)findViewById(R.id.Myes);
		    	mandatoryCB.setEnabled(false);
				 
			    CheckBox pendingCB=(CheckBox)findViewById(R.id.Ryes);
			    pendingCB.setEnabled(false);
		    	
		    	Button addbutton = (Button)findViewById(R.id.add);
				addbutton.setVisibility(Button.INVISIBLE);
				
				Button cancelbutton = (Button) findViewById(R.id.cancel);
				cancelbutton.setVisibility(Button.INVISIBLE);
				
				TextView tv = (TextView) findViewById(R.id.date);
				tv.setEnabled(false);
				
				
			}
		    private void makeWritable()
		    {
		    	EditText Itemname=(EditText)findViewById(R.id.itemname);
		    	Itemname.setEnabled(true);
				
		    	EditText Itemtype=(EditText)findViewById(R.id.itemtype);
		    	Itemtype.setEnabled(true);
		    	
		    	CheckBox mandatoryCB=(CheckBox)findViewById(R.id.Myes);
		    	mandatoryCB.setEnabled(true);
				 
			    CheckBox pendingCB=(CheckBox)findViewById(R.id.Ryes);
			    pendingCB.setEnabled(true);
		    	
		    	Button addbutton  = (Button)findViewById(R.id.add);
		    	addbutton.setVisibility(Button.VISIBLE);
				
				Button cancelbutton = (Button) findViewById(R.id.cancel);
				cancelbutton.setVisibility(Button.INVISIBLE); 	
				
				TextView tv = (TextView) findViewById(R.id.date);
				tv.setEnabled(true);
				
		    }
	}