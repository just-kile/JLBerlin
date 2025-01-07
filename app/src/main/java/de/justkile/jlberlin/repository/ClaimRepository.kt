package de.justkile.jlberlin.repository

import de.justkile.jlberlin.datasource.ClaimRemoteDataSource
import de.justkile.jlberlinmodel.DistrictClaim
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ClaimRepository (
    private val claimRemoteDataSource: ClaimRemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
){

    private var _currentClaims = MutableStateFlow(emptyList<DistrictClaim>())
    val currentClaims = _currentClaims.asStateFlow()

    suspend fun getDistrictClaims() = claimRemoteDataSource.getDistrictClaims()

    suspend fun createOrUpdate(claim: DistrictClaim) = claimRemoteDataSource.createOrUpdate(claim)

    suspend fun listenForNewClaims() {
        claimRemoteDataSource.listenForNewClaims { _currentClaims.value = it }
    }

}