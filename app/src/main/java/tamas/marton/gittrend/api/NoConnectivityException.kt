package tamas.marton.gittrend.api


class NoConnectivityException : Exception() {

    override val message: String?
        get() = "Internet connection error! Pls connect to the internet!"
}