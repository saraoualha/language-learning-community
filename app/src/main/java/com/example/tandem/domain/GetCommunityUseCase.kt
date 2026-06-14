package com.example.tandem.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Retrieves the paginated list of community members.
 */
class GetCommunityUseCase(private val repository: CommunityRepository) {
    operator fun invoke(): Flow<PagingData<Member>> = repository.getPagedMembers()
}