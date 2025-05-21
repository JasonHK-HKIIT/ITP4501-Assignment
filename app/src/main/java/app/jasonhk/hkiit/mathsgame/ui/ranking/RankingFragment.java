package app.jasonhk.hkiit.mathsgame.ui.ranking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.jasonhk.hkiit.mathsgame.R;
import app.jasonhk.hkiit.mathsgame.model.RankingItem;

public class RankingFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.fragment_ranking_toolbar_wrapper), (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, 0, insets.right, 0);
            ((MarginLayoutParams) v.getLayoutParams()).topMargin = insets.top; // Fix the stupid edge-to-edge bug with CollapsingToolbarLayout.
            return WindowInsetsCompat.CONSUMED;
        });

        var ranking = new ArrayList<RankingItem>();
        ranking.add(new RankingItem("A", 5, 5));
        ranking.add(new RankingItem("B", 5, 5));
        ranking.add(new RankingItem("C", 5, 5));
        ranking.add(new RankingItem("D", 5, 5));
        ranking.add(new RankingItem("E", 5, 5));
        ranking.add(new RankingItem("F", 5, 5));
        ranking.add(new RankingItem("A", 5, 5));
        ranking.add(new RankingItem("B", 5, 5));
        ranking.add(new RankingItem("C", 5, 5));
        ranking.add(new RankingItem("D", 5, 5));
        ranking.add(new RankingItem("E", 5, 5));
        ranking.add(new RankingItem("F", 5, 5));

        var adapter = new RankingAdapter(ranking);

        RecyclerView list = view.findViewById(R.id.fragment_ranking_list_ranking);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));
        list.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(list, (v, windowInsets) ->
        {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, 0, insets.right, insets.bottom + insets.top);
            return windowInsets;
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}
