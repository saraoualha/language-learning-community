package com.example.tandem

import com.example.tandem.domain.CommunityRepository
import com.example.tandem.domain.ToggleLikeUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

/**
 * Tests that ToggleLikeUseCase correctly delegates to the repository.
 */
class ToggleLikeUseCaseTest {

    // a fake repository that records what was called
    private val fakeRepository = object : CommunityRepository {
        var toggledId: Int? = null

        override fun getPagedMembers() = throw NotImplementedError()
        override fun getLikedIds() = throw NotImplementedError()

        override suspend fun toggleLike(memberId: Int) {
            toggledId = memberId
        }
    }

    private val useCase = ToggleLikeUseCase(fakeRepository)

    @Test
    fun `toggleLike calls repository with correct id`() = runTest {
        useCase(42)
        assertEquals(42, fakeRepository.toggledId)
    }
}