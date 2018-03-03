package tamas.marton.gittrend.home.adapter

import tamas.marton.gittrend.api.model.Repositories


class CardMapper {

    fun map(repositories: Repositories): List<CardUIModel> {
        val cardUIModelList = arrayListOf<CardUIModel>()
        repositories.items.forEach {
            val id = it.id
            val name = it.name
            val fullName = it.fullName
            val avatarUrl = it.owner.avatarUrl
            val lastUpdated = it.updatedAt
            cardUIModelList.add(CardUIModel(id, name, fullName, avatarUrl, lastUpdated))
        }
        return cardUIModelList
    }
}