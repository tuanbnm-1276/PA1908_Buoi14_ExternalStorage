package vn.sunasterisk.buoi14_externalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ExternalStorageManager mExternalStorageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExternalStorageManager = new ExternalStorageManager(this);

        mExternalStorageManager.readFileFromExternalStorage();
    }
}
