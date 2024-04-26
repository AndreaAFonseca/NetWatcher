package com.aaf.netwatcher


/*
 * File Name: IConnectivityListener
 * Author: Andréa A. Fonsêca
 * Creation Date: 2024-04-25
 * Description: This file is part of an Android project: NetWatcher.
 * Contains interfaces and implementations related to monitoring internet
 connectivity status in activities.
 * This project aims to provide connectivity information and status within the app.
 *
 * Copyright (c) 2024 Andréa A. Fonsêca
 * Licensed under the MIT License.
 * See the LICENSE file for details.
**/

// Interface that should be implemented in the activity where you want
// to obtain information about internet connectivity.
// The object must be pre-configured.

interface IConnectivityListener {

    fun onConnected()
    fun onDisconnected()
}