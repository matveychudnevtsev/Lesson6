package ru.mirea.chudnevtsevmr.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.chudnevtsevmr.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE_PERMISSION = 100;
    private boolean isWork = false;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int storagePermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.
                WRITE_EXTERNAL_STORAGE);

        if (storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {

            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }


        binding.buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS);
                File file = new File(path, binding.editTextFilename.getText().toString().concat(".txt"));
                String string = binding.editTextQuote.getText().toString();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
                    OutputStreamWriter outputStream = new OutputStreamWriter(fileOutputStream);
                    outputStream.write(string);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        binding.buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS);
                File file = new File(path, binding.editTextFilename.getText().toString().concat(".txt"));
                try {
                    FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                    List<String> lines = new ArrayList<>();
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    binding.editTextQuote.setText(line);
                    Log.w("ExternalStorage", String.format("Read from file %s successful", line));
                } catch (Exception e) {
                    Log.w("ExternalStorage", String.format("Read from file %s failed", e.getMessage()));
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }
}