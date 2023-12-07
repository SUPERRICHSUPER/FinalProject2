package algonquin.cst2335.finalproject.deezer;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;

/**
 * The {@code DetailsActivity} class represents an activity displaying details about songs retrieved from Deezer.
 */
public class DetailsActivity extends AppCompatActivity {
    String url;
    private Button button;
    private RecyclerView rvSong;
    List<Song> list = new ArrayList<>();
    DetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        url = getIntent().getStringExtra("url");
        initView();
    }

    /**
     * Initializes the view components of the activity.
     */
    private void initView() {
        button = findViewById(R.id.button);
        rvSong = findViewById(R.id.rv_song);
        adapter = new DetailsAdapter(this, list);
        rvSong.setAdapter(adapter);
        rvSong.setLayoutManager(new LinearLayoutManager(this));
        initData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, CollectionActivity.class));
            }
        });
    }

    /**
     * Retrieves and populates the song data from Deezer API.
     */
    public void initData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());

                        try {
                            JSONObject responseJson = new JSONObject(response.toString());
                            JSONArray artists = responseJson.getJSONArray("data");

                            for (int i = 0; i < artists.length(); i++) {
                                JSONObject artist = artists.getJSONObject(i);
                                String id = artist.getString("id");
                                String title = artist.getString("title");
                                String duration = artist.getString("duration");
                                String album = artist.getString("album");
                                JSONObject responseJson2 = new JSONObject(album);
                                String album_title = responseJson2.getString("title");
                                String cover = responseJson2.getString("cover_small");
                                Song song1 = new Song(id, title, duration, album_title, cover);
                                list.add(song1);
                                adapter.notifyDataSetChanged();
                                Log.i(TAG, "onResponse: ---------" + song1.toString());
                            }

                            Log.i("TAG", "onClick: ---------------artlist is " + list.size());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        queue.add(jsonObjectRequest);
    }
}
