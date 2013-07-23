package com.tripman;

import java.util.List;

import com.tripman.R;
import com.tripman.engine.BoRequisite;
import com.tripman.engine.BoRequisiteManger;
import com.tripman.engine.Bosearchrequisitetype;

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

public class BoRequisiteAdapter extends BaseAdapter {
    
    private Activity iActAvtivity;
    private static LayoutInflater inflater=null;
    List<BoRequisite> iData = null;
//    public ImageLoader imageLoader; 
    
    public BoRequisiteAdapter(Activity a) {
    	iActAvtivity = a;
        inflater = (LayoutInflater)iActAvtivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iData = BoRequisiteManger.getInstance().getAllRequisite(Bosearchrequisitetype.Alltype);
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
            vi = inflater.inflate(R.layout.requisite_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);// thumb image
        ImageView img = (ImageView)vi.findViewById(R.id.status_image);
        
        BoRequisite bo = BoRequisiteManger.getInstance().getRequisiteAt(position);
        title.setText(bo.getiItemname());
        
      
     if(  bo.getiIsmanditory()==true && bo.getiPendingRequisite() == false)
      {
    	  img.setImageResource(R.drawable.green);
      }
    	 
       else if(bo.getiIsmanditory()==true && bo.getiPendingRequisite()==true )
      {
    	  img.setImageResource(R.drawable.red);
      }
      else
      {
    	 img.setImageResource(R.drawable.yellow) ;
      }
     
     
        return vi;
    }

	private View findViewById(int emptyList) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
