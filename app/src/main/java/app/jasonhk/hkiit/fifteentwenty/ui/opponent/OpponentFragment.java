package app.jasonhk.hkiit.fifteentwenty.ui.opponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import app.jasonhk.hkiit.fifteentwenty.R;

public class OpponentFragment extends Fragment
{
    private NavController navigation;

    /**
     * List of opponents.
     */
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

        var adapter = new OpponentAdapter(opponents);
        adapter.setOnItemClickListener(this::onOpponentsListItemClick);

        RecyclerView opponentsList = view.findViewById(R.id.fragment_opponent_list_opponents);
        opponentsList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        opponentsList.setAdapter(adapter);

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

    private void onOpponentsListItemClick(OpponentAdapter adapter, View view, int position)
    {
        navigation.navigate(OpponentFragmentDirections.actionFragmentOpponentToFragmentGameChoices(
                opponents[position]));
    }
}
