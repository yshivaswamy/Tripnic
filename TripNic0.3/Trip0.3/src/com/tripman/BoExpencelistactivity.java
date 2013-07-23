package com.tripman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import com.tripman.engine.BoExpense;
import com.tripman.engine.BoExpenseManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//import com.android.MainActivity;

public class BoExpencelistactivity extends Activity {
	List<String> names=new ArrayList<String>();
	
	
//ArrayAdapter iAdapter;
	BoExpenseAdapter iAdapter;
	
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
protected void onResume()
{
	super.onResume();
	BoExpenseManager.getInstance().setselectedindex(-1);
	 List<BoExpense> test_list=BoExpenseManager.getInstance().getAllExpence();
		Iterator<BoExpense> it=test_list.iterator();
		names.clear();
		while(it.hasNext())
		{
			BoExpense exp = (BoExpense)it.next();
			names.add(exp.getiDescription());
		}
		
		ListView li=(ListView) findViewById(R.id.MyList);
		iAdapter = new BoExpenseAdapter(this);
		li.setAdapter(iAdapter);
		View test = findViewById(R.id.empty_list);
		if(names.isEmpty())
		{
			test.setVisibility(View.VISIBLE);
		}
		else
		{
			test.setVisibility(View.INVISIBLE);	
		}
}
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater menuInflater = getMenuInflater();
	 
	  
	        menuInflater.inflate(R.menu.expense_button_menu, menu);

	        return super.onCreateOptionsMenu(menu);
	 }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View test, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, test, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.array.item_context_menu, menu);
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
/*	    switch (item.getItemId()) {
	        case android.R.id.itemid_0:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, MainActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }*/
		 Intent intent = new Intent(this, AddExpenseActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);
         return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.expenselistactivity);
	
//	BoExpenseManager.setsFilePath(getBaseContext().getFilesDir().getPath().toString());
	
	 List<BoExpense> test_list=BoExpenseManager.getInstance().getAllExpence();
	Iterator<BoExpense> it=test_list.iterator();
	//ListView li1=(ListView) findViewById(R.id.MyList);
	//li1.setAdapter(new ArrayAdapter(getBaseContext(),R.layout.listtext,names));
	
	while(it.hasNext())
	{
		BoExpense exp = (BoExpense)it.next();
		names.add(exp.getiDescription());
	}
	
	ListView li=(ListView) findViewById(R.id.MyList);
	//iAdapter = new ArrayAdapter(getBaseContext(),R.layout.expenselisttext,names);

	//li.setAdapter(iAdapter);
	iAdapter = new BoExpenseAdapter(this);
	li.setAdapter(iAdapter);
	
	li.getChildCount();
	View v = findViewById(R.id.empty_list);  
	if (names.isEmpty())
		v.setVisibility(View.VISIBLE);
	else 
		v.setVisibility(View.INVISIBLE);
	
	li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
///////////
		
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			BoExpenseManager.getInstance().setselectedindex(arg2);
			//TextView ClickedView=(TextView)arg1;
			Intent myintent=new Intent(BoExpencelistactivity.this, AddExpenseActivity.class);
			startActivity(myintent);
			//Toast.makeText(getBaseContext(),"ClickedItems ="+arg2+""+"value="+ClickedView.getText(),Toast.LENGTH_LONG).show();
			
		}
		});
		
	
	}
}
