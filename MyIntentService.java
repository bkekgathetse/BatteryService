package swift.com.batteryservice;

import android.app.Service;

/**
 * Created by bernerdictk on 7/6/16.
 */
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
/**
 * Created by bernerdictk on 7/5/16.
 */
public class MyIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

public MyIntentService(){
    super("MyIntentService");

  }


    @Override
    protected void onHandleIntent(Intent intent) {
        //  Bundle extras = intent.getExtras();
        // Do the work that requires your app to keep the CPU running.
        // ...
        startService(new Intent(this, TimeService.class));
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        MyWakefulReceiver.completeWakefulIntent(intent);
    }

}