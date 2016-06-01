
package com.deploygate.sample;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Install DeployGate to this application instance.
        DeployGate.install(this);
        
        // [[ Since DeployGate SDK r2 ]]
        //
        // If you want to prevent the app distributed by someone else,
        // specify your username explicitly here, like:
        //
        // DeployGate.install(this, "YOURUSERNAME");
    }
}
