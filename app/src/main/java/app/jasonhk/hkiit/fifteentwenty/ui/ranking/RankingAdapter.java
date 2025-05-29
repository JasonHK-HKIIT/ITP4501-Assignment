package app.jasonhk.hkiit.fifteentwenty.ui.ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.model.RankingItem;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder>
{
    private Context context;
    private List<RankingItem> items;

    public RankingAdapter(List<RankingItem> items)
    {
        this.items = items;
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        var context = parent.getContext();
        var view = LayoutInflater.from(context).inflate(R.layout.adapter_ranking, parent, false);
        return new ViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.setRankingItem(position, items.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private Context context;

        private TextView rank;
        private TextView name;
        private TextView correctCount;
        private TextView timeUsed;

        public ViewHolder(Context context, View view)
        {
            super(view);

            this.context = context;
            rank = view.findViewById(R.id.adapter_ranking_text_rank);
            name = view.findViewById(R.id.adapter_ranking_text_name);
            correctCount = view.findViewById(R.id.adapter_ranking_text_correct);
            timeUsed = view.findViewById(R.id.adapter_ranking_text_elapsed);
        }

        public void setRankingItem(int position, RankingItem item)
        {
            rank.setText(context.getString(R.string.adapter_ranking_text_rank, position + 1));
            name.setText(item.name);
            correctCount.setText(String.valueOf(item.correctCount));
            timeUsed.setText(context.getString(R.string.adapter_ranking_text_elapsed, item.timeUsed));
        }
    }
}
