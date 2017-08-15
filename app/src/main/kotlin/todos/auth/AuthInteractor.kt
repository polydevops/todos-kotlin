package todos.auth

import android.content.Context


class AuthInteractor(val context: Context) {

    private val KEY_TOKEN = "tokenId"

    val sharedPreferences = context.getSharedPreferences("com.polydevops.todos", Context.MODE_PRIVATE)

    fun cacheToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

}