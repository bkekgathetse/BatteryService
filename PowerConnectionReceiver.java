package swift.com.batteryservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by bernerdictk on 6/30/16.
 */
public class PowerConnectionReceiver extends BroadcastReceiver {

    public static int VOLTAGE;

    //Are We Currently Charging?
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        //How Are we charging?
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        VOLTAGE = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
        System.out.println(VOLTAGE + " ***********************************************************************************************88");


    }





}

