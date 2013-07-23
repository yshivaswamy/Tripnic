package com.tripman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tripman.engine.BoActivity;
import com.tripman.engine.BoActivityManager;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BoActivityList extends Activity 
{
	List<String>MyTeamNames=new ArrayList<String>();
	BoImageTextImageAdapter iAdapter;
	
	@Override
	protected void onResume()
	{
		super.onResume();
		BoActivityManager.getInstance().setselectedindex(-1);
		
		List<BoActivity>test_list=BoActivityManager.getInstance().getAllActivity();
	    Iterator<BoActivity> ac=test_list.iterator();
	    MyTeamNames.clear(); 
	    while(ac.hasNext())
	    {
	    	BoActivity act=(BoActivity)ac.next();
	    	MyTeamNames.add(act.getName());
	    }
	    
	    ListView li=(ListView) findViewById(R.id.MyList);
	    
	    iAdapter = new BoImageTextImageAdapter(this, AdapterType.ETypeActivity);
		li.setAdapter(iAdapter);
		
	    View v = findViewById(R.id.empty_list);  
		if (MyTeamNames.isEmpty())
			v.setVisibility(View.VISIBLE);
		else 
			v.setVisibility(View.INVISIBLE);
	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) 
	{
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.list_view_menu, menu);
            return super.onCreateOptionsMenu(menu);
	 }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.array.item_context_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		 Intent intent = new Intent(this, BoActivity_add_modify.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);
         return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitylist);
		List<BoActivity>test_list=BoActivityManager.getInstance().getAllActivity();
	    Iterator<BoActivity> ac=test_list.iterator();
	    while(ac.hasNext())
	    {
	    	BoActivity act=(BoActivity)ac.next();
	    	MyTeamNames.add(act.getName());
	    }
		
		ListView li=(ListView) findViewById(R.id.MyList);
		li.setAdapter(iAdapter);

		iAdapter = new BoImageTextImageAdapter(this, AdapterType.ETypeActivity);
		li.setAdapter(iAdapter);
		
		
		
		
		View v = findViewById(R.id.empty_list);  
		if (MyTeamNames.isEmpty())
			v.setVisibility(View.VISIBLE);
		else 
			v.setVisibility(View.INVISIBLE);
		
		li.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				BoActivityManager.getInstance().setselectedindex(arg2);
				Intent myintent=new Intent(BoActivityList.this,BoActivity_add_modify.class);
						startActivity(myintent);
				
			}
		});	
   }
}
