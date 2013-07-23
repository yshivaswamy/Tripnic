package com.tripman;
import java.util.List;

import com.tripman.engine.BoExpense;
import com.tripman.engine.BoExpenseManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BoExpenseAdapter extends BaseAdapter {
    
    private Activity iActAvtivity;
    private static LayoutInflater inflater=null;
    List<BoExpense> iData = null;
//    public ImageLoader imageLoader; 
    
    public BoExpenseAdapter( Activity a) {
    	iActAvtivity = a;
        inflater = (LayoutInflater)iActAvtivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iData = BoExpenseManager.getInstance().getAllExpence();
//        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

   
	public int getCount() {
        return iData.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.expense_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); 
        TextView amount = (TextView)vi.findViewById(R.id.exptext); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

       
        BoExpense bo = BoExpenseManager.getInstance().getExpenseAt(position);
        title.setText(bo.getiDescription());
        amount.setText(String.valueOf(bo.getiAmount()));

        
        return vi;
    }	
}