package tamas.marton.gittrend.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [(RepositoriesEntity::class)], version = 1)
abstract class RepositoriesDataBase : RoomDatabase() {

    companion object {
        const val TABLE_REPOSITORIES = "repositories"
    }

    abstract fun repositoriesDao(): RepositoriesDao
}