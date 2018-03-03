package tamas.marton.gittrend.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface RepositoriesDao {

    companion object {
        const val SELECT_ALL = "SELECT * FROM ${RepositoriesDataBase.TABLE_REPOSITORIES}"
    }

    @Query(SELECT_ALL)
    fun getRepositories(): LiveData<RepositoriesEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepositories(repositoriesEntity: RepositoriesEntity)
}