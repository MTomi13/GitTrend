package tamas.marton.gittrend.db

import android.arch.persistence.room.*
import tamas.marton.gittrend.api.model.Repositories


@Entity(tableName = RepositoriesDataBase.TABLE_REPOSITORIES)
@TypeConverters(ItemsConverter::class)
data class RepositoriesEntity(

        @Embedded(prefix = GITHUB)
        var repositories: Repositories
) {

    @ColumnInfo(name = ID)
    @PrimaryKey
    var id: Int = 0


    companion object {
        const val ID = "_id"
        const val GITHUB = "github"
    }
}