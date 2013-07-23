package com.tripman;

import java.io.File;

import com.tripman.engine.BoAudioCapture;
import com.tripman.engine.BoImageCapture;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class BoMediaDisplay extends Activity{

	String iMedia_directory;
	
	@Override
	protected void onResume()
	{
		super.onResume();
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new BoGridImageAdapter(this,iMedia_directory ));
	}
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_media_display);

	    //iMedia_directory = Environment.getExternalStorageDirectory().toString()+"//media";
	    iMedia_directory = this.getIntent().getStringExtra("media_directory");
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new BoGridImageAdapter(this, iMedia_directory));

	    gridview.setOnItemClickListener(new OnItemClickListener() {

	    public void onItemClick(AdapterView<?> parentView, View view, int position,long id) {
				
				TextView title = (TextView)view.findViewById(R.id.grid_item_text);
				String fileName = title.getText().toString();
				String ext = fileName.substring(fileName.length()-4);
				
				if (0 == ext.compareTo(".jpg"))
	        	{
					Uri uri = Uri.fromFile(new File(iMedia_directory+"//"+fileName));
					Intent i = new Intent(android.content.Intent.ACTION_VIEW);
					i.setDataAndType(uri, "image/jpg");
					startActivity(i);
	        	}
	        	else if (0 == ext.compareTo(".mp3"))
	        	{
	        		File musicFile2Play = new File(iMedia_directory+"//"+fileName);
	        		Intent i2 = new Intent();
	        		i2.setAction(android.content.Intent.ACTION_VIEW);
	        		i2.setDataAndType(Uri.fromFile(musicFile2Play), "audio/mp3");
	        		startActivity(i2);
	        	}
	        	else if (0 == ext.compareTo(".mp4"))
	        	{
	        		File videoFile2Play = new File(iMedia_directory+"//"+fileName);
	        		Intent i = new Intent();
	        		i.setAction(android.content.Intent.ACTION_VIEW);
	        		i.setDataAndType(Uri.fromFile(videoFile2Play), "video/mp4");
	        		startActivity(i);
	        	}
			}
	    });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media_add_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//String path = Environment.getExternalStorageDirectory().toString()+"//media";
		
		switch(item.getItemId())
	    {
		    case R.id.new_image:
			{
				BoImageCapture test = new BoImageCapture(iMedia_directory, this);
				test.capture_image();
			}
			break;	
		    case R.id.new_audio:
		    {
		    	BoAudioCapture test = new BoAudioCapture(iMedia_directory, this);
				test.Record();
		    }
		    break;
		    case R.id.new_video:
		    {
		    	Intent video_capture_activity = new Intent(BoMediaDisplay.this,BoVideoCapture.class);
		    	video_capture_activity.putExtra("root_directory", iMedia_directory);
				startActivity(video_capture_activity);
		    }
		    break;
		    default:
		        break;

	    }
	    return true;
	}
}
