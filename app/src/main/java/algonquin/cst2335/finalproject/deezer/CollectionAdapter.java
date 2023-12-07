package algonquin.cst2335.finalproject.deezer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import algonquin.cst2335.finalproject.R;

/**
 * The {@code CollectionAdapter} class is a custom RecyclerView adapter for displaying a collection of songs.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private Context context;
    private List<Song> data;
    private ArtistAdapter.OnItemClickListener listener;

    /**
     * Constructs a {@code CollectionAdapter} with the specified context and data.
     *
     * @param context The context of the calling activity or fragment.
     * @param data    The list of songs to be displayed in the collection.
     */
    public CollectionAdapter(Context context, List<Song> data) {
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
        holder.imageView2.setImageResource(android.R.drawable.star_on);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is item " + (position + 1) + "!", Snackbar.LENGTH_SHORT).show();

                if (listener != null) {
                    listener.onItemClick(position);
                }
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
     * Sets the item click listener for the adapter.
     *
     * @param listener The {@code OnItemClickListener} to be set.
     */
    public void setOnItemClickListener(ArtistAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Interface definition for a callback to be invoked when an item in the RecyclerView is clicked.
     */
    public interface OnItemClickListener {
        /**
         * Called when an item in the RecyclerView is clicked.
         *
         * @param position The position of the clicked item in the data set.
         */
        void onItemClick(int position);
    }
}
