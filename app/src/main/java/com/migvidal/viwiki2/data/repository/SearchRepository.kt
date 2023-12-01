package com.migvidal.viwiki2.data.repository

import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchRepository(private val viWikiDatabase: ViWikiDatabaseSpec) : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    /**
     * Single source of truth for the "today" response
     */
    private val _data = MutableStateFlow(emptyList<String>())
    override val data = _data.asStateFlow()


    fun refreshSearchData(query: String) {
        _data.update {
            emptyList()
            // WikipediaApiImpl.wikipediaApiService.getSearchResults(query = query)
        }
    }
}