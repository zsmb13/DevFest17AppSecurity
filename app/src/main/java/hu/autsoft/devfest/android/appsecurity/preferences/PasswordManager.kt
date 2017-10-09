package hu.autsoft.key.interator

import android.content.Context
import hu.autsoft.devfest.android.appsecurity.utils.base64.asBase64
import hu.autsoft.devfest.android.appsecurity.utils.base64.base64
import hu.autsoft.key.preferences.PlainPreferences
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class PasswordManager(val context: Context) {
    private val KEY_DERIVATION_ALG = "PBKDF2WithHmacSHA1"
    private val RANDOM_ALG = "SHA1PRNG"

    fun setDerivedPassword(tag: String, password: CharArray, keyLengthInBits: Int): ByteArray {
        val plainPrefs = PlainPreferences(context)

        val iv = generateRandom(keyLengthInBits)
        val derived = deriveKey(password, iv, keyLengthInBits)
        plainPrefs[tag] = iv.base64()
        return derived
    }

    fun getDerivedPassword(tag: String, password: CharArray, keyLengthInBits: Int): ByteArray {
        val plainPrefs = PlainPreferences(context)

        val iv: String = plainPrefs[tag]!!
        val derived = deriveKey(password, iv.asBase64(), keyLengthInBits)
        return derived
    }

    fun isPasswordSet(tag: String): Boolean {
        val plainPrefs = PlainPreferences(context)
        val ivStr: String? = plainPrefs[tag]
        return ivStr != null
    }

    fun removePassword(tag: String) {
        val plainPrefs = PlainPreferences(context)
        val nothing: String? = null
        plainPrefs[tag] = nothing
    }


    private fun deriveKey(password: CharArray, iv: ByteArray, keyLengthInBits: Int): ByteArray {
        val iterationCount = 1000
        val keySpec = PBEKeySpec(password, iv, iterationCount, keyLengthInBits)
        val keyFactory = SecretKeyFactory.getInstance(KEY_DERIVATION_ALG)
        val keyBytes = keyFactory.generateSecret(keySpec).encoded
        return keyBytes
    }

    private fun generateRandom(lengthInBites: Int): ByteArray {
        val random = SecureRandom.getInstance(RANDOM_ALG)
        val genBytes = ByteArray(lengthInBites / 8)
        random.nextBytes(genBytes)
        return genBytes
    }

}
