package app.jasonhk.hkiit.fifteentwenty;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private String PREFERENCE_LANGUAGE;
    private String PREFERENCE_LANGUAGE_DEFAULT;
    private String PREFERENCE_THEME;
    private String PREFERENCE_DYNAMIC_COLOR;

    private List<String> languages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        PREFERENCE_LANGUAGE = getString(R.string.preference_language);
        PREFERENCE_LANGUAGE_DEFAULT = getString(R.string.preference_language_default);
        PREFERENCE_THEME = getString(R.string.preference_theme);
        PREFERENCE_DYNAMIC_COLOR = getString(R.string.preference_dynamic_color);

        languages = List.of(getResources().getStringArray(R.array.preference_language_values));

        Log.d("getApplicationLocales", AppCompatDelegate.getApplicationLocales().toLanguageTags());

        var preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            var editor = preferences.edit();

            var locales = AppCompatDelegate.getApplicationLocales();
            if (locales.isEmpty())
            {
                editor.putString(PREFERENCE_LANGUAGE, PREFERENCE_LANGUAGE_DEFAULT);
            }
            else
            {
                Log.d("locales.getFirstMatch", locales.getFirstMatch(getResources().getStringArray(R.array.preference_language_values)).toLanguageTag());

                for (var i = 0; i < locales.size(); i++)
                {
                    var locale = new Locale.Builder()
                            .setLanguage(locales.get(i).getLanguage())
                            .setRegion(locales.get(i).getCountry())
                            .build()
                            .toLanguageTag();

                    if (languages.contains(locale))
                    {
                        editor.putString(PREFERENCE_LANGUAGE, locale);
                    }
                }
            }

            editor.apply();
        }

        setLanguage(preferences.getString(PREFERENCE_LANGUAGE, PREFERENCE_LANGUAGE_DEFAULT));
        setTheme(Theme.valueOf(preferences.getString(PREFERENCE_THEME, Theme.AUTO.name())));
        setDynamicColors(preferences.getBoolean(PREFERENCE_DYNAMIC_COLOR, false));

        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, @Nullable String key)
    {
        if (Objects.equals(key, PREFERENCE_LANGUAGE))
        {
            setLanguage(preferences.getString(PREFERENCE_LANGUAGE, PREFERENCE_LANGUAGE_DEFAULT));
        }
        else if (Objects.equals(key, PREFERENCE_THEME))
        {
            setTheme(Theme.valueOf(preferences.getString(PREFERENCE_THEME, Theme.AUTO.name())));
        }
        else if (Objects.equals(key, PREFERENCE_DYNAMIC_COLOR))
        {
            setDynamicColors(preferences.getBoolean(PREFERENCE_DYNAMIC_COLOR, false));
            recreate();
        }
    }

    private void setLanguage(@NonNull String language)
    {
        var locales = language.equals(PREFERENCE_LANGUAGE_DEFAULT)
                ? LocaleListCompat.getEmptyLocaleList() : LocaleListCompat.forLanguageTags(language);
        setLanguage(locales);
    }

    private void setLanguage(LocaleListCompat locales)
    {
        AppCompatDelegate.setApplicationLocales(locales);
    }

    private void setTheme(Theme theme)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            var manager = getSystemService(UiModeManager.class);
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
