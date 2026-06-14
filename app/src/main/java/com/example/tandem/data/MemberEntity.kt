package com.example.tandem.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity that stores the liked state of community members.
 * We only store the member ID — everything else comes from the API.
 */
@Entity(tableName = "liked_members")
data class MemberEntity(
    @PrimaryKey val memberId: Int
)