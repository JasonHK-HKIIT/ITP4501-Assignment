package app.jasonhk.hkiit.fifteentwenty.ui.opponent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import app.jasonhk.hkiit.fifteentwenty.R;

public class OpponentsAdapter extends RecyclerView.Adapter<OpponentsAdapter.ViewHolder>
{
    private final String[] opponents;

    @Nullable
    private OnItemClickListener onItemClickListener = null;

    public OpponentsAdapter(String[] opponents)
    {
        this.opponents = opponents;
    }

    @Override
    public int getItemCount()
    {
        return opponents.length;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener)
    {
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        var context = parent.getContext();
        var view = LayoutInflater.from(context).inflate(R.layout.adapter_opponent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.setText(opponents[position]);
        holder.itemView.setOnClickListener((v) ->
        {
            if (onItemClickListener != null)
            {
                onItemClickListener.onItemClick(this, v, position);
            }
        });
    }

    public interface OnItemClickListener
    {
        void onItemClick(OpponentsAdapter adapter, View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(@NonNull View view)
        {
            super(view);
        }

        public void setText(String text)
        {
            ((TextView) itemView).setText(text);
        }
    }
}
