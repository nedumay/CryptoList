package com.example.cryptolist.domain

import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke() = repository.loadData()
}