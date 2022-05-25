package com.compose.jreader.data.model

data class BookUi(
    var id: String = "",
    var title: String = "",
    var photoUrl: String = "",
    var authors: String = "",
    var notes: String = "",
    var categories: String = "",
    var publishedDate: String = "",
) {

    companion object {

        fun getBooks(): List<BookUi> = listOf(
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                notes = "Good, interesting one"
            ),
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                notes = "Good, interesting one"
            ),
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                notes = "Good, interesting one"
            ),
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                notes = "Good, interesting one"
            )
        )

    }

}