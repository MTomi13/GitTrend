package tamas.marton.gittrend.home

import tamas.marton.gittrend.api.model.Repositories


data class UIStateModel private constructor(
        private val inProgress: Boolean = false,
        private val exception: Exception? = null,
        private val repositories: Repositories? = null
) {
    companion object {
        fun loading() = UIStateModel(inProgress = true)
        fun success(repositories: Repositories) = UIStateModel(repositories = repositories)
        fun error(error: Exception) = UIStateModel(exception = error)
    }

    fun isLoading() = inProgress
    fun isError() = exception != null
    fun isSuccess() = repositories?.items?.isNotEmpty() ?: false && !isError()
    fun isEmpty() = !inProgress && exception == null && !isSuccess()

    fun getError(): java.lang.Exception {
        if (exception != null) {
            return exception
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