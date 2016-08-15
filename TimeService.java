package swift.com.batteryservice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
 import java.io.FileOutputStream;
/**
 * Created by bernerdictk on 6/30/16.
 */
public class TimeService extends Service {
    // constant
    public static final long NOTIFY_INTERVAL =  180000;// milliseconds = 3minutes!
    PowerConnectionReceiver receiver = new PowerConnectionReceiver();

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    private int voltage;
    private int temperature;
    private int level;
    private int scale;



    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }
    @Override
    public void onCreate() {
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }


    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
              level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
              scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0 );


        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        Toast.makeText(this, "TimerService Destroyed", Toast.LENGTH_LONG).show();
    }




    class TimeDisplayTimerTask extends TimerTask {

        //private int counter = 0;
        @Override
        public void run() {


                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // display toast
                            Toast.makeText(getBaseContext(), getDateTime(),
                                    Toast.LENGTH_SHORT).show();

                                String filename = "EngagedECC-256.txt";


                                FileOutputStream outputStream;


                                try {
                                    outputStream = openFileOutput(filename, MODE_APPEND);

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date date = new Date();

                                    outputStream.write(getDateTime().concat(",").getBytes());// " minutes.".getBytes() );


                                    float percent = level / (float) scale;

                                    /**
                                     * We will stop based on Temperature(Kelvin) and Voltage(milli-Volts)
                                     */
                                    outputStream.write(String.valueOf(voltage).concat("mV\n").getBytes());

                                    if (voltage < 2000 && temperature > 302) {

                                        /*Intent i = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
                                        i.putExtra("android.intent.extra.KEY_CONFIRM", true);
                                        startActivity(i);
*/
                                        mTimer.cancel();
                                        Runtime.getRuntime().exec(new String[]{ "su", "-c", "reboot -p" });
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    //Log.e( "run: ",e.printStackTrace() );
                                }

                            }
                       // }
                    });

               // run on another thread

        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
            return sdf.format(new Date());
        }

    }





}