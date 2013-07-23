package com.tripman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tripman.engine.BoExpenseManager;
import com.tripman.engine.BoRequisiteManger;
import com.tripman.engine.BoTrip;
import com.tripman.engine.BoTripManager;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class TripManListActivity extends Activity {
	
	BoTripManager iTripMan;
	List<String>iTripNameList=new ArrayList <String>();
	BoTripmanAdapter iAdapter;

	@Override
	public void onResume()
	{
		super.onResume();
		
		BoTripManager.getInstance().setslectedindex(-1);
	    BoExpenseManager.destroyInstance();
	    BoRequisiteManger.destoryInstance();
	    
		 List<BoTrip> textlist = BoTripManager.getInstance().getAllTrip();
			Iterator<BoTrip> it= textlist.iterator();
			iTripNameList.clear();
			while(it.hasNext())
			{
				BoTrip rq =(BoTrip)it.next();
				iTripNameList.add(rq.getTripName());
			}
			ListView li=(ListView) findViewById(R.id.TripList);
			//iAdapter = new BoTripmanAdapter(getBaseContext(),R.layout.list_item,iTripNameList);
			iAdapter = new BoTripmanAdapter(this);
			li.setAdapter(iAdapter);
	}

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_list);
        
        
        
        if (null == iTripMan)
        {
	        String rootDir = getBaseContext().getFilesDir().getPath().toString();
	        iTripMan = BoTripManager.getInstance(rootDir+"//"+"BoTrip");
        }
        
        BoTripManager.getInstance().setslectedindex(-1);
        BoExpenseManager.destroyInstance();
        
        List<BoTrip> textlist = BoTripManager.getInstance().getAllTrip();
		Iterator<BoTrip> it= textlist.iterator();
		while(it.hasNext())
		{
			BoTrip rq =(BoTrip)it.next();
			iTripNameList.add(rq.getTripName());
		}
		ListView li=(ListView) findViewById(R.id.TripList);
		//iAdapter = new BoTripmanAdapter(getBaseContext(),R.layout.list_item,iTripNameList);
		iAdapter = new BoTripmanAdapter(this);
		li.setAdapter(iAdapter);
		
		View test = findViewById(R.id.empty_list);
		if(iTripNameList.isEmpty())
		{
			test.setVisibility(View.VISIBLE);
		}
		else
		{
			test.setVisibility(View.INVISIBLE);
		}
		li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BoTripManager.getInstance().setslectedindex(arg2);
			
			BoTripManager.getInstance().setEditingTrip(BoTripManager.getInstance().getTripAt(arg2));
			Intent req= new Intent(TripManListActivity.this, TripDetailTabActivity.class);	
			startActivity(req);
			
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.action_add:
	    	{
	    		//Toast.makeText(getBaseContext(), "ADD", Toast.LENGTH_LONG).show();
	    		BoTripManager.getInstance().setEditingTrip(BoTripManager.getInstance().CreateNewTrip());
	    		Intent myintent=new Intent(TripManListActivity.this,AddEditTripActivity.class);
				startActivity(myintent);
				
	    		return true;
	    	}
    	
	    default:
	    		return false;
	    		
	    		
    	}
    	
    }
}