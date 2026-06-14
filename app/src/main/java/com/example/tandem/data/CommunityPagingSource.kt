package com.example.tandem.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tandem.domain.Member

/**
 * Responsible for loading one page of community members at a time.
 * Tells the Paging library how to fetch data and what the next/previous pages are.
 */
class CommunityPagingSource(
    private val api: CommunityApiService,
    private val likedIds: Set<Int>
) : PagingSource<Int, Member>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Member> {
        val page = params.key ?: 1
        return try {
            val response = api.getCommunityPage(page)
            val members = response.response.map { dto ->
                dto.toDomain(isLiked = dto.id in likedIds)
            }
            LoadResult.Page(
                data = members,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (members.size < 20) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Member>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }
}

