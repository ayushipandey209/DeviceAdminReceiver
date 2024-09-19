package com.devicemng;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devicemng.mainfiles.MyDeviceAdminReceiver;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    private ComponentName adminComponent;
    private DevicePolicyManager dpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the admin component
        adminComponent = new ComponentName(this, MyDeviceAdminReceiver.class);
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        Button btnGetAccess = findViewById(R.id.btnGetAccess);

        // Set click listener for the button
        btnGetAccess.setOnClickListener(v -> {
            // Check if already a device admin
            if (!dpm.isAdminActive(adminComponent)) {
                // Request to become a device admin
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
                startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
            } else {
                Toast.makeText(this, "Admin access already granted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (resultCode == RESULT_OK) {
                // Admin granted
                Toast.makeText(this, "Admin enabled!", Toast.LENGTH_SHORT).show();
            } else {
                // Admin permission denied
                Toast.makeText(this, "Admin enable failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
