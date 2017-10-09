package hu.autsoft.devfest.android.appsecurity.utils.base64

import android.util.Base64


fun ByteArray.base64() = Base64.encodeToString(this, Base64.NO_WRAP)
fun String.asBase64() = Base64.decode(this, Base64.NO_WRAP)

