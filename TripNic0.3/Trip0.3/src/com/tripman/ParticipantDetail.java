package com.tripman;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;


import com.tripman.engine.BoParticipant;
import com.tripman.engine.ParticipantManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;




public class ParticipantDetail extends Activity{
	
	int selectedParticipantId = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.participantdetail);
		BoParticipant par = ParticipantManager.getInstance().getParticipant(ParticipantManager.getInstance().getselectedindex());
		

		TextView parName =(TextView) findViewById(R.id.participantname);
		parName.setText(parName.getText().toString()+" "+par.getPersonIdentifier().getFirstName());
		//parName.setText(par.iName.iFirstName);
		TextView email = (TextView)findViewById(R.id.emailId);
		email.setText(email.getText().toString()+" "+par.getEmail());
		TextView mobilenumber = (TextView)findViewById(R.id.mobilenumber);
		mobilenumber.setText(mobilenumber.getText().toString()+" "+par.getMobileNumber());
		
		TextView address = (TextView)findViewById(R.id.address);
		address.setText(address.getText().toString()+ " "+par.getAddress().getArea());
		
		TextView dob = (TextView)findViewById(R.id.DoB);
		
      
        
     //   DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyy");
	//	dob.setText(dob.getText().toString()+" "+par.getDoB());
//dateFormat.parse(par.getDoB().toString()));
		
		  selectedParticipantId = par.getId().intValue();
	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.participantdetail, menu);

	        return super.onCreateOptionsMenu(menu);
	 }
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId())
	    {
		//if(R.id.action_edit == item.getItemId())
	    case R.id.action_edit:
		{				
			Intent in = new Intent(this,ParticipantMainActivity.class);
	        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(in);
			
		}
		break;	
	    case R.id.action_delete:
	    {
		ParticipantManager.getInstance().deleteParticipant(selectedParticipantId);
		
		String filePath = getBaseContext().getFilesDir().getPath().toString();
		try {
			ParticipantManager.getInstance().serialize(filePath+"BoParticipants.xml");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent intent = new Intent(this, ParticipantList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
	    //	Toast.makeText(getBaseContext(), "DELETE selected", Toast.LENGTH_SHORT).show();
	    }
	    break;
	    	

	}
	    return true;
	}

}
