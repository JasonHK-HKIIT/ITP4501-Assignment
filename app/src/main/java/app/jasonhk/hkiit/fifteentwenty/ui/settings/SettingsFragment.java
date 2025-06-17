package app.jasonhk.hkiit.fifteentwenty.ui.settings;

import java.util.Objects;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.Theme;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @SuppressWarnings("FieldCanBeLocal")
    private String PREFERENCE_THEME;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey)
    {
        PREFERENCE_THEME = getString(R.string.preference_theme);

        setPreferencesFromResource(R.xml.preferences, rootKey);

        ListPreference themePreference = Objects.requireNonNull(findPreference(PREFERENCE_THEME));
        themePreference.setEntries(R.array.preference_theme_entries);
        themePreference.setEntryValues(Theme.names());
        if (themePreference.getValue() == null) { themePreference.setValueIndex(Theme.AUTO.ordinal()); }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

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
}
