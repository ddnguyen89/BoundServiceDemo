package nguyen.boundservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.os.IBinder;
import android.content.Context;
import android.content.ComponentName;
import android.content.ServiceConnection;

import nguyen.boundservicedemo.MyService.MyLocalBinder;

public class TimeActivity extends AppCompatActivity {

    MyService myService; //reference to the class
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        ComponentName i = startService(new Intent(this, MyService.class));
        bindService(new Intent(this, MyService.class), myConnection, Context.BIND_AUTO_CREATE); //bind the service
    }

    public void showTime(View view) {
        String currentTime = myService.getCurrentTime();

        TextView mTimeTV = (TextView) findViewById(R.id.timeTV);

        mTimeTV.setText(currentTime);
    }

    //action class that will bind to the service
    private ServiceConnection myConnection = new ServiceConnection() {

        //what is it that you want to do when you connect
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            myService = binder.getService(); //get service to the class
            isBound = true; //bound to a service
        }

        //what is it that you want to do when you disconnect
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false; //disconnect the service
        }
    };
}
