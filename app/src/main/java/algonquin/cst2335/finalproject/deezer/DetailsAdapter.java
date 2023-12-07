package algonquin.cst2335.finalproject.deezer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import algonquin.cst2335.finalproject.R;

/**
 * The {@code DetailsAdapter} class is a custom RecyclerView adapter for displaying song details.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private Context context;
    private List<Song> data;

    /**
     * Constructs a {@code DetailsAdapter} with the specified context and data.
     *
     * @param context The context of the calling activity or fragment.
     * @param data    The list of songs to be displayed.
     */
    public DetailsAdapter(Context context, List<Song> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = data.get(position);
        holder.tvTitle.setText(song.getTitle());
        holder.tvAlbumTitle.setText(song.getAlbumTitle());
        holder.tvDuration.setText(song.getDuration());
        Picasso.get().load(song.getCover()).into(holder.imgAlbum);
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageView2.setImageResource(android.R.drawable.star_on);
                Toast.makeText(context.getApplicationContext(), "Collection successful!", Toast.LENGTH_SHORT).show();
                addToCollection(song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * The {@code ViewHolder} class represents each item view in the RecyclerView.
     * It holds references to the views within the item layout.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAlbum;
        private TextView tvTitle;
        private TextView tvAlbumTitle;
        private TextView tvDuration;
        private ImageView imageView2;

        /**
         * Constructs a {@code ViewHolder} with the specified item view.
         *
         * @param itemView The item view for the ViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.img_album);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAlbumTitle = itemView.findViewById(R.id.tv_album_title);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            imageView2 = itemView.findViewById(R.id.imageView2);
        }
    }

    /**
     * Adds a song to the collection in the database.
     *
     * @param song The song to be added to the collection.
     */
    public void addToCollection(Song song) {
        SQLiteDatabase db = new SQHelper(context.getApplicationContext()).getWritableDatabase();
        db.execSQL("insert into " + SQHelper.TABLE_NAME_SONG + "(id, title, duration, album_title, cover, collection) values(?,?,?,?,?,?)",
                new Object[]{song.getId(), song.getTitle(), song.getDuration(), song.getAlbumTitle(), song.getCover(), 1});
    }
}
