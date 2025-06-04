package app.jasonhk.hkiit.fifteentwenty.ui.game.choices;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
    private static final String OPPONENT_CHOICES_KEY = "OPPONENT_CHOICES_KEY";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private OpponentChoices opponentChoices;

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

        if (savedInstanceState != null)
        {
            opponentChoices = savedInstanceState.getParcelable(OPPONENT_CHOICES_KEY);
        }

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        var navigation = NavHostFragment.findNavController(this);

        var args = GameChoicesFragmentArgs.fromBundle(getArguments());
        var opponent = args.getOpponent();
        var round = args.getRound();

        Toolbar toolbar = view.findViewById(R.id.fragment_game_toolbar);
        toolbar.setTitle(requireActivity().getString(R.string.fragment_game_choices_title, round));
        toolbar.setNavigationOnClickListener((v) -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        MaterialButton handLeftButton = view.findViewById(R.id.fragment_game_choices_button_hand_left);
        MaterialButton handRightButton = view.findViewById(R.id.fragment_game_choices_button_hand_right);

        var hands = args.getHands();
        if (hands != null)
        {
            handLeftButton.setChecked(hands.left());
            handRightButton.setChecked(hands.right());
        }

        Button confirmButton = view.findViewById(R.id.fragment_game_button_confirm);
        confirmButton.setOnClickListener((v) ->
        {
            navigation.navigate(GameChoicesFragmentDirections.actionFragmentGameChoicesToFragmentGameRound(
                    opponent,
                    round,
                    new Hands(handLeftButton.isChecked(), handRightButton.isChecked()),
                    opponentChoices.toHands(),
                    5));
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
        if (opponentChoices != null) { return; }

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
                Log.d(GameChoicesFragment.class.getName(), opponentChoices.toString());
            }
            catch (IOException e)
            {
                handler.post(() -> Toast.makeText(
                        requireContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG).show());
                return;
            }

            handler.post(() ->
            {
                Button confirmButton = requireView().findViewById(R.id.fragment_game_button_confirm);
                confirmButton.setEnabled(true);
            });
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
