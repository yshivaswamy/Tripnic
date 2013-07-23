package com.tripman;

import javax.xml.transform.TransformerException;

import com.tripman.R;
import com.tripman.engine.BoTrip;
import com.tripman.engine.BoTripManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class BoTripDetailActivity extends Activity {
	int selectedTripId=0;

	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	  BoTripManager.getInstance().deleteTrip(selectedTripId);
	  
/*	  String aTrip = getBaseContext().getFilesDir().getPath().toString();
	  try {
		  BoTripManager.getInstance().serializeTrip(aTrip+"BoTrip.xml");
	  } catch (TransformerException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	*/  
	   Intent intent = new Intent(this, BoTripDetailActivity.class);
	         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	         startActivity(intent);
	         return true;
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);
		BoTrip trip=BoTripManager.getInstance().getTrip(BoTripManager.getInstance().getslectedindex());
	    String todisplay=trip.getTripName()+"\n";
	    TextView t=(TextView)findViewById(R.id.tripname);
        t.setText(todisplay);
	    selectedTripId=trip.getId();
	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.menu, menu);

	        return super.onCreateOptionsMenu(menu);
	 }	
}


