/*
 * SPDX-FileCopyrightText: 2016 The CyanogenMod Project
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.clover.setupwizard;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

import static com.clover.setupwizard.SetupWizardApp.ACTION_LOAD;
import static com.clover.setupwizard.SetupWizardApp.EXTRA_SCRIPT_URI;
import static com.clover.setupwizard.SetupWizardApp.EXTRA_WIZARD_BUNDLE;
import static com.clover.setupwizard.SetupWizardApp.LOGV;

import android.annotation.Nullable;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.clover.setupwizard.util.SetupWizardUtils;
import com.clover.setupwizard.wizardmanager.WizardManager;

public class SetupWizardActivity extends AppCompatActivity {
    private static final String TAG = SetupWizardActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LOGV) {
            Log.v(TAG, "onCreate savedInstanceState=" + savedInstanceState);
        }
        SetupWizardUtils.enableComponent(this, WizardManager.class);
        Intent intent = new Intent(ACTION_LOAD);
        Bundle wizardBundle = new Bundle();
        if (SetupWizardUtils.isOwner()) {
            wizardBundle.putString(EXTRA_SCRIPT_URI, getString(R.string.clover_wizard_script_uri));
        } else if (SetupWizardUtils.isManagedProfile(this)) {
            wizardBundle.putString(EXTRA_SCRIPT_URI,
                    getString(R.string.clover_wizard_script_managed_profile_uri));
        } else {
            wizardBundle.putString(EXTRA_SCRIPT_URI,
                    getString(R.string.clover_wizard_script_user_uri));
        }
        intent.putExtra(EXTRA_WIZARD_BUNDLE, wizardBundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_GRANT_READ_URI_PERMISSION);
        intent.setPackage(getPackageName());
        startActivity(intent);
        finish();
    }
}
