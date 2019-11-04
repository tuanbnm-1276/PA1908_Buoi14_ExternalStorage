package vn.sunasterisk.buoi14_externalstorage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;

public class ExternalStorageManager {

    private static final String TAG = ExternalStorageManager.class.getSimpleName();
    private Context mContext;

    public ExternalStorageManager(Context context) {
        mContext = context;
    }

    /**
     * Phải cấp quyền đọc External Storage trong file Manifest
     */
    public void readFileFromExternalStorage() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String downloadPath = path + "/" + Environment.DIRECTORY_DOWNLOADS;

        File rootFile = new File(downloadPath);
        File[] files = rootFile.listFiles();

        /*for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                Log.d(TAG, f.getName());
            } else if (f.isFile()) {
                Log.d(TAG, f.getName() +" "+ f.length() / 1024 + "KB");
            }
        }*/


        /**
         * lọc ra ra file ảnh jpg
         */
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg")) {
                Log.d(TAG, f.getName() + " " + f.length() / 1024 + "KB");
            }
        }

        Log.d(TAG, "============================================");

        File[] listFile = rootFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return (f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg"));
            }
        });

        for (int i = 0; i < listFile.length; i++) {
            File f = listFile[i];
            Log.d(TAG, f.getName() + " " + f.length() / 1024 + "KB");
        }
    }
}
