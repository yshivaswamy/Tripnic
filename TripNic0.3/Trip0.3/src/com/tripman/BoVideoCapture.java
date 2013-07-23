package com.tripman;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class BoVideoCapture extends Activity  implements SurfaceHolder.Callback
{
	public MediaRecorder mrec = null; //new MediaRecorder();
	private Camera mCamera;
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	
	private String iRootDir = null;
	private boolean iIsRecording = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_surface);
        mCamera = Camera.open();
        surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        iRootDir = this.getIntent().getStringExtra("root_directory");
        
        iIsRecording = false;
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
		getMenuInflater().inflate(R.menu.video_rec_menu, menu);
		return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(R.id.rec_start_stop == item.getItemId())
	    {

			if (iIsRecording == false)
			{
				try {
					startRecording();
					item.setIcon(R.drawable.rec_stop);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				stopRecording();
				item.setIcon(R.drawable.rec_strt);
			}

	    }
	    return true;
	}
	
	protected void startRecording() throws IOException 
    {
        mrec = new MediaRecorder();  // Works well
        mCamera.unlock();

        mrec.setCamera(mCamera);

        mrec.setPreviewDisplay(surfaceHolder.getSurface());
        mrec.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mrec.setAudioSource(MediaRecorder.AudioSource.MIC); 

        mrec.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mrec.setPreviewDisplay(surfaceHolder.getSurface());
        mrec.setOutputFile(iRootDir+"//"+getNewVideoFileName()); 

        mrec.prepare();
        mrec.start();
        
        iIsRecording = true;
    }

	protected void stopRecording() {
		mrec.stop();
        mrec.release();
        mrec = null;
        
        iIsRecording = false;
    }

    private void releaseMediaRecorder(){
        if (mrec != null) {
            mrec.reset();   // clear recorder configuration
            mrec.release(); // release the recorder object
            mrec = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
    
	private String getNewVideoFileName()
	{
		String fileName = "video_clip";
		String searchName = fileName;
		
		//String imagesFolderPath = iRootDir;
		File imagesFolder = new File(iRootDir);
		
		if(imagesFolder.exists() && imagesFolder.isDirectory())
		{
			String fileList[] = imagesFolder.list();
			for(int loop = 0; loop < fileList.length; loop++)
			{
				File inFile = new File(imagesFolder.getPath()+"//"+searchName+".mp4");
				if (inFile.exists())
				{
					searchName = fileName+String.valueOf(loop+1);
				}
				else
				{
					break;
				}
			}
		}
		else
			imagesFolder.mkdirs();
		
		return searchName+".mp4";
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) 
	{
		if (mCamera != null){
            Parameters params = mCamera.getParameters();
            mCamera.setParameters(params);
        }
        else {
            Toast.makeText(getApplicationContext(), "Camera not available!", Toast.LENGTH_LONG).show();
            //iActivity.finish();
        }
	}

	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		mCamera.stopPreview();
        mCamera.release();
	}
	
}
