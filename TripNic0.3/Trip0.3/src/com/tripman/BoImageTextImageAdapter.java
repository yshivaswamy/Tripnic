package com.tripman;

import java.util.List;

import com.tripman.engine.BoActivity;
import com.tripman.engine.BoActivityManager;
import com.tripman.engine.BoVisit;
import com.tripman.engine.BoVisitManager;

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

enum AdapterType
{
	TTypeTrip,
	ETypeActivity,
	ETypePlace,
	TTypeEnd
};

public class BoImageTextImageAdapter extends BaseAdapter {
    
    private Activity iActAvtivity;
    private static LayoutInflater inflater=null;
    List<?> iData = null;
    private AdapterType iType;
    
    public BoImageTextImageAdapter(Activity a, AdapterType aType) {
    	iActAvtivity = a;
    	iType = aType;
        inflater = (LayoutInflater)iActAvtivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        if (AdapterType.ETypeActivity == iType)
        	iData = BoActivityManager.getInstance().getAllActivity();
        else if(AdapterType.ETypePlace == iType)
        	iData = BoVisitManager.getInstance().getAllVisit();
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
            vi = inflater.inflate(R.layout.image_text_image_list_item, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        if (AdapterType.ETypeActivity == iType)
        {
        	BoActivity bo = BoActivityManager.getInstance().getActivityAt(position);
            title.setText(bo.getName());
            thumb_image.setImageResource(R.drawable.activity_icon);
        }
        else if(AdapterType.ETypePlace == iType)
        {
        	BoVisit vis = BoVisitManager.getInstance().getVisitAt(position);
        	title.setText(vis.getName());
        	thumb_image.setImageResource(R.drawable.pv);
        }
        
        
        
        return vi;
    }

	private View findViewById(int emptyList) {
		// TODO Auto-generated method stub
		return null;
	}
	
}