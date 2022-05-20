package com.compose.jreader.data.model

data class MUser(
    val id: String?,
    val userId: String?,
    val displayName: String?
) {

    fun toMap(): MutableMap<String, String?> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName
        )
    }

}
