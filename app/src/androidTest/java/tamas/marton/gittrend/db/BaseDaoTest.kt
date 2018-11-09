package tamas.marton.gittrend.db

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import org.junit.After
import org.junit.Before

open class BaseDaoTest {

    protected lateinit var repositoriesDataBase: RepositoriesDataBase

    @Before
    open fun initDb() {
        repositoriesDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RepositoriesDataBase::class.java).build()
    }

    @After
    fun closeDb() {
        repositoriesDataBase.close()
    }
}