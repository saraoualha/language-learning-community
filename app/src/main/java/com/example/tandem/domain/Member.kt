package com.example.tandem.domain

/**
 * Represents a single community member.
 */
data class Member(
    val id: Int,
    val firstName: String,
    val pictureUrl: String,
    val topic: String,
    val natives: List<String>,
    val learns: List<String>,
    val referenceCnt: Int,
    val isLiked: Boolean = false
) {
    val isNew: Boolean get() = referenceCnt == 0
}
