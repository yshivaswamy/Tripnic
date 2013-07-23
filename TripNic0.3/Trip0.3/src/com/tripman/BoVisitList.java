package com.tripman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.tripman.engine.BoVisit;
import com.tripman.engine.BoVisitManager;

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
import android.widget.Toast;

public class BoVisitList extends Activity {
	List<String> MyTeamNames = new ArrayList<String>();
	BoImageTextImageAdapter iAdapter;
	@Override
	protected void onResume() {
		super.onResume();
		BoVisitManager.getInstance().setSelecetedindex(-1);
		List<BoVisit> testlist = BoVisitManager.getInstance().getAllVisit();

		Iterator<BoVisit> it = testlist.iterator();
		MyTeamNames.clear();
		while (it.hasNext()) {
			BoVisit visit = (BoVisit) it.next();
			MyTeamNames.add(visit.getName());
		}
		ListView view = (ListView) findViewById(R.id.MyList);
		iAdapter=new BoImageTextImageAdapter(this, AdapterType.ETypePlace);
		view.setAdapter(iAdapter);
		View v = findViewById(R.id.empty_list);
		if (MyTeamNames.isEmpty())
			v.setVisibility(View.VISIBLE);
		else
			v.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.list_view_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.array.item_context_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, BoVisitAddEditActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitylist);
		//BoVisitManager.sFilePath = getBaseContext().getFilesDir().getPath().toString();
		List<BoVisit> testlist = BoVisitManager.getInstance().getAllVisit();
		//BoVisitManager.getInstance().setSelecetedindex(-1);

		Iterator<BoVisit> it = testlist.iterator();
		//MyTeamNames.clear();
		while (it.hasNext()) {
			BoVisit visit = (BoVisit) it.next();
			MyTeamNames.add(visit.getName());
		}
		ListView view = (ListView) findViewById(R.id.MyList);
		iAdapter=new BoImageTextImageAdapter(this,AdapterType.ETypePlace);
		//iAdapter = new ArrayAdapter(getBaseContext(), R.layout.listtext,MyTeamNames);
		view.setAdapter(iAdapter);
		View v = findViewById(R.id.empty_list);
		if (MyTeamNames.isEmpty())
			v.setVisibility(View.VISIBLE);
		else
			v.setVisibility(View.INVISIBLE);

		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				BoVisitManager.getInstance().setSelecetedindex(arg2);
				Intent myintent = new Intent(BoVisitList.this,BoVisitAddEditActivity.class);
				startActivity(myintent);
				
				//Toast.makeText(getBaseContext(), "Populate and start Detail view", Toast.LENGTH_LONG).show();
				
			}
			
		});
	}
}
