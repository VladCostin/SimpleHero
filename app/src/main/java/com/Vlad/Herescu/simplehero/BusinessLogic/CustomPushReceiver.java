package com.Vlad.Herescu.simplehero.BusinessLogic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.Vlad.Herescu.simplehero.DatabaseHandler.DatabaseHandler;
import com.Vlad.Herescu.simplehero.View.MainActivity;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 11/21/2015.
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {

    private NotificationUtils notificationUtils;

    private Intent parseIntent;

    @Override
    protected void onPushReceive(Context context, Intent intent) {

    //    super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.e("message", "Push received: " + json);

            parseIntent = intent;

            parsePushJson(context, json);

        } catch (JSONException e) {
            Log.e("message", "Push message json exception: " + e.getMessage());
        }

    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json) {
        try {


            String data = json.getString("alert");
            if(data.equals(PushTypes.RETRIEVE_DATA))
                ManagerSingleton.getInstance().get_ServerHandler().getNewData();
            else
            {
                if(MainActivity.m_dataBase == null)
                    MainActivity.m_dataBase =   new DatabaseHandler(context.getApplicationContext());
                MainActivity.m_dataBase.addThank();
                Intent resultIntent = new Intent(context, MainActivity.class);
                JSONObject o = new JSONObject(data);
                String hero = o.getString(ParseConstants.KEY_HERO);
                String message = o.getString(ParseConstants.KEY_MESSAGE);
                showNotificationMessage(context, hero, message, resultIntent);
            }


            /*
            else {
                JSONObject o = new JSONObject(data);
                String title = o.getString(ParseConstants.KEY_IDEA);
                String type = o.getString(ParseConstants.KEY_TYPE);


                Intent resultIntent = new Intent(context, MainActivity.class);
                showNotificationMessage(context, type, title, resultIntent);
            }
            */
        } catch (JSONException e) {
            Log.e("message", "Push message json exception: " + e.getMessage());
        }
    }


    /**
     * Shows the notification message in the notification bar
     * If the app is in background, launches the app
     *
     * @param context
     * @param title
     * @param message
     * @param intent
     */

    private void showNotificationMessage(Context context, String title, String message, Intent intent) {

        notificationUtils = new NotificationUtils(context);

        intent.putExtras(parseIntent.getExtras());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(title, message, intent);
    }

}
