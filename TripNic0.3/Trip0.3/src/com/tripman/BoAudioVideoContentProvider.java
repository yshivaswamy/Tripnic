package com.tripman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class BoAudioVideoContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse
                                    ("content://com.example.camerademo/");
    private static final HashMap<String, String> MIME_TYPES = 
                                     new HashMap<String, String>();

    static {
        MIME_TYPES.put(".jpg", "image/jpeg");
        MIME_TYPES.put(".jpeg", "image/jpeg");
    }

    @Override
    public boolean onCreate() {

        try {
            getContext().getContentResolver().notifyChange(CONTENT_URI, null);
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public String getType(Uri uri) {
        String path = uri.toString();

        File mFile = new File(getContext().getFilesDir(), "newImage.jpg");
        if(!mFile.exists()) {
            try {
				mFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        for (String extension : MIME_TYPES.keySet()) {
            if (path.endsWith(extension)) {
                return (MIME_TYPES.get(extension));
            }
        }
        return (null);
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
    throws FileNotFoundException {

        File f = new File(getContext().getFilesDir(), "newImage.jpg");
        if (f.exists()) {
            return (ParcelFileDescriptor.open(f,
                    ParcelFileDescriptor.MODE_READ_WRITE));
        }
        throw new FileNotFoundException(uri.getPath());
    }

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
}
