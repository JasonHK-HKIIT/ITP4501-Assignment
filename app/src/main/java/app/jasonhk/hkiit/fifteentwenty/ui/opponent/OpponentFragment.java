package app.jasonhk.hkiit.fifteentwenty.ui.opponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import app.jasonhk.hkiit.fifteentwenty.R;

public class OpponentFragment extends Fragment
{
    private String[] opponents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_opponent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        opponents = getResources().getStringArray(R.array.opponents);

        ListView opponentsList = view.findViewById(R.id.fragment_opponent_list_opponents);
        opponentsList.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.adapter_opponent, opponents));
        opponentsList.setOnItemClickListener(this::onOpponentsListItemClick);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.fragment_opponent_toolbar_wrapper), (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, 0, insets.right, 0);
            ((ViewGroup.MarginLayoutParams) v.getLayoutParams()).topMargin = insets.top; // Fix the stupid edge-to-edge bug with CollapsingToolbarLayout.
            return WindowInsetsCompat.CONSUMED;
        });

        ViewCompat.setOnApplyWindowInsetsListener(opponentsList, (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, 0, insets.right, insets.bottom + insets.top);
            return windowInsets;
        });
    }

    private void onOpponentsListItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        NavHostFragment.findNavController(this).navigate(OpponentFragmentDirections
                .actionFragmentOpponentToFragmentGameChoices(opponents[position]));
    }
}
