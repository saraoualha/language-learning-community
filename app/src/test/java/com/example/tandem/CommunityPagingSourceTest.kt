package com.example.tandem

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tandem.data.CommunityApiService
import com.example.tandem.data.CommunityPagingSource
import com.example.tandem.data.CommunityResponse
import com.example.tandem.data.MemberDto
import com.example.tandem.domain.Member
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests that CommunityPagingSource correctly loads and maps members from the API.
 */
class CommunityPagingSourceTest {

    // fake API that returns predictable data
    private val fakeApi = object : CommunityApiService {
        override suspend fun getCommunityPage(page: Int): CommunityResponse {
            return CommunityResponse(
                response = listOf(
                    MemberDto(
                        id = 1,
                        firstName = "Tobi",
                        pictureUrl = "https://tandem2019.web.app/img/pic1.png",
                        topic = "What's something not many people know about you?",
                        natives = listOf("de", "ja"),
                        learns = listOf("en"),
                        referenceCnt = 0
                    ),
                    MemberDto(
                        id = 2,
                        firstName = "Luca",
                        pictureUrl = "https://tandem2019.web.app/img/pic1.png",
                        topic = "What's something not many people know about you?",
                        natives = listOf("de"),
                        learns = listOf("en", "pt"),
                        referenceCnt = 10
                    )
                ),
                errorCode = null,
                type = "success"
            )
        }
    }

    private val pagingSource = CommunityPagingSource(fakeApi, emptySet())

    @Test
    fun `load returns correct members on success`() = runTest {
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(2, page.data.size)
        assertEquals("Tobi", page.data[0].firstName)
        assertEquals(true, page.data[0].isNew) // referenceCnt == 0
        assertEquals("Luca", page.data[1].firstName)
        assertEquals(false, page.data[1].isNew) // referenceCnt == 10
    }

    @Test
    fun `load sets prevKey to null on first page`() = runTest {
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertNull(result.prevKey)
    }

    @Test
    fun `load returns error on api failure`() = runTest {
        val failingApi = object : CommunityApiService {
            override suspend fun getCommunityPage(page: Int): CommunityResponse {
                throw Exception("Network error")
            }
        }
        val failingSource = CommunityPagingSource(failingApi, emptySet())
        val result = failingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )
        assertTrue(result is PagingSource.LoadResult.Error)
    }
}