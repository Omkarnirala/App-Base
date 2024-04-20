class PrefHelper (
    context: Context
) {
    private val PREFS_NAME = "app_name"
    private var sharedPref: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Int){
        editor.putInt(key, value)
            .apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun getInt(key: String): Int{
        return sharedPref.getInt(key, 0)
    }

    fun clearValue(key: String){
        editor.remove(key)
            .apply()
    }

    fun clear() {
        editor.clear()
            .apply()
    }
}