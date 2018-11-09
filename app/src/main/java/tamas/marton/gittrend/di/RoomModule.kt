package tamas.marton.gittrend.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import tamas.marton.gittrend.db.RepositoriesDao
import tamas.marton.gittrend.db.RepositoriesDataBase
import javax.inject.Singleton


@Module
class RoomModule(application: Application) {

    private var repositoriesDataBase: RepositoriesDataBase = Room.databaseBuilder(application, RepositoriesDataBase::class.java, "room-db")
            .build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): RepositoriesDataBase {
        return repositoriesDataBase
    }

    @Singleton
    @Provides
    fun providesRepositoriesDao(repositoriesDataBase: RepositoriesDataBase): RepositoriesDao {
        return repositoriesDataBase.repositoriesDao()
    }
}