package app.jasonhk.hkiit.fifteentwenty.ui.settings;

import java.util.Objects;
import java.util.concurrent.Executors;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.room.Room;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.Theme;
import app.jasonhk.hkiit.fifteentwenty.database.RecordsDatabase;

public class SettingsFragment extends PreferenceFragmentCompat
{
    private RecordsDatabase database;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey)
    {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        ListPreference themePreference = Objects.requireNonNull(findPreference(getString(R.string.preference_theme)));
        themePreference.setEntries(R.array.preference_theme_entries);
        themePreference.setEntryValues(Theme.names());
        if (themePreference.getValue() == null) { themePreference.setValueIndex(Theme.AUTO.ordinal()); }

        Preference clearRecordsPreference = Objects.requireNonNull(findPreference(getString(R.string.preference_clear_records)));
        clearRecordsPreference.setOnPreferenceClickListener(this::onClearRecordsPreferenceClick);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        database = Room.databaseBuilder(requireContext(), RecordsDatabase.class, RecordsDatabase.FILENAME).build();

        Toolbar toolbar = view.findViewById(R.id.fragment_settings_toolbar);
        toolbar.setNavigationOnClickListener((v) -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.fragment_settings_toolbar_wrapper), (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, 0);
            return windowInsets;
        });
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(android.R.id.list_container), (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, 0, insets.right, insets.bottom);
            return windowInsets;
        });
    }

    private void clearGameRecords()
    {
        var executor = Executors.newSingleThreadExecutor();
        var handler = new Handler(Looper.getMainLooper());

        executor.execute(() ->
        {
            database.gameRecordDao().deleteAll();

            handler.post(() ->
                    Snackbar.make(requireView(), R.string.fragment_settings_message_records_cleared, Snackbar.LENGTH_LONG).show());
        });
    }

    private boolean onClearRecordsPreferenceClick(Preference preference)
    {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_clear_records_title)
                .setMessage(R.string.dialog_clear_records_message)
                .setPositiveButton(R.string.dialog_clear_records_action_yes, (d, w) -> clearGameRecords())
                .setNegativeButton(R.string.dialog_clear_records_action_no, (d, w) -> { })
                .show();

        return true;
    }
}
