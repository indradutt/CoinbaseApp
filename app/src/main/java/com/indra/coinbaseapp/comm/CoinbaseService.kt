package com.indra.coinbaseapp.comm

import com.indra.coinbaseapp.comm.request.SubscribeAction
import com.indra.coinbaseapp.comm.response.TickerResponse
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface CoinbaseService {
    @Send
    fun subscribe(action: SubscribeAction)

    @Receive
    fun observeTicker(): Flowable<TickerResponse>

    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>
}