package com.compose.jreader.data.model

data class Book(
    var id: String? = null,
    var title: String? = null,
    var photoUrl: String? = null,
    var authors: String? = null,
    var notes: String? = null
) {

    companion object {

        fun getBooks(): List<Book> = listOf(
            Book(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            ),
            Book(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            ),
            Book(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            ),
            Book(
                "12",
                "Running",
                "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "Jacob",
                "Good, interesting one"
            )
        )

    }

}