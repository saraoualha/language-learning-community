package com.example.tandem

import androidx.paging.PagingData
import com.example.tandem.domain.CommunityRepository
import com.example.tandem.domain.GetCommunityUseCase
import com.example.tandem.domain.Member
import com.example.tandem.domain.ToggleLikeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Tests that CommunityViewModel correctly exposes data from the domain layer.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CommunityViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val fakeMembers = listOf(
        Member(
            id = 1,
            firstName = "Tobi",
            pictureUrl = "https://tandem2019.web.app/img/pic1.png",
            topic = "What's something not many people know about you?",
            natives = listOf("de", "ja"),
            learns = listOf("en"),
            referenceCnt = 0
        )
    )

    private lateinit var fakeRepository: FakeCommunityRepository
    private lateinit var viewModel: CommunityViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeCommunityRepository(fakeMembers)
        viewModel = CommunityViewModel(
            getCommunity = GetCommunityUseCase(fakeRepository),
            toggleLike = ToggleLikeUseCase(fakeRepository)
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `members flow is not null`() = runTest {
        assertNotNull(viewModel.members)
    }

    @Test
    fun `onLikeClicked delegates to toggleLike use case`() = runTest {
        viewModel.onLikeClicked(1)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(1, fakeRepository.toggledId)
    }
}

class FakeCommunityRepository(private val members: List<Member>) : CommunityRepository {
    var toggledId: Int? = null

    override fun getPagedMembers() = flowOf(PagingData.from(members))
    override suspend fun toggleLike(memberId: Int) { toggledId = memberId }
    override fun getLikedIds() = flowOf(emptySet<Int>())
}