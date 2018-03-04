package tamas.marton.gittrend.home.adapter

import tamas.marton.gittrend.api.model.Item
import tamas.marton.gittrend.api.model.Repositories
import tamas.marton.gittrend.details.DetailsUIModel


class CardMapper {

    fun map(repositories: Repositories): List<CardUIModel> {
        val cardUIModelList = arrayListOf<CardUIModel>()
        repositories.items.forEach {
            val id = it.id
            val name = it.name
            val fullName = it.fullName
            val avatarUrl = it.owner.avatarUrl
            val lastUpdated = it.updatedAt
            cardUIModelList.add(CardUIModel(id, name, fullName, avatarUrl, lastUpdated, mapDetails(it)))
        }
        return cardUIModelList
    }

    private fun mapDetails(item: Item): DetailsUIModel {
        with(item) {
            return (DetailsUIModel(name, description, language.orEmpty(), owner.type, owner.avatarUrl,
                    updatedAt, createdAt, size, stargazersCount, watchersCount, forksCount, openIssuesCount))
        }
    }
}