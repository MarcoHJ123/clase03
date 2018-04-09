package pe.com.gescom.acsion.monitoreo;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by gescom on 16/11/2016.
 */

public class MonitoreoApp extends com.orm.SugarApp {
    private static String TAG = "MonitoreoApp";
    static MonitoreoApp MonitoreoAppAppInstance;

    public MonitoreoApp() {
        super();
        MonitoreoAppAppInstance = this;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Application.onCreate");
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
