package com.compose.jreader.data.model

data class BookUi(
    var id: String? = null,
    var title: String? = null,
    var photoUrl: String? = null,
    var authors: String? = null,
    var notes: String? = null,
    var publishedDate: String? = null
) {

    companion object {

        fun getBooks(): List<BookUi> = listOf(
            BookUi(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            ),
            BookUi(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            ),
            BookUi(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            ),
            BookUi(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            )
        )

    }

}