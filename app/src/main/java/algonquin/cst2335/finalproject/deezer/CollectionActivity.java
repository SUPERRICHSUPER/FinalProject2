package algonquin.cst2335.finalproject.deezer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;

/**
 * The {@code CollectionActivity} class represents an activity for displaying and managing a collection of songs.
 */
public class CollectionActivity extends AppCompatActivity {

    private RecyclerView rvCollection;
    private List<Song> list = new ArrayList<>();
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_deezer);
        initView();
    }

    /**
     * Initializes the view components of the activity.
     */
    private void initView() {
        rvCollection = findViewById(R.id.rv_collection);
        adapter = new CollectionAdapter(this, list);
        rvCollection.setAdapter(adapter);
        rvCollection.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new ArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showDeleteDialog(position);
            }
        });
        initData();
    }

    /**
     * Initializes the data for the collection by querying the database.
     */
    @SuppressLint("Range")
    public void initData() {
        SQLiteDatabase db = new SQHelper(this).getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from " + SQHelper.TABLE_NAME_SONG, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String duration = cursor.getString(cursor.getColumnIndex("duration"));
            String album_title = cursor.getString(cursor.getColumnIndex("album_title"));
            String cover = cursor.getString(cursor.getColumnIndex("cover"));
            int collection = cursor.getInt(cursor.getColumnIndex("collection"));
            Song song = new Song(id, title, duration, album_title, cover, collection);
            list.add(song);
        }
        cursor.close();
        db.close();
        adapter.notifyDataSetChanged();
    }

    /**
     * Shows a confirmation dialog for deleting a song from the collection.
     *
     * @param position The position of the selected song in the collection.
     */
    public void showDeleteDialog(int position) {
        Song song = list.get(position);
        new AlertDialog.Builder(this)
                .setTitle(R.string.msg_title)
                .setMessage(R.string.del_message)
                .setPositiveButton(
                        R.string.msg_confirm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Delete the song from the collection
                                deleteSong(song.getId());
                                list.remove(position);
                                adapter.notifyItemChanged(position);
                                Toast.makeText(getApplicationContext(), "Delete Successful!", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .show();
    }

    /**
     * Deletes a song from the database based on its ID.
     *
     * @param id The ID of the song to be deleted.
     */
    public void deleteSong(String id) {
        SQLiteDatabase db = new SQHelper(this).getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = new String[]{id};

        // Execute the delete operation
        int rowsDeleted = db.delete(SQHelper.TABLE_NAME_SONG, whereClause, whereArgs);
        db.close();
    }
}
