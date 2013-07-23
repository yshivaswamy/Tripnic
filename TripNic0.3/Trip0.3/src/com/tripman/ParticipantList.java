package com.tripman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.tripman.engine.BoParticipant;
import com.tripman.engine.ParticipantManager;





import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ParticipantList extends Activity {
	List<String> names=new ArrayList<String>();
	
	BoParticipantAdapter iAdapter;
	

	
	protected void onResume()
	{
		super.onResume();
		ParticipantManager.getInstance().setselectedindex(-1);
		
		List<BoParticipant>test_list=ParticipantManager.getInstance().getAllParticipant();
	    Iterator<BoParticipant> ac=test_list.iterator();
	    names.clear();
	    while(ac.hasNext())
	    {
	    	BoParticipant act=(BoParticipant)ac.next();
	    	names.add(act.getPersonIdentifier().getFirstName());
	    }
	    
	    ListView li=(ListView) findViewById(R.id.MyList);
		//iAdapter = new ArrayAdapter(getBaseContext(),R.layout.listtext,MyTeamNames);
	    //iAdapter.
	    
	    iAdapter = new BoParticipantAdapter(this);
		li.setAdapter(iAdapter);
		
	View v = findViewById(R.id.empty_list);  
		if (names.isEmpty())
			v.setVisibility(View.VISIBLE);
		else 
			v.setVisibility(View.INVISIBLE);
	}
	 
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.participantlistmenu, menu);

	        return super.onCreateOptionsMenu(menu);
	 }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 Intent intent = new Intent(this, ParticipantMainActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);
         return true;
	}
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.listparticipant);
	
	//ParticipantManager.SFilePath = getBaseContext().getFilesDir().getPath().toString();
	
	 List<BoParticipant> test_list=ParticipantManager.getInstance().getAllParticipant();
	Iterator<BoParticipant> it=test_list.iterator();
	//ListView li1=(ListView) findViewById(R.id.MyList);
	//li1.setAdapter(new ArrayAdapter(getBaseContext(),R.layout.listtext,names));
	
	while(it.hasNext())
	{
		BoParticipant buddy = (BoParticipant)it.next();
		names.add(buddy.getPersonIdentifier().getFirstName());
	}
	
	ListView l=(ListView) findViewById(R.id.MyList);
	//iAdapter = new ArrayAdapter(getBaseContext(),R.layout.listparticipant,names);
	iAdapter = new 	BoParticipantAdapter(this);
	l.setAdapter(iAdapter);
	
	
	View v = findViewById(R.id.empty_list);  
	if (names.isEmpty())
		v.setVisibility(View.VISIBLE);
	else 
		v.setVisibility(View.INVISIBLE);
	ListView li=(ListView) findViewById(R.id.MyList);
	
	li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
///////////
		
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ParticipantManager.getInstance().setselectedindex(arg2);
		//	TextView ClickedView=(TextView)arg1;
			Intent myintent=new Intent(ParticipantList.this, ParticipantDetail.class);
			startActivity(myintent);
			//Toast.makeText(getBaseContext(),"ClickedItems ="+arg2+""+"value="+ClickedView.getText(),Toast.LENGTH_LONG).show();
			
		}
		});
		
	
	}
}
