package com.example.tandem.data

import com.example.tandem.domain.Member

/**
 * Raw API response model for the community endpoint.
 * Maps directly to the JSON structure returned by the Tandem API.
 */
data class CommunityResponse(
    val response: List<MemberDto>,
    val errorCode: String?,
    val type: String
)

/**
 * Represents a single member as returned by the API.
 * Gets mapped to the clean Member domain model before reaching the rest of the app.
 */
data class MemberDto(
    val id: Int,
    val firstName: String,
    val pictureUrl: String,
    val topic: String,
    val natives: List<String>,
    val learns: List<String>,
    val referenceCnt: Int
) {
    fun toDomain(isLiked: Boolean = false) = Member(
        id = id,
        firstName = firstName,
        pictureUrl = pictureUrl,
        topic = topic,
        natives = natives,
        learns = learns,
        referenceCnt = referenceCnt,
        isLiked = isLiked
    )
}