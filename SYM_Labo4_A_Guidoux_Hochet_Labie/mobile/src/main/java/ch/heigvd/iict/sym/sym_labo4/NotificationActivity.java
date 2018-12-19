/*
 * File         : BeaconActivity.java
 * Project      : SYM_Labo3
 * Authors      : Hochet Guillaume 16 décembre 2018
 *                Labie Marc 16 décembre 2018
 *                Guidoux Vincent 16 décembre 2018
 *
 * Description  : list the various iBeacons nearby. You will see for each the rssi (strength of the
 *                received signal), the major number and the minor number in an activity. The
 *                displayed list is updated regularly.
 *
 * Sources      : https://developer.android.com/training/wearables/notifications/creating
 *                https://developer.android.com/training/notify-user/channels
 */
package ch.heigvd.iict.sym.sym_labo4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ch.heigvd.iict.sym.wearcommon.Constants;

public class NotificationActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1; //code to use for the notification id
    private static final int SIMPLE_NOTIFICATION_ID = 1234;
    private static final String CHANNEL_ID = "labo41";

    private Button notificationBtnDisplayNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if (getIntent() != null)
            onNewIntent(getIntent());

        notificationBtnDisplayNotification = findViewById(R.id.notification_btn_display_notification);

        createNotificationChannel();

        notificationBtnDisplayNotification.setOnClickListener(v -> {

                // Build intent for notification content
                PendingIntent viewPendingIntent = createPendingIntent(SIMPLE_NOTIFICATION_ID, "Des travaux se trouvent sur votre trajet");

                // Notification channel ID is ignored for Android 7.1.1
                // (API level 25) and lower.
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_directions_bike_black_18dp)
                                .setContentTitle(getString(R.string.ic_simple_notification_title))
                                .setContentText(getString(R.string.ic_simple_notification_description))
                                .setAutoCancel(true)
                                .setContentIntent(viewPendingIntent);

                // Get an instance of the NotificationManager service
                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(getApplicationContext());

                // Issue the notification with notification manager.
                notificationManager.notify(SIMPLE_NOTIFICATION_ID, notificationBuilder.build());

        });
    }

    /* A IMPLEMENTER */

    /*
     *  Code fourni pour les PendingIntent
     */

    /*
     *  Method called by system when a new Intent is received
     *  Display a toast with a message if the Intent is generated by
     *  createPendingIntent method.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null) return;
        if (Constants.MY_PENDING_INTENT_ACTION.equals(intent.getAction())) {
            Toast.makeText(this, "" + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
            NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID); //we close the notification
        }
    }

    /**
     * Method used to create a PendingIntent with the specified message
     * The intent will start a new activity Instance or bring to front an existing one.
     * See parentActivityName and launchMode options in Manifest
     * See https://developer.android.com/training/notify-user/navigation.html for TaskStackBuilder
     *
     * @param requestCode The request code
     * @param message     The message
     * @return The pending Intent
     */
    private PendingIntent createPendingIntent(int requestCode, String message) {
        Intent myIntent = new Intent(NotificationActivity.this, NotificationActivity.class);
        myIntent.setAction(Constants.MY_PENDING_INTENT_ACTION);
        myIntent.putExtra("msg", message);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(myIntent);

        return stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

}
