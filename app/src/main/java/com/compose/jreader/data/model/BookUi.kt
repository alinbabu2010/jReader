package com.compose.jreader.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class BookUi(

    @Exclude
    var id: String = "",

    var title: String = "",

    @get:PropertyName("book_photo_url")
    @set:PropertyName("book_photo_url")
    var photoUrl: String = "",

    var authors: String = "",

    var description: String = "",

    var categories: String = "",

    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate: String = "",

    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount: Int = 0,
    var rating: Int = 0,

    @get:PropertyName("started_reading_at")
    @set:PropertyName("started_reading_at")
    var startedReading: Timestamp? = null,

    @get:PropertyName("finished_reading_at")
    @set:PropertyName("finished_reading_at")
    var finishedReading: Timestamp? = null,

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String = "",

    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId: String = "",

    var notes: String = ""

)