package tamas.marton.gittrend.api


class EmptyResultException : Exception() {

    override val message: String?
        get() = "With these query parameters, we could not provide ani results. Pls try again later."
}