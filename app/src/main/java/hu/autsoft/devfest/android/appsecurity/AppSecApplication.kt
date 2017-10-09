package hu.autsoft.devfest.android.appsecurity

import android.app.Application
import hu.autsoft.devfest.android.appsecurity.utils.random.PRNGFixes
import io.realm.Realm
import org.spongycastle.jce.provider.BouncyCastleProvider
import java.security.Security

class AppSecApplication : Application() {

    companion object {
        init {
            Security.insertProviderAt(BouncyCastleProvider(), 1)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializeRandomNumberFix()
        Realm.init(this)
    }

    private fun initializeRandomNumberFix() {
        PRNGFixes.apply()
    }

}
