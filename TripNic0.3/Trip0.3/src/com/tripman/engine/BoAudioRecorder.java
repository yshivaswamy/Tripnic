package com.tripman.engine;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;

public class BoAudioRecorder 
{
	 final MediaRecorder recorder = new MediaRecorder();
	  
	  /**
	   * Starts a new recording.
	   */
	  public void start(String path) throws IOException {
	    String state = android.os.Environment.getExternalStorageState();
	    if(!state.equals(android.os.Environment.MEDIA_MOUNTED))  {
	        throw new IOException("SD Card is not mounted.  It is " + state + ".");
	    }

	    // make sure the directory we plan to store the recording in exists
	    File directory = new File(path).getParentFile();
	    if (!directory.exists() && !directory.mkdirs()) {
	      throw new IOException("Path to file could not be created.");
	    }

	    recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(path);
	    recorder.prepare();
	    recorder.start();
	  }
	  
	  /**
	   * Stops a recording that has been previously started.
	   */
	  public void stop() throws IOException {
	    recorder.stop();
	    recorder.release();
	  }

}
