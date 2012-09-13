
package com.deploygate.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.deploygate.sdk.DeployGate;
import com.deploygate.sdk.DeployGateCallback;

public class SampleActivity extends Activity
        implements DeployGateCallback {

    private static final String TAG = "SampleActivity";

    private TextView mAvailableText;
    private TextView mManagedText;
    private TextView mAuthorizedText;
    private TextView mTitleText;
    private EditText mLogMessage;
    private Button mCrashButton;

    private static final int[] sLogButtonIds = new int[] {
        R.id.logError, R.id.logWarn, R.id.logDebug, R.id.logInfo, R.id.logVerbose
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_me);

        mAvailableText = (TextView) findViewById(R.id.available);
        mManagedText = (TextView) findViewById(R.id.managed);
        mAuthorizedText = (TextView) findViewById(R.id.authorized);
        mTitleText = (TextView) findViewById(R.id.title);
        mCrashButton = (Button) findViewById(R.id.button);
        mLogMessage = (EditText) findViewById(R.id.message);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register for callback, also request refreshing (second argument)
        DeployGate.registerCallback(this, true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister to stop callback
        DeployGate.unregisterCallback(this);
    }


    /**
     * Called when the log buttons clicked. Each button has ID that can be used
     * to change log level.
     * 
     * @param v View instance of the button
     */
    public void onLogClick(View v) {
        String text = mLogMessage.getText().toString();
        switch (v.getId()) {
            case R.id.logError:
                DeployGate.logError(text);
                break;
            case R.id.logWarn:
                DeployGate.logWarn(text);
                break;
            case R.id.logDebug:
                DeployGate.logDebug(text);
                break;
            case R.id.logInfo:
                DeployGate.logInfo(text);
                break;
            case R.id.logVerbose:
                DeployGate.logVerbose(text);
                break;
            default:
                return;
        }
    }

    /**
     * Called when the crash button clicked
     * 
     * @param v View instance of the button
     */
    public void onCrashMeClick(View v) {
        // let's throw!
        throw new RuntimeException("CRASH TEST BUTTON CLICKED YAY!");
    }

    @Override
    public void onInitialized(boolean isServiceAvailable) {
        // will be called to notify DeployGate SDK has initialized
        Log.d(TAG, "DeployGate SDK initialized, is DeployGate available? : " + isServiceAvailable);
        mAvailableText.setText(isServiceAvailable ? R.string.available_yes : R.string.available_no);
    }

    @Override
    public void onStatusChanged(boolean isManaged, boolean isAuthorized, String loginUsername,
            boolean isStopped) {
        // will be called when DeployGate status has changed, including this
        // activity starting and resuming.
        mManagedText.setText(isManaged ? R.string.managed_yes : R.string.managed_no);
        mAuthorizedText.setText(getString(isAuthorized ? R.string.authorized_yes
                : R.string.authorized_no, loginUsername));
        
        mCrashButton.setEnabled(isAuthorized);
        mLogMessage.setEnabled(isAuthorized);
        for (int id : sLogButtonIds) {
            findViewById(id).setEnabled(isAuthorized);
        }
    }

    @Override
    public void onUpdateAvailable(int serial, String versionName, int versionCode) {
        // will be called on app update is available.
        mTitleText.setTextColor(Color.GREEN);
        mTitleText.setText(String.format("Update is Available: #%1$d, %2$s(%3$d)", serial,
                versionName, versionCode));
    }
}
