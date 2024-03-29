package com.wipro.movie.feature.trending.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.wipro.movie.feature.trending.data.repository.datasource.TrendingLocalDataSource
import com.wipro.movie.feature.trending.data.repository.datasource.TrendingRemoteDataSource
import com.wipro.movie.feature.trending.data.repository.mediator.TrendingRemoteMediator
import com.wipro.movie.feature.trending.domain.model.TrendingMovie
import com.wipro.movie.feature.trending.domain.model.TrendingMovieRemoteToLocalMapper
import com.wipro.movie.feature.trending.domain.model.TrendingMoviesLocalMapper
import com.wipro.movie.feature.trending.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val NETWORK_PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 40
private const val PREFETCH_DISTANCE = 6

class TrendingRepositoryImpl @Inject constructor(
    private val remoteDataSource: TrendingRemoteDataSource,
    private val localDataSource: TrendingLocalDataSource,
    private val remoteToLocalMapper: TrendingMovieRemoteToLocalMapper,
    private val localMapper: TrendingMoviesLocalMapper,
) : TrendingRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getTrendingMovies(): Flow<PagingData<TrendingMovie>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INITIAL_LOAD_SIZE,
        ),
        remoteMediator = TrendingRemoteMediator(
            remoteDataSource,
            localDataSource,
            remoteToLocalMapper,
        ),
        pagingSourceFactory = { localDataSource.getPagingSourceFromDb() },
    ).flow.map { pagingData ->
        pagingData.map {
            localMapper.map(it)
        }
    }
}
