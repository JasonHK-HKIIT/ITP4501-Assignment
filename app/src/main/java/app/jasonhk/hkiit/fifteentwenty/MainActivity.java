package app.jasonhk.hkiit.fifteentwenty;

import java.util.Objects;

import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private String PREFERENCE_THEME;
    private String PREFERENCE_DYNAMIC_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        PREFERENCE_THEME = getString(R.string.preference_theme);
        PREFERENCE_DYNAMIC_COLOR = getString(R.string.preference_dynamic_color);

        var preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.registerOnSharedPreferenceChangeListener(this);

        setTheme(Theme.valueOf(preferences.getString(PREFERENCE_THEME, Theme.AUTO.name())));
        setDynamicColors(preferences.getBoolean(PREFERENCE_DYNAMIC_COLOR, false));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, @Nullable String key)
    {
        if (Objects.equals(key, PREFERENCE_THEME))
        {
            setTheme(Theme.valueOf(preferences.getString(PREFERENCE_THEME, Theme.AUTO.name())));
        }
        else if (Objects.equals(key, PREFERENCE_DYNAMIC_COLOR))
        {
            setDynamicColors(preferences.getBoolean(PREFERENCE_DYNAMIC_COLOR, false));
            recreate();
        }
    }

    private void setTheme(Theme theme)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            var manager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
            manager.setApplicationNightMode(switch (theme)
            {
                case AUTO -> UiModeManager.MODE_NIGHT_AUTO;
                case LIGHT -> UiModeManager.MODE_NIGHT_NO;
                case DARK -> UiModeManager.MODE_NIGHT_YES;
            });
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(switch (theme)
            {
                case AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                case LIGHT -> AppCompatDelegate.MODE_NIGHT_NO;
                case DARK -> AppCompatDelegate.MODE_NIGHT_YES;
            });
        }
    }

    private void setDynamicColors(boolean enabled)
    {
        if (enabled)
        {
            DynamicColors.applyToActivityIfAvailable(this);
        }
        else
        {
            DynamicColors.applyToActivityIfAvailable(this, new DynamicColorsOptions.Builder()
                    .setThemeOverlay(R.style.Theme_FifteenTwenty).build());
        }
    }
}
