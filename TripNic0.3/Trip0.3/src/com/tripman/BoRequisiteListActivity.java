package com.tripman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.tripman.R;
import com.tripman.engine.BoRequisite;
import com.tripman.engine.BoRequisiteManger;
import com.tripman.engine.Bosearchrequisitetype;


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

public class BoRequisiteListActivity extends Activity {
	List<String>MyTeamNames=new ArrayList <String>();
	//ArrayAdapter iAdapter;
	BoRequisiteAdapter iAdapter;
	
	
	/*@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		finish();
	}*/
	@Override
	public void onResume()
	{
		super.onResume();
		BoRequisiteManger.getInstance().setslectedindex(-1);
		 List<BoRequisite> textlist = BoRequisiteManger.getInstance().getAllRequisite(Bosearchrequisitetype.Alltype);
			Iterator<BoRequisite> it= textlist.iterator();
			MyTeamNames.clear();
			while(it.hasNext())
			{
				BoRequisite rq =(BoRequisite)it.next();
				MyTeamNames.add(rq.getiItemname());
			}
			ListView li=(ListView) findViewById(R.id.MyList);
			//iAdapter = new ArrayAdapter(getBaseContext(),R.layout.requisite_list_item,MyTeamNames);
			iAdapter=new BoRequisiteAdapter(this);
			li.setAdapter(iAdapter);
	}
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.requisitemenu, menu);

	        return super.onCreateOptionsMenu(menu);
	 }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, AddRequisiteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(intent);
       return true;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.requisite_list_activity);
		
	
		List<BoRequisite> textlist = BoRequisiteManger.getInstance().getAllRequisite(Bosearchrequisitetype.Alltype);
		Iterator<BoRequisite> it= textlist.iterator();
		while(it.hasNext())
		{
			BoRequisite rq =(BoRequisite)it.next();
			MyTeamNames.add(rq.getiItemname());
		}
		ListView li=(ListView) findViewById(R.id.MyList);
		//iAdapter = new ArrayAdapter(getBaseContext(),R.layout.requisite_list_item,MyTeamNames);
		iAdapter=new BoRequisiteAdapter(this);
		li.setAdapter(iAdapter);
		
		View test = findViewById(R.id.empty_list);
		if(MyTeamNames.isEmpty())
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
				BoRequisiteManger.getInstance().setslectedindex(arg2);
			Intent req= new Intent(BoRequisiteListActivity.this, AddRequisiteActivity.class);	
			startActivity(req);
			
			}
		});
		
		
		
}
}
	
