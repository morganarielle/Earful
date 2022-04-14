package edu.neu.earful;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class IntervalGVAdapter extends ArrayAdapter<IntervalCard> {
    public IntervalGVAdapter(@NonNull Context context, ArrayList<IntervalCard> intervalCards) {
        super(context, 0, intervalCards);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_interval, parent, false);
        }
        IntervalCard intervalCard = getItem(position);
        TextView intervalTV = convertView.findViewById(R.id.intervalTV);
        intervalTV.setText(IntervalCard.getIntervalDisplayText(intervalCard.getInterval()));
        return convertView;
    }
}

