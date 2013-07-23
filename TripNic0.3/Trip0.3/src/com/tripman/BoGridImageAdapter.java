package com.tripman;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BoGridImageAdapter extends BaseAdapter {
    private Activity mActivity;
    private String iMedia_directory;

    public BoGridImageAdapter(Activity c, String adir) {
    	mActivity = c;
    	iMedia_directory = adir;
    }

    public int getCount() {
    	
    	//String folderPath = Environment.getExternalStorageDirectory().toString()+"//media";
    	File myNewFolder = new File(iMedia_directory);
        myNewFolder.mkdir();
    	File imagesFolder = new File(iMedia_directory);
    	if(imagesFolder.exists() && imagesFolder.isDirectory())
    	{
    		return imagesFolder.list().length;
    	}
    	
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        //String folderPath = Environment.getExternalStorageDirectory().toString()+"//media";
        File imagesFolder = new File(iMedia_directory);
        
        if (convertView == null) {
        	
        	if(imagesFolder.exists() && imagesFolder.isDirectory())
        	{
        		Bitmap thumbnail = null;
        		
        		String fileList[] = imagesFolder.list();
        		String test = fileList[position];
        		String ext = test.substring(test.length()-4);
	        	LayoutInflater li = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	v = li.inflate(R.layout.grid_item_image_text, null);
	        	
	        	String filePath = iMedia_directory+"//"+fileList[position];
	        	
	        	if (0 == ext.compareTo(".jpg"))
	        	{
	        		Uri uri = Uri.fromFile(new File(filePath));
		            thumbnail = getPreview(uri);
	        	}
	        	else if (0 == ext.compareTo(".mp3"))
	        	{
		            thumbnail = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.audio_record);
	        	}
	        	else if (0 == ext.compareTo(".mp4"))
	        	{
		            thumbnail = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.video_record);
	        	}
	            
	            TextView title = (TextView)v.findViewById(R.id.grid_item_text);
	            ImageView thumb_image=(ImageView)v.findViewById(R.id.grid_item_image);
	            
	            thumb_image.setImageBitmap(thumbnail);
	            title.setText(fileList[position]);
        	}
        }
        
        return v;
    }

    Bitmap getPreview(Uri uri) {
        File image = new File(uri.getPath());

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image.getPath(), bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
            return null;

        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / 50;
        return BitmapFactory.decodeFile(image.getPath(), opts);     
    }
  }
