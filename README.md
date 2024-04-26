# NetWatcher - Internet Connectivity Observer for Android
This utility class monitors changes in internet connectivity for Android applications.
It adheres to best development practices and provides a straightforward and effective way to notify your activity when connectivity is established or lost.

## Features

- Monitors changes in internet connectivity. 
- Immediately notifies when connectivity is established or lost.
- Supports Wi-Fi and cellular connections.

## Usage

1. Add the necessary permissions to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
```

2. Download the **InternetConnectivityObserver.kt** class and include it in your Android Project. Or copy:

```kotlin
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

object InternetConnectivityObserver {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            internetConnectivity?.onConnected()
        }
        
        override fun onLost(network: Network) {
            super.onLost(network)
            internetConnectivity?.onDisconnected()
        }
    }

    private var internetConnectivity: InternetConnectivity? = null
    private var connectivityManager: ConnectivityManager? = null

    fun instance(context: Context): InternetConnectivityObserver {
        connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        return InternetConnectivityObserver
    }

    fun setCallback(internetConnectivity: InternetConnectivity): InternetConnectivityObserver {
        this.internetConnectivity = internetConnectivity
        return InternetConnectivityObserver
    }

    fun build() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager?.requestNetwork(networkRequest, networkCallback)
    }
}
```

3. Download the **IConnectivityListener** and include it in your Android Project. Or copy:

```kotlin
interface InternetConnectivity {
    fun onConnected()
    fun onDisconnected()
}
```

4. Integrate the **InternetConnectivityObserver** class in your _activity_:
   
```kotlin
class MainActivity : AppCompatActivity(), InternetConnectivity {
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure the InternetConnectivityObserver
        InternetConnectivityObserver
            .instance(this)
            .setCallback(this)
            .build()
    }

    override fun onConnected() {
        // Action when connected to the internet
        // Example: Display a Snackbar or Toast
    }

    override fun onDisconnected() {
        // Action when disconnected from the internet
        // Example: Display a Snackbar or Toast
    }
}
```
Customize the **onConnected()** and **onDisconnected()** actions according to your app´s needs.

## Connectivity Demonstation
_When connected to the internet, a Snackbar is displayed. If disconnected, a Toast appears._

![Demonstração de Conectividade](https://github.com/AndreaAFonseca/NetWatcher/blob/master/conexaoInternet.gif)

### Contributing
Feel free to contribute with improvements, bug fixes, or new features. Open an issue or submit a pull request!

### License
This project is licensed under the MIT License. Read the [LICENSE](https://github.com/AndreaAFonseca/NetWatcher/blob/master/LICENSE) 
file for details on rights and limitations.
