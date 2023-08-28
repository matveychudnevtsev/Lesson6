package ru.mirea.chudnevtsevmr.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import ru.mirea.chudnevtsevmr.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);
        binding.group.setText(sharedPref.getString("group", ""));
        binding.index.setText(sharedPref.getString("index", ""));
        binding.film.setText(sharedPref.getString("film", ""));

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("group", String.valueOf(binding.group.getText()));
                editor.putString("index", String.valueOf(binding.index.getText()));
                editor.putString("film", String.valueOf(binding.film.getText()));
                editor.apply();
            }
        });
    }
}