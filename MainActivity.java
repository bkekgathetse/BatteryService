package swift.com.batteryservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Method to start the service - invoked on Button click
    public void startService(View view) {

        Toast.makeText( this, "WakeLock Service Started", Toast.LENGTH_LONG).show();
        //send a Broadcast to Wakeful class
        sendBroadcast(new Intent(this, MyWakefulReceiver.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        //stopService(new Intent(getBaseContext(), MyService.class));
        stopService(new Intent(getBaseContext(), TimeService.class));
       // stopService(new Intent(getBaseContext(), MyIntentService.class));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
