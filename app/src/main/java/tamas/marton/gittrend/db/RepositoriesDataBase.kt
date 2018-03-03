package tamas.marton.gittrend.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = [(RepositoriesEntity::class)], version = 1)
abstract class RepositoriesDataBase : RoomDatabase() {

    companion object {
        const val TABLE_REPOSITORIES = "repositories"
    }

    abstract fun repositoriesDao(): RepositoriesDao
}