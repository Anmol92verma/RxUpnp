package com.mutualmobile.rxupnp

import android.content.Context
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ChannelListener
import android.os.Looper
import io.reactivex.Observable

object RxUpnp : ChannelListener {

  private var wifiP2PManager: WifiP2pManager? = null
  private var wifiP2PChannel: WifiP2pManager.Channel? = null

  fun initialize(context: Context) {
    wifiP2PManager = context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    wifiP2PChannel = wifiP2PManager?.initialize(context, Looper.getMainLooper(), this)
  }

  fun requestPeersList(): Observable<WifiP2pDeviceList>? {
    return Observable.create<WifiP2pDeviceList> { emitter ->
      wifiP2PManager?.requestPeers(wifiP2PChannel) {
        emitter.onNext(it)
      }
    }
  }

  override fun onChannelDisconnected() {

  }
}