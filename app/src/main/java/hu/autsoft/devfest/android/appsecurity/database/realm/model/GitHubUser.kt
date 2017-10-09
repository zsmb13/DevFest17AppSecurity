package hu.autsoft.devfest.android.appsecurity.database.realm.model

import io.realm.RealmObject

open class GitHubUser : RealmObject() {
    var data: String? = null
}