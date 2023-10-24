package com.example.a2048mult;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // create Function for buttonSingleplayer
        final Button buttonSinglePlayer = findViewById(R.id.buttonSingleplayer);
        buttonSinglePlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });


        // create Function for buttonMultiplayer
        final Button buttonMultiplayer = findViewById(R.id.buttonMultiplayer);
        buttonMultiplayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

    }
}