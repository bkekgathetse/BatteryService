package swift.com.batteryservice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

/**
 * Created by bernerdictk on 7/6/16.
 */
public class MyWakefulReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Start the service, keeping the device awake while the service is
        // launching. This is the Intent to deliver to the service.
        // Let it continue running until it is stopped.


        Intent service = new Intent(context, MyIntentService.class);
        startWakefulService(context, service);
    }
}