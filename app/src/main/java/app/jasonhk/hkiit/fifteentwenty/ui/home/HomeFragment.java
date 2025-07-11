package app.jasonhk.hkiit.fifteentwenty.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import app.jasonhk.hkiit.fifteentwenty.R;

public class HomeFragment extends Fragment
{
    private NavController navigation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        navigation = NavHostFragment.findNavController(this);

        Button playButton = view.findViewById(R.id.button_play);
        playButton.setOnClickListener((v) -> navigation.navigate(R.id.action_fragment_home_to_fragment_opponent));

        Button recordsButton = view.findViewById(R.id.button_records);
        recordsButton.setOnClickListener((v) -> navigation.navigate(R.id.action_fragment_home_to_fragment_records));

        Toolbar toolbar = view.findViewById(R.id.fragment_home_toolbar);
        toolbar.setOnMenuItemClickListener(this::onToolbarMenuItemClick);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private boolean onToolbarMenuItemClick(MenuItem item)
    {
        var id = item.getItemId();
        if (id == R.id.fragment_home_toolbar_menu_settings)
        {
            navigation.navigate(R.id.action_fragment_home_to_fragment_settings);
            return true;
        }
        else if (id == R.id.fragment_home_toolbar_menu_exit)
        {
            requireActivity().finishAffinity();
            return true;
        }

        return false;
    }
}
