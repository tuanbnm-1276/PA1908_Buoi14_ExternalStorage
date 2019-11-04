package vn.sunasterisk.buoi13_internalstorage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StorageManager mStorageManager;

    private Button mButtonWrite;
    private Button mButtonCheck;
    private Button mButtonRead;
    private Button mButtonDelete;

    /**
     * Khi luu giu lieu o Internal Storage, sau khi ban xoa ung dung
     * di thi du lieu do cung mat luon.
     * Han che luu giu lieu o day, chi dung luu nhung giu lieu bi mat
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        registerListeners();
    }

    private void registerListeners() {
        mButtonWrite.setOnClickListener(this);
        mButtonCheck.setOnClickListener(this);
        mButtonRead.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
    }

    private void initComponents() {
        mButtonWrite = findViewById(R.id.button_write);
        mButtonCheck = findViewById(R.id.button_check);
        mButtonRead = findViewById(R.id.button_read);
        mButtonDelete = findViewById(R.id.button_delete);
        mStorageManager = new StorageManager(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_write:
                writeToInternalStorage();
                break;
            case R.id.button_check:
                checkFileExistInternalStorage();
                break;
            case R.id.button_read:
                readFileFromInternalStorage();
            case R.id.button_delete:
                mStorageManager.deleteFile("PA1908.txt");
                break;
            default:
                break;
        }
    }

    private void readFileFromInternalStorage() {
        //mStorageManager.readFileFromInternalStorage("PA1908.txt");
        mStorageManager.readFileFromInternalStorage2("PA1909.txt");
    }

    private void checkFileExistInternalStorage() {
        boolean existFile = mStorageManager.existFile("PA1908.txt");
        if(existFile){
            Toast.makeText(MainActivity.this, "File Exist!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "File Not Exist", Toast.LENGTH_SHORT).show();
        }

    }

    private void writeToInternalStorage() {

        //mStorageManager.writeFileToInternalStorage("PA1908.txt", "Toan nhung nguoi Dep Zai!");
        mStorageManager.writeFileToInternalStorage2("PA1909.txt", "Toan nhung Dai Gia!");
    }
}
