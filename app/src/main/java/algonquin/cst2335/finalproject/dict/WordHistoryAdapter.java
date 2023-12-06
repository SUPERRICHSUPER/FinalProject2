package algonquin.cst2335.finalproject.dict;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.finalproject.R;


public class WordHistoryAdapter extends RecyclerView.Adapter<WordHistoryAdapter.WordHistoryViewHolder> {

    private List<WordHistory> wordHistoryList;
    private OnItemClickListener listener;

    public WordHistoryAdapter(List<WordHistory> wordHistoryList, OnItemClickListener listener) {
        this.wordHistoryList = wordHistoryList;
        this.listener = listener;
    }

    @Override
    public WordHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_history_item, parent, false);
        return new WordHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordHistoryViewHolder holder, int position) {
        WordHistory wordHistory = wordHistoryList.get(position);
        holder.wordTextView.setText(wordHistory.getWord()); // 设置文字
        holder.itemView.setOnClickListener(v -> listener.onItemClick(wordHistory)); // 设置点击监听器
    }



    @Override
    public int getItemCount() {
        return wordHistoryList.size();
    }

    public class WordHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView;

        public WordHistoryViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.wordTitleTextView); // 确保这里的 ID 正确
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(wordHistoryList.get(position));
                    }
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(WordHistory wordHistory);
    }




}
