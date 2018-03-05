package tamas.marton.gittrend.home

import tamas.marton.gittrend.api.model.Repositories


data class UIStateModel private constructor(
        private val inProgress: Boolean = false,
        private val error: Throwable? = null,
        private val repositories: Repositories? = null
) {
    companion object {
        fun loading() = UIStateModel(inProgress = true)
        fun success(repositories: Repositories) = UIStateModel(repositories = repositories)
        fun error(error: Throwable) = UIStateModel(error = error)
    }

    fun isLoading() = inProgress
    fun isError() = error != null
    fun isSuccess() = repositories?.items?.isNotEmpty() ?: false && !isError()
    fun isEmpty() = !inProgress && error == null && !isSuccess()

    fun getError(): Throwable {
        if (error != null) {
            return error
        }
        throw IllegalStateException("Error shouldn't be null")
    }

    fun getRepositories(): Repositories {
        if (repositories != null) {
            return repositories
        }
        throw IllegalStateException("Repositories shouldn't be null")
    }
}