package android.libs.stickheaders.model

import java.io.Serializable

data class StickyHeadEntity<T>(var data: T, val itemType: Int, var stickyHeadName: String) :
    Serializable

