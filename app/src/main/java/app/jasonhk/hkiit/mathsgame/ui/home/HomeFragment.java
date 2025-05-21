package app.jasonhk.hkiit.mathsgame.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import app.jasonhk.hkiit.mathsgame.R;

public class HomeFragment extends Fragment
{
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

        var navigation = NavHostFragment.findNavController(this);

        Button playButton = view.findViewById(R.id.button_play);
        playButton.setOnClickListener((v) -> navigation.navigate(R.id.action_fragment_home_to_fragment_game));

        Button rankingButton = view.findViewById(R.id.button_ranking);
        rankingButton.setOnClickListener((v) -> navigation.navigate(R.id.action_fragment_home_to_fragment_ranking));

        Button recordsButton = view.findViewById(R.id.button_records);
        recordsButton.setOnClickListener((v) -> navigation.navigate(R.id.action_fragment_home_to_fragment_records));
    }
}
