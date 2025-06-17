package app.jasonhk.hkiit.fifteentwenty.ui.settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

import app.jasonhk.hkiit.fifteentwenty.R;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

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

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey)
    {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat dynamicColorSwitch = findPreference("dynamic_color");
    }
}
