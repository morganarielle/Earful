package edu.neu.earful;

public class SettingCard implements SettingClickListener {

    private String settingName;
    private Runnable action;

    public SettingCard(String settingName, Runnable action) {
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
        action.run();
    }
}
