package app.jasonhk.hkiit.fifteentwenty.ui.game.round;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.database.RecordsDatabase;
import app.jasonhk.hkiit.fifteentwenty.entity.GameRecord;
import app.jasonhk.hkiit.fifteentwenty.model.Side;

public class GameRoundFragment extends Fragment
{
    private static final String RECORD_ID_KEY = "RECORD_ID_KEY";

    private RecordsDatabase database;

    private NavController navigation;

    private HandsView playerHandsDisplay;

    private MaterialButton finishButton;

    private String opponent;
    private int round;

    /**
     * {@code true} when the game was finished.
     */
    private boolean isGameFinished = false;

    /**
     * If {@link #isGameFinished} is {@code true}, the fragment will save a record of the game
     * result and store the record ID here to ensure no duplicated game records.
     **/
    private long recordId = GameRecord.NO_RECORD;

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

        if (savedInstanceState != null)
        {
            recordId = savedInstanceState.getLong(RECORD_ID_KEY);
        }

        database = Room.databaseBuilder(requireContext(), RecordsDatabase.class, RecordsDatabase.FILENAME).build();

        navigation = NavHostFragment.findNavController(this);

        var args = GameRoundFragmentArgs.fromBundle(getArguments());
        opponent = args.getOpponent();
        round = args.getRound();

        var side = Side.fromRound(round);

        Toolbar toolbar = view.findViewById(R.id.fragment_game_round_toolbar);
        toolbar.setTitle(requireActivity().getString(R.string.fragment_game_round_title, round));
        toolbar.setNavigationOnClickListener((v) -> onBackPressed());

        TextView opponentHandsText = view.findViewById(R.id.fragment_game_round_text_hands_opponent);
        opponentHandsText.setText(getString(R.string.fragment_game_round_hands_opponent, opponent));

        var playerHands = args.getPlayerHands();
        var opponentHands = args.getOpponentHands();

        HandsView opponentHandsDisplay = view.findViewById(R.id.fragment_game_round_hands_opponent);
        opponentHandsDisplay.setHands(opponentHands);
        playerHandsDisplay = view.findViewById(R.id.fragment_game_round_hands_player);
        playerHandsDisplay.setHands(playerHands);

        var guess = args.getGuess();
        var answer = playerHands.getValue() + opponentHands.getValue();

        TextView guessText = view.findViewById(R.id.fragment_game_round_text_guess);
        guessText.setText((side == Side.PLAYER)
                ? getString(R.string.fragment_game_round_guess_player, String.valueOf(guess))
                : getString(R.string.fragment_game_round_guess_opponent, opponent, String.valueOf(guess)));

        MaterialButton nextButton = view.findViewById(R.id.fragment_game_round_button_next);
        finishButton = view.findViewById(R.id.fragment_game_round_button_finish);

        nextButton.setOnClickListener(this::onNextButtonClick);
        finishButton.setOnClickListener(this::onFinishButtonClick);

        if (guess == answer)
        {
            isGameFinished = true;

            TextView resultText = view.findViewById(R.id.fragment_game_round_text_result);
            resultText.setText((side == Side.PLAYER) ? R.string.fragment_game_round_result_win : R.string.fragment_game_round_result_lose);
            resultText.setVisibility(View.VISIBLE);

            nextButton.setVisibility(View.GONE);
            finishButton.setVisibility(View.VISIBLE);

            saveGameRecord();
        }
        else
        {
            nextButton.setVisibility(View.VISIBLE);
            finishButton.setVisibility(View.GONE);
        }

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() { onBackPressed(); }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putLong(RECORD_ID_KEY, recordId);
    }

    private void saveGameRecord()
    {
        if (recordId != GameRecord.NO_RECORD)
        {
            finishButton.setEnabled(true);
            return;
        }

        var executor = Executors.newSingleThreadExecutor();
        var handler = new Handler(Looper.getMainLooper());

        executor.execute(() ->
        {
            var record = new GameRecord();
            record.timestamp = LocalDateTime.now();
            record.opponent = opponent;
            record.rounds = round;
            record.isPlayerWon = (Side.fromRound(round) == Side.PLAYER);

            database.gameRecordDao().insertAll(record);
            recordId = record.id;

            handler.post(() -> finishButton.setEnabled(true));
        });
    }

    private void onNextButtonClick(View v)
    {
        navigation.navigate(GameRoundFragmentDirections.actionFragmentGameRoundToFragmentGameChoices(
                opponent, round + 1, playerHandsDisplay.getHands()));
    }

    private void onFinishButtonClick(View v)
    {
        navigation.navigateUp();
    }

    private void onBackPressed(View v) { onBackPressed(); }

    private void onBackPressed()
    {

        if (isGameFinished)
        {
            navigation.navigateUp();
        }
        else
        {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.game_exit_title)
                    .setMessage(R.string.game_exit_message)
                    .setPositiveButton(R.string.game_exit_yes, (d, w) -> NavHostFragment.findNavController(this).popBackStack())
                    .setNegativeButton(R.string.game_exit_no, (d, w) -> { })
                    .show();
        }
    }
}
