package com.example.a2048mult;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonSinglePlayer;
    Button buttonMultiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // create Function for buttonSingleplayer
        buttonSinglePlayer = findViewById(R.id.buttonSingleplayer);
        buttonSinglePlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_singleplayer_choosing);
            }
        });


        // create Function for buttonMultiplayer
        buttonMultiplayer = findViewById(R.id.buttonMultiplayer);
        buttonMultiplayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_multiplayer_choosing);
            }
        });

    }
}