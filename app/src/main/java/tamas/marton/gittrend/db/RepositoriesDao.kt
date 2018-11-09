package tamas.marton.gittrend.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


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