package com.zeepy.zeepyforandroid.preferences

class UserPreferenceManager(private val sharedPreferencesManager: SharedPreferencesManager) {

    fun saveIsAlreadyLogin(isAlreadyLogin: Boolean) {
        sharedPreferencesManager.putSharedPref(IS_ALREADY_LOGIN, isAlreadyLogin)
    }

    fun fetchIsAlreadyLogin(): Boolean {
        return sharedPreferencesManager.getSharedPrefs(IS_ALREADY_LOGIN, false)
    }

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

    fun saveUserId(id: Int) {
        sharedPreferencesManager.putSharedPref(USER_ID, id)
    }

    fun fetchUserId(): Int {
        return sharedPreferencesManager.getSharedPrefs(USER_ID, -1)
    }

    fun saveUserEmail(email: String) {
        sharedPreferencesManager.putSharedPref(USER_EMAIL, email)
    }

    fun fetchUserEmail(): String {
        return sharedPreferencesManager.getSharedPrefs(USER_EMAIL, "")
    }

    fun saveOnboard(onBoard: Boolean) {
        sharedPreferencesManager.putSharedPref(ON_BOARD, onBoard)
    }

    fun fetchUserOnBoard(): Boolean {
        return sharedPreferencesManager.getSharedPrefs(ON_BOARD, false)
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val IS_ALREADY_LOGIN = "isAlreadyLogin"
        private const val USER_ID = "user_id"
        private const val USER_EMAIL = "user_email"
        private const val ON_BOARD = "on_board"
    }
}