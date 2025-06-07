package app.jasonhk.hkiit.fifteentwenty.ui.records;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.entity.GameRecord;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>
{
    private List<GameRecord> records;

    public RecordAdapter()
    {
        this(new ArrayList<>());
    }

    public RecordAdapter(List<GameRecord> records)
    {
        this.records = records;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRecords(List<GameRecord> records)
    {
        this.records = records;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return records.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        var context = parent.getContext();
        var view = LayoutInflater.from(context).inflate(R.layout.adapter_record, parent, false);
        return new RecordAdapter.ViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.setRecordItem(records.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final Context context;

        private final TextView dateText;
        private final TextView timeText;
        private final TextView resultText;

        public ViewHolder(@NonNull Context context, @NonNull View view)
        {
            super(view);

            this.context = context;
            dateText = view.findViewById(R.id.adapter_record_text_date);
            timeText = view.findViewById(R.id.adapter_record_text_time);
            resultText = view.findViewById(R.id.adapter_record_text_result);
        }

        public void setRecordItem(@NonNull GameRecord record)
        {
            dateText.setText(record.timestamp.format(DateTimeFormatter.ISO_DATE));
            timeText.setText(record.timestamp.format(DateTimeFormatter.ISO_TIME));
            resultText.setText(context.getResources().getQuantityString(
                    record.isPlayerWon ? R.plurals.adapter_record_text_result_won : R.plurals.adapter_record_text_result_lost,
                    record.rounds, record.opponent, record.rounds));
        }
    }
}
