package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;
import algonquin.cst2335.finalproject.deezer.MainActivity_deezer;
import algonquin.cst2335.finalproject.dict.MainActivity_dict;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.music.setOnClickListener( click ->
                startActivity(new Intent(this, MainActivity_deezer.class)));
        binding.dictionary.setOnClickListener( click ->
                startActivity(new Intent(this, MainActivity_dict.class)));
//        binding.triviaButton.setOnClickListener( click ->
//                startActivity(new Intent(this, TriviaQuestion.class)));
//        binding.bearButton.setOnClickListener( click ->
//                startActivity(new Intent(this, BearImageGenerator.class)));
    }
}