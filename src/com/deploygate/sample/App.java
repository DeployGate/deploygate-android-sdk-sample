
package com.deploygate.sample;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Install DeployGate to this application instance.
        DeployGate.install(this);
    }
}
