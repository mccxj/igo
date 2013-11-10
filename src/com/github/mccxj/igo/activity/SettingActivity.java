package com.github.mccxj.igo.activity;

import com.github.mccxj.igo.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingActivity extends PreferenceActivity {
    private SharedPreferences sp;
    private Preference silence;
    private Preference shownum;
    private Preference update4wifi;
    private Preference feedback;
    private Preference update;
    private Preference about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        
        silence = (Preference) findPreference("silence");
        shownum = (Preference) findPreference("shownum");
        update4wifi = (Preference) findPreference("update4wifi");
        feedback = (Preference) findPreference("feedback");
        update = (Preference) findPreference("update");
        about = (Preference) findPreference("about");
//        if (ac.isLogin()) {
//            account.setTitle(R.string.main_menu_logout);
//        } else {
//            account.setTitle(R.string.main_menu_login);
//        }
//        account.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            public boolean onPreferenceClick(Preference preference) {
//                UIHelper.loginOrLogout(Setting.this);
//                account.setTitle(R.string.main_menu_login);
//                return true;
//            }
//        });
    }
}
