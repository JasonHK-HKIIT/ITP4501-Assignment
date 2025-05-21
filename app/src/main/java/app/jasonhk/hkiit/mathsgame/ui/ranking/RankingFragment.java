package app.jasonhk.hkiit.mathsgame.ui.ranking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

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

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) ->
        {
            var bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
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

        var adapter = new RankingAdapter(requireContext(), ranking);

        ListView list = view.findViewById(R.id.fragment_ranking_list_ranking);
        list.setAdapter(adapter);

//
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}
