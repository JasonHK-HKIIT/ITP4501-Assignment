package app.jasonhk.hkiit.fifteentwenty.ui.opponent;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import app.jasonhk.hkiit.fifteentwenty.R;

public class OpponentFragment extends Fragment
{
    private NavController navigation;

    /** List of opponents. */
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

        navigation = NavHostFragment.findNavController(this);

        // Retrieve opponents list
        opponents = getResources().getStringArray(R.array.opponents);

        Toolbar toolbar = view.findViewById(R.id.fragment_opponent_toolbar);
        toolbar.setNavigationOnClickListener((v) -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        ListView opponentsList = view.findViewById(R.id.fragment_opponent_list_opponents);
        opponentsList.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.adapter_opponent, opponents));
        opponentsList.setOnItemClickListener(this::onOpponentsListItemClick);

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.fragment_opponent_toolbar_wrapper), (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, 0);
            return windowInsets;
        });
        ViewCompat.setOnApplyWindowInsetsListener(opponentsList, (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, 0, insets.right, insets.bottom);
            return windowInsets;
        });
    }

    private void onOpponentsListItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        navigation.navigate(OpponentFragmentDirections.actionFragmentOpponentToFragmentGameChoices(
                opponents[position]));
    }
}
