package com.zeepy.zeepyforandroid.preferences

class UserPreferenceManager(private val sharedPreferencesManager: SharedPreferencesManager) {

    fun saveUserAccessToken(token: String) {
        sharedPreferencesManager.putSharedPref(ACCESS_TOKEN, token)
    }

    fun fetchUserAccessToken(): String {
        return sharedPreferencesManager.getSharedPrefs(ACCESS_TOKEN, "")
    }

    fun saveUserRefreshToken(token: String) {
        sharedPreferencesManager.putSharedPref(REFRESH_TOKEN, token)
    }

    fun fetchUserRefreshToken(): String {
        return sharedPreferencesManager.getSharedPrefs(REFRESH_TOKEN, "")
    }


    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}