package com.example.a2048mult;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView welcomeMessageTV = new TextView(this);
        welcomeMessageTV.setText("Hello CodeYourApp World!");
        setContentView(R.layout.main_activity_layout);
    }
}