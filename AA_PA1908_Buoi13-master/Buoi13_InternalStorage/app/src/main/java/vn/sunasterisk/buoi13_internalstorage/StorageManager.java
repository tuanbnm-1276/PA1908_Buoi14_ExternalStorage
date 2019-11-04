package vn.sunasterisk.buoi13_internalstorage;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.widget.Toast;

import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKeys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

public class StorageManager {

    private Context mContext;

    public StorageManager(Context context) {
        mContext = context;
    }

    // Ghi file bình thường.
    public void writeFileToInternalStorage(String fileName, String contents) {
        FileOutputStream outputStream;

        try {
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(contents.getBytes());
            outputStream.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // Ghi file mã hóa
    public void writeFileToInternalStorage2(String fileName, String contents) {

        try {
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;

            String masterKeyAlias = null;
            masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            String parentPath = mContext.getFilesDir().getAbsolutePath();
            File file = new File(parentPath, fileName);

            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    file,
                    mContext,
                    masterKeyAlias,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build();

            FileOutputStream fileOutputStream = encryptedFile.openFileOutput();
            fileOutputStream.write(contents.getBytes());
            fileOutputStream.close();

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readFileFromInternalStorage2(String fileName) {

        try {
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            String masterKeyAlias = null;
            masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);

            String filePath = mContext.getFilesDir().getAbsolutePath() + "/" + fileName;
            File file = new File(filePath);

            if (!file.exists()) {
                Toast.makeText(mContext, "File Not Exist!", Toast.LENGTH_SHORT).show();
                return;
            }

            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    file,
                    mContext,
                    masterKeyAlias,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
            ).build();

            FileInputStream fileInputStream = encryptedFile.openFileInput();
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Toast.makeText(mContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existFile(String fileName) {
        String filePath = mContext.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(filePath);

        return file.exists();
    }


    // Dọc file bình thường
    public void readFileFromInternalStorage(String fileName) {
        String filePath = mContext.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(mContext, "File Not Exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileInputStream fileInputStream = mContext.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            // sẽ dùng thằng BufferedReader để đọc dữ liệu.
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // Dùng thằng StringBuilder để chứ dữ liệu
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";

            //BufferedReader sẽ đọc theo dòng
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Toast.makeText(mContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        //mContext.deleteFile(fileName);
        String filePath = mContext.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            Toast.makeText(mContext, "File Not Exist", Toast.LENGTH_SHORT).show();
        }

        file.delete();
        Toast.makeText(mContext, "File Deleted!", Toast.LENGTH_SHORT).show();

    }
}
