package algonquin.cst2335.finalproject.dict;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.R;


public class WordDetailFragment extends Fragment {

    private String word;
    private String definition;

    public WordDetailFragment() {
        // Required empty public constructor
    }

    public static WordDetailFragment newInstance(String word, String definition) {
        WordDetailFragment fragment = new WordDetailFragment();
        Bundle args = new Bundle();
        args.putString("word", word);
        args.putString("definition", definition);
        fragment.setArguments(args);
        Log.d("WordDetailFragment002", "newInstance: Word - " + word + ", Definition - " + definition);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = getArguments().getString("word");
            definition = getArguments().getString("definition");
            Log.d("WordDetailFragment003", "onCreate: Word - " + word + ", Definition - " + definition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);

        TextView wordTextView = view.findViewById(R.id.wordTextView);
        TextView definitionTextView = view.findViewById(R.id.definitionTextView);

        if (word != null && definition != null) {
            wordTextView.setText(word);
            definitionTextView.setText(definition);
            Log.d("WordDetailFragment004", "onCreateView: Updated TextViews with word and definition");
        } else {
            Log.d("WordDetailFragment005", "onCreateView: Word or Definition is null");
        }

        return view;
    }
}
