package algonquin.cst2335.finalproject.sunrise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import algonquin.cst2335.finalproject.R;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    Context context;
    List<SearchInfo> data;
    private OnItemClickListener listener;
    private OnItemLongClickListener longlistener;

    public CollectionAdapter(Context context, List<SearchInfo> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_sun_item, parent, false);
        return new ViewHolder(view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longlistener) {
        this.longlistener = longlistener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SearchInfo searchInfo=data.get(position);
        holder.tvLat.setText("LAT : "+searchInfo.getLat());
        holder.tvLng.setText("LNG : "+searchInfo.getLng());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "this is  "+(position+1) +" item !", Snackbar.LENGTH_SHORT).show();

                if (listener != null) {
                    listener.onItemClick(position);
                }

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longlistener!=null){
                    longlistener.onItemLongClick(position);
                }
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLat;
        private TextView tvLng;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLat = (TextView) itemView.findViewById(R.id.tv_lat);
            tvLng = (TextView) itemView.findViewById(R.id.tv_lng);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

}
