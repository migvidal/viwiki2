package com.migvidal.viwiki2.data.repository

import com.migvidal.viwiki2.data.network.search.SearchResponseModel
import com.migvidal.viwiki2.data.network.search.WikipediaApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Single source of truth for the "search" response
 */
class SearchRepository : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    private val _data: MutableStateFlow<SearchResponseModel> = MutableStateFlow(SearchResponseModel())
    override val data = _data.asStateFlow()

    suspend fun refreshSearchData(query: String) {
        _data.update {
            WikipediaApiImpl.wikipediaApiService.getSearchResults(query = query)
        }
    }
}