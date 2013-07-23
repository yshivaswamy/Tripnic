package com.tripman.engine;

import java.io.File;
import java.io.IOException;

import com.tripman.R;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class BoAudioCapture implements OnClickListener
{
	private BoAudioRecorder iRecorder = new BoAudioRecorder();
	private String iRootDir = null;
	private Activity iActivity = null;
	Dialog iRecdialog = null;
	int position;
	String test;
	public BoAudioCapture(String aRootDir, Activity aActivity)
	{
		iRootDir = aRootDir;
		iActivity = aActivity;
	}
	
	public void Record()
	{
		iRecdialog = new Dialog(iActivity);
		iRecdialog.setContentView(R.layout.record_dialog);
		iRecdialog.setTitle("Title...");

		Button startButton = (Button) iRecdialog.findViewById(R.id.rec_start_bnt);
		startButton.setOnClickListener(this);
		
		Button stopButton = (Button) iRecdialog.findViewById(R.id.rec_stop_bnt);
		stopButton.setOnClickListener(this);
		
		Button closeButton = (Button) iRecdialog.findViewById(R.id.rec_close_bnt);
		closeButton.setOnClickListener(this);
		
		iRecdialog.show();
	}
	
	private String getNewAudioFileName()
	{
		String imageName = "sound_clip";
		String searchName = imageName;

		File imagesFolder = new File(iRootDir);
		
		if(imagesFolder.exists() && imagesFolder.isDirectory())
		{
			String fileList[] = imagesFolder.list();
			for(int loop = 0; loop < fileList.length; loop++)
			{
				File inFile = new File(imagesFolder.getPath()+"//"+searchName+".mp3");
				if (inFile.exists())
				{
					searchName = imageName+String.valueOf(loop+1);
				}
				else
				{
					break;
				}
			}
		}
		else
			imagesFolder.mkdirs();
		
		return searchName+".mp3";
	}

	public void onClick(View v) {
		if(v.getId() == R.id.rec_start_bnt)
		{
			
			String filePath = iRootDir+"//"+getNewAudioFileName();
			try {
				iRecorder.start(filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(v.getId() == R.id.rec_stop_bnt)
		{
			try {
				iRecorder.stop();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(v.getId() == R.id.rec_close_bnt)
		{
			iRecdialog.dismiss();
		}
		
	}
}
