package com.tripman;
import java.util.List;

import com.tripman.engine.BoTrip;
import com.tripman.engine.BoTripManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BoTripmanAdapter extends BaseAdapter {
    
    private Activity iActAvtivity;
    private static LayoutInflater inflater=null;
    List<BoTrip> iData = null;
//    public ImageLoader imageLoader; 
    
    public BoTripmanAdapter( Activity a) {
    	iActAvtivity = a;
        inflater = (LayoutInflater)iActAvtivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iData = BoTripManager.getInstance().getAllTrip();
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
            vi = inflater.inflate(R.layout.tripman_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

       
        BoTrip bo = BoTripManager.getInstance().getTripAt(position);
        title.setText(bo.getTripName());
        
        return vi;
    }

	private View findViewById(int emptyList) {
		// TODO Auto-generated method stub
		return null;
	}
	
}