package app.jasonhk.hkiit.fifteentwenty.ui.game.choices;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.DataInput;
import java.io.IOException;
import java.util.concurrent.Executors;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.model.Hands;
import app.jasonhk.hkiit.fifteentwenty.model.OpponentChoices;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GameChoicesFragment extends Fragment
{
    private static final String IS_HAND_LEFT_CHECKED_KEY = "IS_HAND_LEFT_CHECKED_KEY";
    private static final String IS_HAND_RIGHT_CHECKED_KEY = "IS_HAND_RIGHT_CHECKED_KEY";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private String opponent;
    private int round;

    private OpponentChoices opponentChoices;

    private boolean isHandLeftChecked = false;
    private boolean isHandRightChecked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_game_choices, container, false);
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

        var args = GameChoicesFragmentArgs.fromBundle(getArguments());
        opponent = args.getOpponent();
        round = args.getRound();

        Toolbar toolbar = view.findViewById(R.id.fragment_game_toolbar);
        toolbar.setTitle(requireActivity().getString(R.string.fragment_game_choices_title, round));
        toolbar.setNavigationOnClickListener((v) -> onBackRequested());

        MaterialButton handLeftButton = view.findViewById(R.id.fragment_game_choices_button_hand_left);
        handLeftButton.addOnCheckedChangeListener((b, isChecked) -> isHandLeftChecked = isChecked);

        MaterialButton handRightButton = view.findViewById(R.id.fragment_game_choices_button_hand_right);
        handRightButton.addOnCheckedChangeListener((b, isChecked) -> isHandRightChecked = isChecked);

        Button confirmButton = view.findViewById(R.id.fragment_game_button_confirm);
        confirmButton.setOnClickListener(this::onConfirmedChoices);

    }

    @Override
    public void onStart()
    {
        super.onStart();

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() { onBackRequested(); }
        });

        fetchOpponentChoices();
    }

    private void fetchOpponentChoices()
    {
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
                    handler.post(() -> Toast.makeText(
                            requireContext(),
                            getString(R.string.fragment_game_choices_toast_fetch_opponent_failed, response.message()),
                            Toast.LENGTH_LONG).show());
                    return;
                }

                opponentChoices = mapper.readValue(response.body().byteStream(), OpponentChoices.class);
            }
            catch (IOException e)
            {
                handler.post(() -> Toast.makeText(
                        requireContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG).show());
            }

            handler.post(() ->
            {
                Button confirmButton = requireView().findViewById(R.id.fragment_game_button_confirm);
                confirmButton.setEnabled(true);
            });
        });
    }

    private void onConfirmedChoices(@NonNull View v)
    {
        var action = GameChoicesFragmentDirections.actionFragmentGameChoicesToFragmentGameRound(
                opponent,
                round,
                new Hands(isHandLeftChecked, isHandRightChecked),
                opponentChoices.toHands(),
                5);

        NavHostFragment.findNavController(this).navigate(action);
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
