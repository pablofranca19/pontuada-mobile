package com.example.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int numberOfRuns;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonConfig = findViewById(R.id.button_config);
        Button buttonAbout = findViewById(R.id.button_about);
        Button buttonExit = findViewById(R.id.button_exit);

        // opção 1: buttonConfig.setOnClickListener(this); + implementar o método no OnCreate
        buttonExit.setOnClickListener(this);

        sharedPreferences=getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        numberOfRuns=sharedPreferences.getInt("NumeroRodada", 0);
        numberOfRuns++;
        String mens="Esta é a "+numberOfRuns+"a. vez que esta aplicação roda";
        Toast.makeText(this,mens, Toast.LENGTH_LONG).show();
    }

    protected void onPause() {
        super.onPause();
        editor=sharedPreferences.edit();
        if (editor!=null) {
            editor.putInt("NumeroRodada", numberOfRuns);
            editor.commit();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_exit) {
            finish();
        }
    }
}