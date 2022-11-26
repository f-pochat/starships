package repositories

import com.google.gson.Gson
import models.Settings
import java.io.FileReader

private const val config_file = "config.json"

fun readConfig(): Settings {
    val gson = Gson()
    return gson.fromJson(FileReader(config_file), Settings::class.java)
}
