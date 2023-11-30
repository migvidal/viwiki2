package com.migvidal.viwiki2.data.repository

import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchRepository(private val viWikiDatabase: ViWikiDatabaseSpec) : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    /**
     * Single source of truth for the "today" response
     */
    override val data: Flow<Any> = listOf<Any>().asFlow()

    override suspend fun refreshData() {}
}