package app.jasonhk.hkiit.fifteentwenty.ui.game.choices;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.model.Hands;
import app.jasonhk.hkiit.fifteentwenty.model.OpponentChoices;
import app.jasonhk.hkiit.fifteentwenty.model.Side;

public class GameChoicesFragment extends Fragment
{
    private static final String OPPONENT_CHOICES_KEY = "OPPONENT_CHOICES_KEY";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private final ObjectMapper mapper = new ObjectMapper();

    private NavController navigation;

    private MaterialButton handLeftButton;
    private MaterialButton handRightButton;

    private Slider guessSlider;

    private MaterialButton confirmButton;

    /**
     * The opponent's name.
     */
    private String opponent;

    /**
     * The current game round.
     */
    private int round;

    /**
     * The opponent's choices.
     */
    private OpponentChoices opponentChoices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_game_choices, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Restore opponent choices (if exist)
        if (savedInstanceState != null)
        {
            opponentChoices = savedInstanceState.getParcelable(OPPONENT_CHOICES_KEY);
        }

        navigation = NavHostFragment.findNavController(this);

        // Retrieve game states
        var args = GameChoicesFragmentArgs.fromBundle(getArguments());
        opponent = args.getOpponent();
        round = args.getRound();

        Toolbar toolbar = view.findViewById(R.id.fragment_game_choices_toolbar);
        toolbar.setTitle(requireActivity().getString(R.string.fragment_game_choices_title, round));
        toolbar.setNavigationOnClickListener((v) -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        handLeftButton = view.findViewById(R.id.fragment_game_choices_button_hand_left);
        handRightButton = view.findViewById(R.id.fragment_game_choices_button_hand_right);
        guessSlider = view.findViewById(R.id.fragment_game_choices_slider_guess);
        confirmButton = view.findViewById(R.id.fragment_game_choices_button_confirm);

        // Restore hands state from previous round
        var hands = args.getHands();
        if (hands != null)
        {
            handLeftButton.setChecked(hands.left());
            handRightButton.setChecked(hands.right());
        }

        var guessSection = view.findViewById(R.id.fragment_game_choices_section_guess);
        guessSection.setVisibility((Side.fromRound(round) == Side.PLAYER) ? View.VISIBLE : View.INVISIBLE);

        confirmButton.setOnClickListener(this::onConfirmButtonClick);

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() { onBackRequested(); }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        fetchOpponentChoices();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable(OPPONENT_CHOICES_KEY, opponentChoices);
    }

    private void fetchOpponentChoices()
    {
        if (opponentChoices != null)
        {
            confirmButton.setEnabled(true);
            return;
        }

        var executor = Executors.newSingleThreadExecutor();
        var handler = new Handler(Looper.getMainLooper());

        executor.execute(() ->
        {
            var request = new Request.Builder()
                    .url("https://assign-mobileasignment-ihudikcgpf.cn-hongkong.fcapp.run/")
                    .build();

            try (var response = client.newCall(request).execute())
            {
                if (!response.isSuccessful())
                {
                    handler.post(() -> Snackbar.make(
                                    requireView(),
                                    response.message(),
                                    Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.snackbar_action_retry, (v) -> fetchOpponentChoices())
                            .show());
                    return;
                }

                opponentChoices = mapper.readValue(response.body().byteStream(), OpponentChoices.class);
            }
            catch (UnknownHostException ex)
            {
                handler.post(() -> Snackbar.make(
                                requireView(),
                                R.string.fragment_game_choices_error_unknown_host,
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_action_retry, (v) -> fetchOpponentChoices())
                        .show());
                return;
            }
            catch (DatabindException ex)
            {
                handler.post(() -> Snackbar.make(
                                requireView(),
                                R.string.fragment_game_choices_error_databind,
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_action_retry, (v) -> fetchOpponentChoices())
                        .show());
                return;
            }
            catch (IOException ex)
            {
                Log.e(GameChoicesFragment.class.getName(), ex.toString());
                handler.post(() -> Snackbar.make(
                                requireView(),
                                String.valueOf(ex.getMessage()),
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbar_action_retry, (v) -> fetchOpponentChoices())
                        .show());
                return;
            }

            handler.post(() -> confirmButton.setEnabled(true));
        });
    }

    private void onConfirmButtonClick(View view)
    {
        var playerHands = new Hands(handLeftButton.isChecked(), handRightButton.isChecked());
        var guess = (Side.fromRound(round) == Side.PLAYER) ? (int) guessSlider.getValue() : opponentChoices.getGuess();

        navigation.navigate(GameChoicesFragmentDirections.actionFragmentGameChoicesToFragmentGameRound(
                opponent, round, playerHands, opponentChoices.toHands(), guess));
    }

    private void onBackRequested()
    {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.game_exit_title)
                .setMessage(R.string.game_exit_message)
                .setPositiveButton(R.string.game_exit_yes, (d, w) -> NavHostFragment.findNavController(this).navigateUp())
                .setNegativeButton(R.string.game_exit_no, (d, w) -> { })
                .show();
    }
}
