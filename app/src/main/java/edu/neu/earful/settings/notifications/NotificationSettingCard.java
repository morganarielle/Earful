package edu.neu.earful.settings.notifications;

import edu.neu.earful.settings.SettingCard;
import edu.neu.earful.settings.SettingsActivity;

public class NotificationSettingCard extends SettingCard {
    private String settingName;
    private Runnable action;

    public NotificationSettingCard(String settingName, Runnable action) {
        super(settingName, action);
        this.settingName = settingName;
        this.action = action;
    }

    public String getSettingName() {
        return settingName;
    }

    public Runnable getAction() {
        return action;
    }

    @Override
    public void onItemClick(int position) {
        settingName = SettingsActivity.mPrefs.getString("notifications", "true").equals("true") ?
                "Toggle Notifications Off" :
                "Toggle Notifications On";
        action.run();
    }
}
