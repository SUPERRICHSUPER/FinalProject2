package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;
import algonquin.cst2335.finalproject.dict.MainActivity_dict;
import algonquin.cst2335.finalproject.sunrise.MainActivity_sun;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.aviationButton.setOnClickListener( click ->
//                startActivity(new Intent(this, FlightTracker.class)));
        binding.dictionary.setOnClickListener( click ->
                startActivity(new Intent(this, MainActivity_dict.class)));
        binding.Sunrise.setOnClickListener( click ->
                startActivity(new Intent(this, MainActivity_sun.class)));
//        binding.bearButton.setOnClickListener( click ->
//                startActivity(new Intent(this, BearImageGenerator.class)));
    }
}