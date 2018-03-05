package tamas.marton.gittrend.home

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import tamas.marton.gittrend.api.EmptyResultException
import tamas.marton.gittrend.api.model.Item
import tamas.marton.gittrend.api.model.Owner
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.api.schedulers.SchedulerProvider
import tamas.marton.gittrend.db.RepositoriesEntity
import tamas.marton.gittrend.home.adapter.CardMapper


@RunWith(MockitoJUnitRunner::class)
class HomePresenterImplTest {

    lateinit var testScheduler: TestScheduler
    lateinit var homePresenter: HomePresenterImpl

    @Mock
    lateinit var homeInteractor: HomeInteractor
    @Mock
    lateinit var cardMapper: CardMapper
    @Mock
    lateinit var homeView: HomeView
    @Mock
    lateinit var compositeDisposable: CompositeDisposable
    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        homePresenter = HomePresenterImpl(homeInteractor, cardMapper, schedulerProvider, homeView)
        homePresenter.compositeDisposable = compositeDisposable
    }

    @After
    fun tearDown() {
        homePresenter.unSubscribe()
    }

    @Test
    fun testGetRepositoriesSuccess() {
        val repositories = getRepositories()
        val observable = Observable.just(repositories)
        `when`(homeInteractor.getRepositories()).thenReturn(observable)
        `when`(schedulerProvider.backgroundThread()).thenReturn(testScheduler)
        `when`(schedulerProvider.mainThread()).thenReturn(testScheduler)

        homePresenter.getBestRepositories()
        testScheduler.triggerActions()

        verify(homeInteractor).getRepositories()
        verify(homeView).showLoading()
        verify(homeView).saveData(repositories)
        homePresenter.mapEntity(RepositoriesEntity(repositories))
        verify(homeView).setList(emptyList())
        verifyNoMoreInteractions(homeView, homeInteractor)
    }

    @Test
    fun testGetRepositoriesFailure() {
        `when`(homeInteractor.getRepositories()).thenReturn(Observable.error(IllegalArgumentException("Invalid Response")))
        `when`(schedulerProvider.backgroundThread()).thenReturn(testScheduler)
        `when`(schedulerProvider.mainThread()).thenReturn(testScheduler)

        homePresenter.getBestRepositories()
        testScheduler.triggerActions()

        verify(homeInteractor).getRepositories()
        verify(homeView).showLoading()
        verify(homeView).hideProgressBar()
        verify(homeView).hideSwipeRefreshLoader()
        verify(homeView).onError("Invalid Response")
        verifyNoMoreInteractions(homeView, homeInteractor)
    }

    @Test
    fun testGetRepositoriesEmpty() {
        val observable = Observable.just(Repositories(0, false, emptyList()))
        `when`(homeInteractor.getRepositories()).thenReturn(observable)
        `when`(schedulerProvider.backgroundThread()).thenReturn(testScheduler)
        `when`(schedulerProvider.mainThread()).thenReturn(testScheduler)

        homePresenter.getBestRepositories()
        testScheduler.triggerActions()

        verify(homeInteractor).getRepositories()
        verify(homeView).showLoading()
        verify(homeView).hideProgressBar()
        verify(homeView).hideSwipeRefreshLoader()
        verify(homeView).onError(EmptyResultException().message)
        verifyNoMoreInteractions(homeView, homeInteractor)
    }

    private fun getRepositories(): Repositories {
        val count = 10
        val itemList = arrayListOf<Item>()
        for (x in 0 until count) {
            itemList.add(Item(x, "name$x", "fullName$x", Owner(), false, "", "", false,
                    "", "", "", "", "", 12, 1, 1, "",
                    1, 2, "", "", 0L))
        }
        return Repositories(count, false, itemList)
    }
}