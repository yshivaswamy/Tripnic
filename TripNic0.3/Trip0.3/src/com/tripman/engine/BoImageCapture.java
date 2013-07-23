package com.tripman.engine;

import java.io.File;

import com.tripman.BoAudioVideoContentProvider;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

public class BoImageCapture 
{
	final int CAMERA_CAPTURE = 1;
	private String iRootDir = null;
	private Activity iActivity = null;
	
	public BoImageCapture(String aRootDir, Activity aActivity)
	{
		iRootDir = aRootDir;
		iActivity = aActivity;
	}
	
	public void capture_image()
	{
		try {
			File image = new File(iRootDir, getNewImageFileName());
			Uri uriSavedImage = Uri.fromFile(image);
			
			
		    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, BoAudioVideoContentProvider.CONTENT_URI);
		    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
		    
		    iActivity.startActivity(captureIntent);
		}
		catch(ActivityNotFoundException anfe){
		    //display an error message
		    String errorMessage = "Whoops - your device doesn't support capturing images!";
		    Toast toast = Toast.makeText(iActivity, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		} 
	}

	private String getNewImageFileName()
	{
		String imageName = "image";
		String searchName = imageName;
		
		//String imagesFolderPath = iRootDir;
		File imagesFolder = new File(iRootDir);
		
		if(imagesFolder.exists() && imagesFolder.isDirectory())
		{
			String fileList[] = imagesFolder.list();
			for(int loop = 0; loop < fileList.length; loop++)
			{
				File inFile = new File(imagesFolder.getPath()+"//"+searchName+".jpg");
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
		
		return searchName+".jpg";
	}
}
