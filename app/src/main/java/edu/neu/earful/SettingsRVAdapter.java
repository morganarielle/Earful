package edu.neu.earful;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingsRVAdapter extends RecyclerView.Adapter<SettingsRVHolder>{

    private final ArrayList<SettingCard> settings;
    private SettingClickListener listener;

    public SettingsRVAdapter(ArrayList<SettingCard> settings) {
        this.settings = settings;
    }

    public void setSettingClickListener(SettingClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SettingsRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_setting, parent, false);
        return new SettingsRVHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsRVHolder holder, int position) {
        SettingCard currentSetting = settings.get(position);
        holder.settingName.setText(currentSetting.getSettingName());
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

}
