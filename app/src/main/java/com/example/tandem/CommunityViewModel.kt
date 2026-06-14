package com.example.tandem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tandem.data.CommunityRepositoryImpl
import com.example.tandem.domain.GetCommunityUseCase
import com.example.tandem.domain.Member
import com.example.tandem.domain.ToggleLikeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Bridges the domain layer and the UI.
 * Exposes paged community data and handles like interactions.
 */
class CommunityViewModel(
    private val getCommunity: GetCommunityUseCase,
    private val toggleLike: ToggleLikeUseCase
) : ViewModel() {

    // the paged list of members, cached so it survives screen rotation
    val members: Flow<PagingData<Member>> = getCommunity()
        .cachedIn(viewModelScope)

    fun onLikeClicked(memberId: Int) {
        viewModelScope.launch {
            toggleLike(memberId)
        }
    }
}

/**
 * Creates the ViewModel with all its dependencies wired together.
 * Needed because the ViewModel takes constructor parameters.
 */
class CommunityViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = CommunityRepositoryImpl(context)
        val getCommunity = GetCommunityUseCase(repository)
        val toggleLike = ToggleLikeUseCase(repository)
        @Suppress("UNCHECKED_CAST")
        return CommunityViewModel(getCommunity, toggleLike) as T
    }
}