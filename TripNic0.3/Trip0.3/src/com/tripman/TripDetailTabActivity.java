package com.tripman;
import com.tripman.engine.BoTripManager;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class TripDetailTabActivity extends TabActivity  {
	
	BoTripManager iTripMan;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tab);
        
        Resources ressources = getResources(); 
		TabHost tabHost = getTabHost();
		
		String rootDir = getBaseContext().getFilesDir().getPath().toString();
		iTripMan =BoTripManager.getInstance(rootDir+"//"+"BoTrip");
		
/*		if(0 == iTripMan.getTripCount())
		{
			Intent intentSummary = new Intent().setClass(this, TripManListActivity.class);
			TabSpec tabSpecSummary = tabHost
			  .newTabSpec("Android")
			  .setIndicator("", ressources.getDrawable(R.drawable.summary_icon))
			  .setContent(intentSummary);
			
			tabHost.addTab(tabSpecSummary);
		}
		*/
//		else
//		{
		Intent intentSummary = new Intent().setClass(this, AddEditTripActivity.class);
		TabSpec tabSpecSummary = tabHost
		  .newTabSpec("Android")
		  .setIndicator("", ressources.getDrawable(R.drawable.summary_icon))
		  .setContent(intentSummary);
		
		// Participant tab
		Intent intentParticipant = new Intent().setClass(this, ParticipantList.class);
		TabSpec tabSpecParticipant = tabHost
		  .newTabSpec("Android")
		  .setIndicator("", ressources.getDrawable(R.drawable.participants_icon))
		  .setContent(intentParticipant);
		
		
		
		Intent intentVisit = new Intent().setClass(this, BoVisitList.class);
		TabSpec tabSpecVisit = tabHost
		  .newTabSpec("Android")
		  .setIndicator("", ressources.getDrawable(R.drawable.pv))
		  .setContent(intentVisit);
		
		// Requisite tab
			Intent intentRequisite = new Intent().setClass(this, BoRequisiteListActivity.class);
				TabSpec tabSpecRequisite = tabHost
				  .newTabSpec("Android")
				  .setIndicator("", ressources.getDrawable(R.drawable.requisite_icon))
				  .setContent(intentRequisite);
				
		//Activity tab
		Intent intentActivity = new Intent().setClass(this, BoActivityList.class);
		TabSpec tabSpecExpense = tabHost
		  .newTabSpec("Android")
		  .setIndicator("", ressources.getDrawable(R.drawable.activity_icon))
		  .setContent(intentActivity);
				
		// Expense tab
		Intent intentExpense = new Intent().setClass(this, BoExpencelistactivity.class);
		TabSpec tabSpecActivity = tabHost
		  .newTabSpec("Android")
		  .setIndicator("", ressources.getDrawable(R.drawable.expense_icon))
		  .setContent(intentExpense);
		
		// add all tabs 
		tabHost.addTab(tabSpecSummary);
		tabHost.addTab(tabSpecParticipant);
		tabHost.addTab(tabSpecVisit);
		tabHost.addTab(tabSpecRequisite);
		tabHost.addTab(tabSpecExpense);
		tabHost.addTab(tabSpecActivity);
		
		//tabHost.setCurrentTab(2);
		}
 //   }
}