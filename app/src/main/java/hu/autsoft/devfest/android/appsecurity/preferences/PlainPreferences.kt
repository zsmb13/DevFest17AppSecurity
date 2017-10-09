package hu.autsoft.key.preferences

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PlainPreferences(val context: Context) {

    operator inline fun <reified T : Any> get(key: String): T? = getSharedPreferences(key)

    operator inline fun <reified T : Any> set(key: String, value: T?) =
            if (value != null)
                putSharedPreferences(key, value)
            else
                removeSharedPreferences(key)

    @PublishedApi
    internal val gson = Gson()

    inline fun <reified T : Any> putSharedPreferences(key: String, content: T?) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().putString(key, gson.toJson(content, object : TypeToken<T>() {}.type)).apply()
    }

    inline fun <reified T : Any> getSharedPreferences(key: String): T? {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val content = preferences.getString(key, "")
        return gson.fromJson(content, object : TypeToken<T>() {}.type)
    }

    fun removeSharedPreferences(key: String) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().remove(key).apply()
    }

}
