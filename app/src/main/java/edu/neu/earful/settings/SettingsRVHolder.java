package edu.neu.earful.settings;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.neu.earful.R;

public class SettingsRVHolder extends RecyclerView.ViewHolder {
    public TextView settingName;

    public SettingsRVHolder(View itemView, final SettingClickListener listener) {
        super(itemView);
        settingName = itemView.findViewById(R.id.setting_name);

        itemView.setOnClickListener(v -> {
            if (listener != null) {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            }
        });
    }
}
