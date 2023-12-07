package algonquin.cst2335.finalproject.deezer;

import android.annotation.SuppressLint;
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
 * The {@code ArtistAdapter} class is a custom RecyclerView adapter for displaying a list of artists.
 * It binds the data to the corresponding views and handles item clicks.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private Context context;
    private List<Artist> data;
    private OnItemClickListener listener;

    /**
     * Constructs an {@code ArtistAdapter} with the specified context and data.
     *
     * @param context The context of the calling activity or fragment.
     * @param data    The list of artists to be displayed.
     */
    public ArtistAdapter(Context context, List<Artist> data) {
        this.context = context;
        this.data = data;
    }

    /**
     * Sets the item click listener for the adapter.
     *
     * @param listener The {@code OnItemClickListener} to be set.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.artist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Artist artist = data.get(position);
        holder.tvName.setText("Artist : " + artist.getName());
        holder.textView2.setText("Fan : " + artist.getNb_fan());
        holder.textView3.setText("Album : " + artist.getNb_album());

        Picasso.get().load(artist.getPicture()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is " + (position + 1) + " item!", Snackbar.LENGTH_SHORT).show();

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
        private ImageView imageView;
        private TextView tvName;
        private TextView textView2;
        private TextView textView3;

        /**
         * Constructs a {@code ViewHolder} with the specified item view.
         *
         * @param itemView The item view for the ViewHolder.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tv_name);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
        }
    }
}
