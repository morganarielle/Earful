package edu.neu.earful;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;

public class NewLevelFragment extends DialogFragment {

    private final Activity parent;
    protected TextView newLevelTV;
    private DifficultyLevel highestLevel = DifficultyLevel.LEVEL1;

    public NewLevelFragment(Activity parent) {
        this.parent = parent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_new_level, null);
        newLevelTV = (TextView) view.findViewById(R.id.newLevelTV);

        builder.setView(view);

        builder.setPositiveButton("TRY NEW LEVEL", (dialog, id) -> {
            Intent i = new Intent(parent, IntervalTrainingActivity.class);
            i.putExtra("difficulty", highestLevel);
            startActivity(i);
        });

        builder.setNegativeButton("DISMISS", (dialog, id) -> {
            Objects.requireNonNull(NewLevelFragment.this.getDialog()).cancel();
        });
        return builder.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.bright_coral));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.dark_blue));
    }

    public void setHighestLevel(DifficultyLevel level) {
        this.highestLevel = level;
    }
}
