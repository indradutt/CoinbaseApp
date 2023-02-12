package com.indra.coinbaseapp.coinbase.viewmodel

sealed class CoinbaseEvents {
    object ConnectionOpened: CoinbaseEvents()
    object ConnectionClosed: CoinbaseEvents()
    object ConnectionFailed: CoinbaseEvents()
    object MessageReceived: CoinbaseEvents()
}
