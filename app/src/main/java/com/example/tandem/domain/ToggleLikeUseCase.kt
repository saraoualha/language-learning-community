package com.example.tandem.domain

/**
 * Toggles the liked state of a community member.
 */
class ToggleLikeUseCase(private val repository: CommunityRepository) {
    suspend operator fun invoke(memberId: Int) = repository.toggleLike(memberId)
}