package com.example.tandem.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for liked members.
 * Defines all database operations — Room generates the actual implementation at compile time.
 */
@Dao
interface LikedMemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun likeMember(entity: MemberEntity)

    @Query("DELETE FROM liked_members WHERE memberId = :memberId")
    suspend fun unlikeMember(memberId: Int)

    @Query("SELECT memberId FROM liked_members")
    fun getLikedIds(): Flow<List<Int>>
}