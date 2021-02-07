package com.example.practicaltest.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PreferenceManager(mContext: Context) {

    private val preferences: SharedPreferences
   var context: Context

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

         preferences = EncryptedSharedPreferences.create(
            PREF_NAME,
            masterKeyAlias,
            mContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        context = mContext
    }

    fun clearPrefrences() {
        preferences.edit().clear().apply()
    }

    fun removePreference(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun setStringPreference(key: String, stringValue: String) {
        preferences.edit().putString(key, stringValue).apply()
    }

    fun setBooleanPreference(key: String, booleanValue: Boolean) {
        preferences.edit().putBoolean(key, booleanValue).apply()
    }

    fun setIntPreference(key: String, intValue: Int) {
        preferences.edit().putInt(key, intValue).apply()
    }

    fun getStringPreference(key: String): String? {
        return preferences.getString(key, "")
    }

    fun getBooleanPreference(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getIntPreference(key: String): Int {
        return preferences.getInt(key, -1)
    }

    /**
     * get user login or not
     */
    fun getUserLogin(): Boolean {
        return preferences.getBoolean(IS_USER_ALREADY_LOGIN, false)
    }
    /**
     * get user login or not
     */
    fun setUserLogin(isLogin: Boolean){
        preferences.edit().putBoolean(IS_USER_ALREADY_LOGIN, isLogin).apply()
    }

     companion object {
        const val PREF_NAME = "practicaltest"

        val IS_USER_ALREADY_LOGIN = "IS_USER_ALREADY_LOGIN"

    }
}
