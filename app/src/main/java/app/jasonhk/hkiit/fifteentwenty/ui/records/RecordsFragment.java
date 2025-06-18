package app.jasonhk.hkiit.fifteentwenty.ui.records;

import java.util.concurrent.Executors;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.database.RecordsDatabase;

public class RecordsFragment extends Fragment
{
    private RecordsDatabase database;

    private RecyclerView recordsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_records, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.fragment_records_toolbar);
        toolbar.setNavigationOnClickListener((v) -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        database = Room.databaseBuilder(requireContext(), RecordsDatabase.class, RecordsDatabase.FILENAME).build();

        recordsList = view.findViewById(R.id.fragment_records_list_records);
        recordsList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        loadGameRecords();

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.fragment_records_toolbar_wrapper), (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, 0);
            return windowInsets;
        });
        ViewCompat.setOnApplyWindowInsetsListener(recordsList, (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, 0, insets.right, insets.bottom);
            return windowInsets;
        });
    }

    private void loadGameRecords()
    {
        var executor = Executors.newSingleThreadExecutor();
        var handler = new Handler(Looper.getMainLooper());

        executor.execute(() ->
        {
            var records = database.gameRecordDao().getAll();
            handler.post(() -> recordsList.setAdapter(new RecordsAdapter(records)));
        });
    }
}
