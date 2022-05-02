package edu.neu.earful.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.neu.earful.menu.CreateAccountActivity;
import edu.neu.earful.menu.MainActivity;
import edu.neu.earful.settings.notifications.NotificationSettingCard;
import edu.neu.earful.settings.notifications.NotificationReceiver;
import edu.neu.earful.R;

public class SettingsActivity extends AppCompatActivity {
    private final ArrayList<SettingCard> settings = new ArrayList<>();
    private RecyclerView settingsRV;
    private SettingsRVAdapter settingsRVAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static SharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsRV = (RecyclerView) findViewById(R.id.settingsRV);
        Log.v("TAG", "onCreate settingsRV: " + settingsRV);
        mPrefs = getSharedPreferences("notifications", 0);
        createRecyclerView();
        initializeSettings();
    }

    private void initializeSettings() {
        int position = 0;
        Runnable logOutAction = () -> {
            FirebaseAuth.getInstance().signOut();
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity2Intent);
        };
        SettingCard logOutSetting = new SettingCard("Log Out", logOutAction);
        addSetting(logOutSetting, 0);
        position++;

        Runnable createNewAccountAction = () -> {
            FirebaseAuth.getInstance().signOut();
            Intent activity2Intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
            startActivity(activity2Intent);
        };
        SettingCard createNewAccountSetting = new SettingCard("Create New Account", createNewAccountAction);
        addSetting(createNewAccountSetting, position);
        position++;

        Runnable toggleNotificationsAction = () -> {
            Intent notifyIntent = new Intent(this, NotificationReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (this, 3, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            boolean notificationsOn = mPrefs.getString("notifications", "true").equals("true");
            SharedPreferences.Editor mEditor = mPrefs.edit();
            if (notificationsOn) {
                stopService(notifyIntent);
                alarmManager.cancel(pendingIntent);
                mEditor.putString("notifications", "false").apply();
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                        1000 * 60 * 60 * 24, pendingIntent);
                mEditor.putString("notifications", "true").apply();
            }
        };
        String notificationSettingTitle = mPrefs.getString("notifications", "true").equals("true") ?
                "Toggle Notifications Off" :
                "Toggle Notifications On";
        SettingCard notificationSetting = new NotificationSettingCard(notificationSettingTitle, toggleNotificationsAction);
        addSetting(notificationSetting, position);
    }

    private void createRecyclerView() {
        Log.v("TAG", "createRecyclerView settingsRV: " + settingsRV);
        settingsRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        settingsRVAdapter = new SettingsRVAdapter(settings);
        SettingClickListener settingClickListener = position -> {
            settings.get(position).onItemClick(position);
            settingsRVAdapter.notifyItemChanged(position);
        };
        settingsRVAdapter.setSettingClickListener(settingClickListener);
        settingsRV.setAdapter(settingsRVAdapter);
        settingsRV.setLayoutManager(layoutManager);
    }

    private void addSetting(SettingCard setting, int position) {
        settings.add(position, setting);
        settingsRVAdapter.notifyItemInserted(position);
    }
}
