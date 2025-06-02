package it.antonino.dinnerdecideradmin.net

import android.R
import android.content.Context
import it.antonino.dinnerdecideradmin.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


object Network {
    private var retrofit: Retrofit? = null

    fun getInstance(context: Context): Retrofit {
        if (retrofit == null) {
            try {
                // Carica il certificato dal file res/raw/my_cert.crt
                val cf = CertificateFactory.getInstance("X.509")
                val caInput = context.resources.openRawResource(it.antonino.dinnerdecideradmin.R.raw.cert)
                var ca: Certificate?
                try {
                    ca = cf.generateCertificate(caInput)
                } finally {
                    caInput.close()
                }

                // Crea un KeyStore e ci mette dentro il certificato
                val keyStoreType = KeyStore.getDefaultType()
                val keyStore = KeyStore.getInstance(keyStoreType)
                keyStore.load(null, null)
                keyStore.setCertificateEntry("my_ca", ca)

                // Crea un TrustManager che usa il KeyStore con il tuo certificato
                val tmf = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm()
                )
                tmf.init(keyStore)

                // Crea il contesto SSL
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, tmf.trustManagers, SecureRandom())

                // Crea un client OkHttp con SSL personalizzato
                val okHttpClient = OkHttpClient.Builder()
                    .sslSocketFactory(
                        sslContext.socketFactory,
                        tmf.trustManagers[0] as X509TrustManager
                    )
                    .hostnameVerifier { hostname: String?, session: SSLSession? -> true } // ⚠️ Disabilita verifica hostname per IP
                    .build()

                // Costruisce Retrofit
                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.baseUrl) // o altro IP/nome coerente con il certificato
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            } catch (e: Exception) {
                e.printStackTrace()
                throw RuntimeException("Errore nella configurazione SSL", e)
            }
        }
        return retrofit!!
    }
}
