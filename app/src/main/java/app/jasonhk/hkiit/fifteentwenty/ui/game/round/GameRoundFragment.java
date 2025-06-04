package app.jasonhk.hkiit.fifteentwenty.ui.game.round;

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

import app.jasonhk.hkiit.fifteentwenty.R;

public class GameRoundFragment extends Fragment
{
    private String opponent;
    private int round;

    private int guess;

    private boolean isPlayerRound;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_game_round, container, false);
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

        var args = GameRoundFragmentArgs.fromBundle(getArguments());
        opponent = args.getOpponent();
        round = args.getRound();
        guess = args.getGuess();
        isPlayerRound = ((round % 2) != 0);

        Toolbar toolbar = view.findViewById(R.id.fragment_game_round_toolbar);
        toolbar.setTitle(requireActivity().getString(R.string.fragment_game_round_title, round));
        toolbar.setNavigationOnClickListener((v) -> onBackPressed());

        TextView opponentHandsText = view.findViewById(R.id.fragment_game_round_text_hands_opponent);
        opponentHandsText.setText(getString(R.string.fragment_game_round_hands_opponent, opponent));

        TextView guessText = view.findViewById(R.id.fragment_game_round_text_guess);
        guessText.setText(isPlayerRound
                ? getString(R.string.fragment_game_round_guess_player, String.valueOf(guess))
                : getString(R.string.fragment_game_round_guess_opponent, opponent, String.valueOf(guess)));

        HandsView playerHands = view.findViewById(R.id.fragment_game_round_hands_player);
        playerHands.setHands(args.getPlayerHands());

        HandsView opponentHands = view.findViewById(R.id.fragment_game_round_hands_opponent);
        opponentHands.setHands(args.getOpponentHands());

        Button nextRoundButton = view.findViewById(R.id.fragment_game_round_button_next);
        nextRoundButton.setOnClickListener(this::onNextRoundButtonClicked);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() { onBackPressed(); }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    private void onNextRoundButtonClicked(View v)
    {
        HandsView playerHands = requireView().findViewById(R.id.fragment_game_round_hands_player);

        var action = GameRoundFragmentDirections.actionFragmentGameRoundToFragmentGameChoices(opponent, round + 1, playerHands.getHands());
        NavHostFragment.findNavController(this).navigate(action);
    }

    private void onFinishGameRequested(View v)
    {

    }

    private void onBackPressed(View v) { onBackPressed(); }

    private void onBackPressed()
    {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.game_exit_title)
                .setMessage(R.string.game_exit_message)
                .setPositiveButton(R.string.game_exit_yes, (d, w) -> NavHostFragment.findNavController(this).popBackStack())
                .setNegativeButton(R.string.game_exit_no, (d, w) -> { })
                .show();
    }
}
