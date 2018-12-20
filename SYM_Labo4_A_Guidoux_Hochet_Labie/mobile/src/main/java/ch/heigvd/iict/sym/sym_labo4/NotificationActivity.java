/*
 * File         : NotificationActivity.java
 * Project      : SYM_Labo4_A_Guidoux_Hochet_Labie
 * Authors      : Hochet Guillaume 20 janvier 2019
 *                Labie Marc 20 janvier 2019
 *                Guidoux Vincent 20 janvier 2019
 *
 * Description  :
 *
 * Sources      : https://developer.android.com/training/wearables/notifications/creating
 *                https://developer.android.com/training/notify-user/channels
 */
package ch.heigvd.iict.sym.sym_labo4;

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
import android.widget.Button;
import android.widget.Toast;

import ch.heigvd.iict.sym.wearcommon.Constants;

public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "labo4";

    private static final String EMOJI_EMMERGENCY = "\uD83D\uDE91";
    private static final String EMOJI_OUPS = "\uD83D\uDE44";

    private static final int SIMPLE_NOTIFICATION_ID = 1234;

    private static final int ACTIONS_NOTIFICATION_ID = 2340;
    private static final int ACTIONS_NOTIFICATION_ID_YES = 2341;
    private static final int ACTIONS_NOTIFICATION_ID_NO = 2342;
    private static final int ACTIONS_NOTIFICATION_ID_MAYBE = 2343;

    private static final int WEARABLE_NOTIFICATION_ID = 3450;
    private static final int WEARABLE_NOTIFICATION_ID_FINISH = 3451;
    private static final int WEARABLE_NOTIFICATION_ID_CONTINUE = 3452;
    private static final int WEARABLE_NOTIFICATION_ID_CALL = 3453;

    private Button notificationBtnDisplaySimpleNotification = null;
    private Button notificationBtnDisplayActionsNotification = null;
    private Button notificationBtnDisplayWearableNotification = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if (getIntent() != null)
            onNewIntent(getIntent());

        notificationBtnDisplaySimpleNotification = findViewById(R.id.btn_display_simple_notification);
        notificationBtnDisplayActionsNotification = findViewById(R.id.btn_display_actions_notification);
        notificationBtnDisplayWearableNotification = findViewById(R.id.btn_display_wearable_notification);

        createNotificationChannel();

        notificationBtnDisplaySimpleNotification.setOnClickListener(v -> {

            // Build intent for notification content
            PendingIntent viewPendingIntent = createPendingIntentSimple(SIMPLE_NOTIFICATION_ID, "Des travaux se trouvent sur votre trajet");

            // Notification channel ID is ignored for Android 7.1.1
            // (API level 25) and lower.
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_directions_bike_black_18dp)
                            .setContentTitle(getString(R.string.ic_simple_notification_title))
                            .setContentText(getString(R.string.ic_simple_notification_description))
                            .setContentIntent(viewPendingIntent);

            // Get an instance of the NotificationManager service
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getApplicationContext());

            // Issue the notification with notification manager.
            notificationManager.notify(SIMPLE_NOTIFICATION_ID, notificationBuilder.build());

        });

        notificationBtnDisplayActionsNotification.setOnClickListener(v -> {

            // Build intent for notification content
            PendingIntent oui = createPendingIntentActions(ACTIONS_NOTIFICATION_ID_YES, "regardez dans votre jeans, dans la machine à laver");
            PendingIntent non = createPendingIntentActions(ACTIONS_NOTIFICATION_ID_NO, "Bravo !!");
            PendingIntent pe = createPendingIntentActions(ACTIONS_NOTIFICATION_ID_MAYBE, "Ah ça je savais !");

            NotificationCompat.Action ouiAc = new NotificationCompat.Action(R.drawable.ic_search_black_18dp, "OUI", oui);
            NotificationCompat.Action nonAc = new NotificationCompat.Action(R.drawable.ic_key_black_18dp, "NON", non);
            NotificationCompat.Action peAc = new NotificationCompat.Action(R.drawable.ic_oups_black_18dp, EMOJI_OUPS, pe);
            // Notification channel ID is ignored for Android 7.1.1
            // (API level 25) and lower.
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_directions_car_black_18dp)
                    .setContentTitle(getString(R.string.ic_actions_notification_title))
                    .setContentText(getString(R.string.ic_actions_notification_description))
                    .addAction(ouiAc)
                    .addAction(nonAc)
                    .addAction(peAc);

            // Get an instance of the NotificationManager service
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getApplicationContext());
            // Issue the notification with notification manager.
            notificationManager.notify(ACTIONS_NOTIFICATION_ID, notificationBuilder.build());
        });

        notificationBtnDisplayWearableNotification.setOnClickListener(v -> {

            // Build intent for notification content
            PendingIntent continueIntent = createPendingIntentWearable(WEARABLE_NOTIFICATION_ID_CONTINUE, "D'accord on continue ! 40km on été ajouté à votre course");
            PendingIntent finishIntent = createPendingIntentWearable(WEARABLE_NOTIFICATION_ID_FINISH, "Chemin le plus court pour la maison en cours de calcul");
            PendingIntent callIntent = createPendingIntentWearable(WEARABLE_NOTIFICATION_ID_CALL, EMOJI_EMMERGENCY);

            // Build action for notification action
            NotificationCompat.Action continueAction = new NotificationCompat.Action(R.drawable.ic_directions_runner_black_18p, "Continuez", continueIntent);
            NotificationCompat.Action finishAction = new NotificationCompat.Action(R.drawable.ic_alert_stop_black_18p, "FINIR !!", finishIntent);
            NotificationCompat.Action callAction = new NotificationCompat.Action(R.drawable.ic_alert_emergency_black_18p, "Appeler une ambulance", callIntent);

            // Notification channel ID is ignored for Android 7.1.1
            // (API level 25) and lower.
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_directions_runner_black_18p)
                    .setContentTitle(getString(R.string.ic_wearable_notification_title))
                    .setContentText(getString(R.string.ic_wearable_notification_description))
                    .extend(new NotificationCompat.WearableExtender()
                            .addAction(continueAction)
                            .addAction(finishAction)
                            .addAction(callAction));

            // Get an instance of the NotificationManager service
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getApplicationContext());
            // Issue the notification with notification manager.
            notificationManager.notify(WEARABLE_NOTIFICATION_ID, notificationBuilder.build());
        });
    }

    /*
     *  Method called by system when a new Intent is received
     *  Display a toast with a message if the Intent is generated by
     *  createPendingIntent method.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null) return;
        if (intent.getAction() == null) return;
        if (intent.getAction().startsWith(Constants.MY_PENDING_INTENT_ACTION)) {
            Toast.makeText(this, "" + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();

            int notificationToCancel = 0;

            if (Constants.MY_PENDING_INTENT_ACTION_SIMPLE.equals(intent.getAction())) {
                notificationToCancel = SIMPLE_NOTIFICATION_ID;
            } else if (Constants.MY_PENDING_INTENT_ACTION_ACTIONS.equals(intent.getAction())) {
                notificationToCancel = ACTIONS_NOTIFICATION_ID;
            } else if (Constants.MY_PENDING_INTENT_ACTION_WEARABLE.equals(intent.getAction())) {
                notificationToCancel = WEARABLE_NOTIFICATION_ID;
            }

            NotificationManagerCompat.from(this).cancel(notificationToCancel);
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
     * @param action      The action we want to set the pending intent
     * @return The pending Intent
     */
    private PendingIntent createPendingIntent(int requestCode, String message, String action) {
        Intent myIntent = new Intent(NotificationActivity.this, NotificationActivity.class);
        myIntent.setAction(action);
        myIntent.putExtra("msg", message);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(myIntent);

        return stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Method used to create a PendingIntent with the specified message
     * The intent will start a new activity Instance or bring to front an existing one
     * with a toast corresponding to the simple notification
     * See parentActivityName and launchMode options in Manifest
     * See https://developer.android.com/training/notify-user/navigation.html for TaskStackBuilder
     *
     * @param requestCode The request code
     * @param message     The message
     * @return The pending Intent
     */
    private PendingIntent createPendingIntentSimple(int requestCode, String message) {
        return createPendingIntent(requestCode, message, Constants.MY_PENDING_INTENT_ACTION_SIMPLE);
    }

    /**
     * Method used to create a PendingIntent with the specified message
     * The intent will start a new activity Instance or bring to front an existing one
     * with a toast corresponding to the actions notification
     * See parentActivityName and launchMode options in Manifest
     * See https://developer.android.com/training/notify-user/navigation.html for TaskStackBuilder
     *
     * @param requestCode The request code
     * @param message     The message
     * @return The pending Intent
     */
    private PendingIntent createPendingIntentActions(int requestCode, String message) {
        return createPendingIntent(requestCode, message, Constants.MY_PENDING_INTENT_ACTION_ACTIONS);
    }

    /**
     * Method used to create a PendingIntent with the specified message
     * The intent will start a new activity Instance or bring to front an existing one
     * with a toast corresponding to the Wearable notification
     * See parentActivityName and launchMode options in Manifest
     * See https://developer.android.com/training/notify-user/navigation.html for TaskStackBuilder
     *
     * @param requestCode The request code
     * @param message     The message
     * @return The pending Intent
     */
    private PendingIntent createPendingIntentWearable(int requestCode, String message) {
        return createPendingIntent(requestCode, message, Constants.MY_PENDING_INTENT_ACTION_WEARABLE);
    }

    /**
     * Method used to create a Notification Channel,
     * necessary since the Android 26+ when we use the notification
     */
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