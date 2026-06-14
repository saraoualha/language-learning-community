package com.example.tandem.data

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Room
import com.example.tandem.domain.CommunityRepository
import com.example.tandem.domain.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of CommunityRepository.
 * Coordinates between the API and local database to deliver members with their liked state.
 */
class CommunityRepositoryImpl(context: Context) : CommunityRepository {

    // sets up the API client with the base URL and JSON converter
    private val api: CommunityApiService = retrofit2.Retrofit.Builder()
        .baseUrl("https://tandem2019.web.app/api/")
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()
        .create(CommunityApiService::class.java)

    // creates the local database
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "tandem_db"
    ).build()

    private val dao = db.likedMemberDao()

    override fun getPagedMembers(): Flow<PagingData<Member>> {
        return dao.getLikedIds()
            .map { it.toSet() }
            .flatMapLatest { likedIds ->
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        CommunityPagingSource(api, likedIds)
                    }
                ).flow
            }
    }

    override suspend fun toggleLike(memberId: Int) {
        val likedIds = dao.getLikedIds().first()
        if (memberId in likedIds) {
            dao.unlikeMember(memberId)
        } else {
            dao.likeMember(MemberEntity(memberId))
        }
    }

    override fun getLikedIds(): Flow<Set<Int>> {
        return dao.getLikedIds().map { it.toSet() }
    }
}

