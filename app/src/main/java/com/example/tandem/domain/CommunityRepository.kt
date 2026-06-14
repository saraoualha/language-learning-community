package com.example.tandem.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Defines what data operations are available to the rest of the app.
 * The actual implementation lives in the data layer.
 */
interface CommunityRepository {
    fun getPagedMembers(): Flow<PagingData<Member>>
    suspend fun toggleLike(memberId: Int)
    fun getLikedIds(): Flow<Set<Int>>
}