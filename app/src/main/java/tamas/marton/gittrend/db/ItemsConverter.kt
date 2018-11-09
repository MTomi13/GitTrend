package tamas.marton.gittrend.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tamas.marton.gittrend.api.model.Item


class ItemsConverter {

    @TypeConverter
    fun fromString(value: String): List<Item> {
        val listType = object : TypeToken<List<Item>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(items: List<Item>): String {
        return Gson().toJson(items)

    }
}