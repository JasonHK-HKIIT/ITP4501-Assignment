package app.jasonhk.hkiit.fifteentwenty;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        var preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.registerOnSharedPreferenceChangeListener(this);

        setDynamicColors(preferences.getBoolean("dynamic_color", false));

//        DynamicColors.applyToActivityIfAvailable(this);
//        DynamicColors.applyToActivityIfAvailable(this, new DynamicColorsOptions.Builder().setThemeOverlay(R.style.Theme_FifteenTwenty).build());

        var screen = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_fragment_screen);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, @Nullable String key)
    {
        if (Objects.equals(key, "dynamic_color"))
        {
            setDynamicColors(preferences.getBoolean("dynamic_color", false));
            recreate();
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
