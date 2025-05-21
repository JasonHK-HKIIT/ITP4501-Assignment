package app.jasonhk.hkiit.mathsgame.ui.ranking;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import app.jasonhk.hkiit.mathsgame.R;
import app.jasonhk.hkiit.mathsgame.model.RankingItem;

public class RankingAdapter extends BaseAdapter
{
    private Context context;
    private List<RankingItem> items;

    private LayoutInflater inflater;

    public RankingAdapter(Context context, List<RankingItem> items)
    {
        this.context = context;
        this.items = items;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        var item = items.get(position);
        return items.indexOf(item);
    }

    @Override
    public View getView(int position, View view, ViewGroup container)
    {
        ViewHolder holder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.adapter_ranking, container, false);

            holder = new ViewHolder();
            holder.rank = view.findViewById(R.id.adapter_ranking_text_rank);
            holder.name = view.findViewById(R.id.adapter_ranking_text_name);
            holder.correctCount = view.findViewById(R.id.adapter_ranking_text_correct);
            holder.timeUsed = view.findViewById(R.id.adapter_ranking_text_elapsed);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        var item = items.get(position);
        holder.rank.setText(context.getString(R.string.adapter_ranking_text_rank, position + 1));
        holder.name.setText(item.name);
        holder.correctCount.setText(String.valueOf(item.correctCount));
        holder.timeUsed.setText(context.getString(R.string.adapter_ranking_text_elapsed, item.timeUsed));

        return view;
    }

    private static class ViewHolder
    {
        public TextView rank;
        public TextView name;
        public TextView correctCount;
        public TextView timeUsed;
    }
}
