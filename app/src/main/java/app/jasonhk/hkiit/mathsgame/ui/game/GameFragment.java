package app.jasonhk.hkiit.mathsgame.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import app.jasonhk.hkiit.mathsgame.R;

public class GameFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        var navigation = NavHostFragment.findNavController(this);

        Toolbar toolbar = view.findViewById(R.id.fragment_game_toolbar);
        toolbar.setNavigationOnClickListener((v) -> onBackRequested());

        TextView equation = view.findViewById(R.id.fragment_game_text_equation);
        equation.setText(getString(R.string.game_equation, 25, "+", 40));

        Button nextButton = view.findViewById(R.id.fragment_game_button_next);
        nextButton.setOnClickListener((v) -> navigation.navigate(R.id.action_fragment_game_self));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() { onBackRequested(); }
        });
    }

    private void onBackRequested()
    {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.game_exit_title)
                .setMessage(R.string.game_exit_message)
                .setPositiveButton(R.string.game_exit_yes, (d, w) -> NavHostFragment.findNavController(this).popBackStack())
                .setNegativeButton(R.string.game_exit_no, (d, w) -> { })
                .show();
    }
}
