package vn.sunasterisk.buoi13_androidnotificaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonNotify;
    private Button mButtonUpdate;
    private Button mButtonCancel;

    private NotificationManager mNotificationManager;

    private static final String PRIMARY_CHANEL_ID = "primary_chanel_id";

    private static final int PRIMARY_NOTIFICATION_ID = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        registerListeners();

        // nhung dien thoai o API cao (26 tro len) thi yeu cau bat buoc
        // phai co mot Notification chanel
        createNotificationChanel();
    }

    private void createNotificationChanel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANEL_ID,
                    "My Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Android Notification");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void registerListeners() {
        mButtonNotify.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }

    private void initComponents() {
        mButtonNotify = findViewById(R.id.button_notify);
        mButtonUpdate = findViewById(R.id.button_update);
        mButtonCancel = findViewById(R.id.button_cancel);

        setButtonStates(true, false, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_notify:
                sendNotification();
                break;
            case R.id.button_update:
                updateNotification();
                break;
            case R.id.button_cancel:
                cancelNotification();
                break;
            default:
                break;
        }
    }

    private void cancelNotification() {
        mNotificationManager.cancel(PRIMARY_NOTIFICATION_ID);

        setButtonStates(true, false, false);
    }

    private void updateNotification() {
        // chuyen anh thanh bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        // lay ra doi tuong builder
        NotificationCompat.Builder builder = getNotificationBuilder();

        //Notification ngoai chua 1 buc anh lon, co the chua mot Message,
        // Chua mot Inbox, tham chi ca 1 layout vs các action
        builder.setStyle(new NotificationCompat
                .BigPictureStyle().bigPicture(bitmap)
                .setBigContentTitle("Hot Girl").setSummaryText("abcdxyze"));

        mNotificationManager.notify(PRIMARY_NOTIFICATION_ID, builder.build());

        setButtonStates(false, false, true);
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = getNotificationBuilder();

        mNotificationManager.notify(PRIMARY_NOTIFICATION_ID, builder.build());

        setButtonStates(false, true, true);
    }

    private NotificationCompat.Builder getNotificationBuilder() {

        Intent intent = new Intent(this, MainActivity.class);

        // Ở đây là gửi intent đến activity nên .getActivity
        // Sau này các bạn có thể gửi đến Broadcast, Servicer, ...
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                PRIMARY_NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_ONE_SHOT
        );

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, PRIMARY_CHANEL_ID);
        builder.setSmallIcon(R.drawable.ic_notifications_paused_black_24dp);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentTitle("Hello You!");
        builder.setContentText("Today is Friday! Do you want to eat chicken with me!");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        return builder;
    }

    private void setButtonStates(boolean notifyState,
                                 boolean updateState,
                                 boolean cancelState) {
        mButtonNotify.setEnabled(notifyState);
        mButtonUpdate.setEnabled(updateState);
        mButtonCancel.setEnabled(cancelState);
    }
}
