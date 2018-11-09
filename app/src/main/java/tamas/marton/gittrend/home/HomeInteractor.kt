package tamas.marton.gittrend.home

import kotlinx.coroutines.channels.ReceiveChannel
import tamas.marton.gittrend.api.model.Repositories


interface HomeInteractor {

    fun getRepositories(): ReceiveChannel<Repositories>
}