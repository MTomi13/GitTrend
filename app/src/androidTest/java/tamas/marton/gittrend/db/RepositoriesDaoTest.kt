package tamas.marton.gittrend.db

import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import tamas.marton.gittrend.api.model.Repositories


@RunWith(AndroidJUnit4::class)
class RepositoriesDaoTest : BaseDaoTest() {

    override fun initDb() {
        super.initDb()
        val repositoriesEntity = RepositoriesEntity(Repositories(1, false, emptyList()))
        repositoriesDataBase.repositoriesDao().addRepositories(repositoriesEntity)
    }

    @Test
    fun insertBookHolidaySavesData() {
        val repositories = repositoriesDataBase.repositoriesDao().getRepositories()
        Assert.assertTrue(repositories != null)
    }
}