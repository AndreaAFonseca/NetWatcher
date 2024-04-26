package com.aaf.netwatcher

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

/*
 * File Name: MainActivity
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-04-25
 * Description: This file is part of an Android project: NetWatcher.
 * This activity gets information about internet connectivity.
 * It uses the IConnectivityListener interface and initialises the
 InternetConnectivityObserver.
 * You can customise the 'onConnected() and 'onDisconnected()'
 actions to suit your application.
 *
 * This project aims to provide connectivity information and status
 within the app.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/


/*
To check the connection, you need networkRequest and networkCallback.
Both are called in onCreate with permission in the Manifest.
A / B / C comments show how it works.
* */


/* A

// networkRequest: requires network
// .addCapability - responsible for indicating that you must have internet
// Choose from several connection types.:
// - NET_CAPABILITY_INTERNET-> You need a connection for any type of connection.
// - NOT_METERED-> Only WiFi.
// - VPN-> Virtual
// .addTransportType - how data is transported/traffic
// There are several types used most often:
// - TRANSPORT_WIFI
// - TRANSPORT_CELLULAR-> mobile data

    private val networkRequest = NetworkRequest.Builder()
        .addCapability( NetworkCapabilities.NET_CAPABILITY_INTERNET )
        .addTransportType( NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType( NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

A (end)*/


/* B
// networkCallback: Notifies network connection and resource changes.
// You need to implement the functions you desire, along with the ones already in use:
// - onAvailable()-> indicates connection to a new network that meets the requirements
//                   of resources and transport type.
//- onLost()-> indicates loss of connection to the network.
//- onCapabilitiesChanged()-> indicates that network capabilities have changed and
//                            provides information about the current network.

                                   //From the '=' onwards, you have to repeat in all activities
    private val networkCallback = object : ConnectivityManager.NetworkCallback(){
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.i("conexao_internet", "onAvailable")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Log.i("conexao_internet", "onLost")
    }
}
B */


class MainActivity : AppCompatActivity(), IConnectivityListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Checks user´s connection using Android´s built-in classes
        networkRequest -> Internet request
        networkcallBack
        * */

        /* C
        val connectivityManager = getSystemService( ConnectivityManager::class.java )
        // Notification of connection change
        connectivityManager.requestNetwork( networkRequest, networkCallback )
        C */

        // Calling the class - RETROFIT STRATEGY
        InternetConnectivityObserver
            // passes the activity instance and encapsulates the two methods
            .instance(this )
            .setCallback( this ) // 'this' refers to the interface (instead of 'this',
            // "B" would be passed; NOT a standard market practice(working in a different
            // scope and requesting an inner class), hence the interface
            .build()

    }

    override fun onConnected() {
        Log.i("conexao_internet", "onConnected")
        Snackbar.make(this.findViewById(android.R.id.content), "Conectado", Snackbar.LENGTH_SHORT)
            .setAction("CLOSE"){
                fun onClick(view: View){  }
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_dark))
            .show()
        // Toast.makeText(this, "Está conectado", Toast.LENGTH_SHORT).show()
    }

    override fun onDisconnected() {
        Log.i("conexao_internet", "onDisconnected")
        /*Snackbar.make(this.findViewById(android.R.id.content), "Desconectado", Snackbar.LENGTH_SHORT)
            .setAction("CLOSE"){
                fun onClick(view: View){  }
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_dark))
            .show()*/
        Toast.makeText(this, "NÃO está conectado", Toast.LENGTH_SHORT).show()
    }
}