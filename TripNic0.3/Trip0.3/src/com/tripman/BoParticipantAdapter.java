package com.tripman;

import java.util.List;
import com.tripman.engine.BoParticipant;
import com.tripman.engine.ParticipantManager;


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



public class BoParticipantAdapter extends BaseAdapter {
	 private Activity iActAvtivity;
	    private static LayoutInflater inflater=null;
	    List<BoParticipant> iData = null;
//	    public ImageLoader imageLoader; 
	    
	    public BoParticipantAdapter(Activity a) {
	    	iActAvtivity = a;
	        inflater = (LayoutInflater)iActAvtivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        iData = ParticipantManager.getInstance().getAllParticipant();
//	        imageLoader=new ImageLoader(activity.getApplicationContext());
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
	            vi = inflater.inflate(R.layout.participantlistrow, null);

	        TextView title = (TextView)vi.findViewById(R.id.title); // title
	        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

	       
	        BoParticipant bo = ParticipantManager.getInstance().getParticipant(position);
	        title.setText(bo.getPersonIdentifier().getFirstName());
	        
	        return vi;
	    }

		private View findViewById(int emptyList) {
			// TODO Auto-generated method stub
			return null;
		}
		

}
