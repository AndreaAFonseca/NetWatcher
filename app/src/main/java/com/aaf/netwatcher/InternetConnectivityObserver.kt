package com.aaf.netwatcher


/*
 * File Name: InternetConnectivityObserver
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-04-25
 * Description: This file is part of an Android project: NetWatcher.
 * Contains a singleton instance, which, like a class, encapsulates
 specific functionalities for monitoring internet connectivity.
 * It can change connection types, data traffic, and resources as
 long as they adhere to the native class.
 * It's meant to be configured only once in the project and used in
 any activity by simply invoking it where you want to obtain the information.
 * It doesn't depend on any particular architecture (doesn't fit into the architecture).
 * It uses Android's own classes, eliminating the need for a Broadcast Receiver.
 * It requires the InternetConnectivity interface to maintain scope.
 *
 * This project aims to provide connectivity information and status within the app.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

object InternetConnectivityObserver {
    // This is basically the 'B' annotation of MainActivity
    // Expand network monitoring without changing activity
    private val networkCallback = object : ConnectivityManager.NetworkCallback(){
        // Creates the callback inside the class, so that when it's called, it already
        // has the instance to execute. SIMILAR TO MVP (where the activity acts as a middleman)
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            internetConnectivity?.onConnected()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            internetConnectivity?.onDisconnected()
        }
    }
    private var connectivityManager: ConnectivityManager? = null
    private var internetConnectivity: IConnectivityListener? = null

    fun instance( context: Context ) : InternetConnectivityObserver { // The Firebase strategy 'getInstance()'
        connectivityManager = context.getSystemService( ConnectivityManager::class.java )
        // To access in the activity, it needs to return
        return InternetConnectivityObserver
    }

    fun setCallback( internetConnectivity: IConnectivityListener) : InternetConnectivityObserver { // It uses an interface to call methods.
        InternetConnectivityObserver.internetConnectivity = internetConnectivity
        // Callback only works if...
        return InternetConnectivityObserver// CHAIN METHODS
    }

    fun build(){ // You can add more monitoring by including more capabilities and transport types.
        // needs the context (you can go directly here or use the firebase 'getInstance() strategy)
        // transcript 'C' annotation of MainActivity
        //connectivityManager.requestNetwork( networkRequest, networkCallback )

        // transcript 'A' annotation of MainActivity
        val networkRequest = NetworkRequest.Builder()
            .addCapability( NetworkCapabilities.NET_CAPABILITY_INTERNET )
            .addTransportType( NetworkCapabilities.TRANSPORT_WIFI )
            .addTransportType( NetworkCapabilities.TRANSPORT_CELLULAR )
            .build()

        connectivityManager?.requestNetwork( networkRequest, networkCallback )

    }
}