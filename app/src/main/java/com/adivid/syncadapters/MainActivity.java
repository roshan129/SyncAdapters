package com.adivid.syncadapters;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.adivid.syncadapters.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "adivid.com";
    // The account name
    public static final String ACCOUNT = "placeholderaccount";
    // Instance fields
    Account mAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the placeholder account
        mAccount = CreateSyncAccount(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked");
                onRefreshButtonClick(view);
            }
        });

    }

    public void onRefreshButtonClick(View v) {
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
    }



    /**
     * Create a new placeholder account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);

        try{
            Log.d(TAG, "CreateSyncAccount: inside try");
            accountManager.addAccountExplicitly(newAccount, null, null);
        }catch (Exception e){
            Log.e(TAG, "CreateSyncAccount: "+ e.getMessage());
        }

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager != null) {
            if (accountManager.addAccountExplicitly(newAccount, null, null)) {
                /*
                 * If you don't set android:syncable="true" in
                 * in your <provider> element in the manifest,
                 * then call context.setIsSyncable(account, AUTHORITY, 1)
                 * here.
                 */
                Log.d(TAG, "CreateSyncAccount: inside new account");
                return newAccount;
            } else {
                /*
                 * The account exists or some other error occurred. Log this, report it,
                 * or handle it internally.
                 */

                Log.d(TAG, "CreateSyncAccount: some error occurred");
                return null;
            }
        }
        return null;
    }


}