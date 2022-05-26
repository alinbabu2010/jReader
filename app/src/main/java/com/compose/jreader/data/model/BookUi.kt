package com.compose.jreader.data.model

data class BookUi(
    var id: String = "",
    var title: String = "",
    var photoUrl: String = "",
    var authors: String = "",
    var description: String = "",
    var categories: String = "",
    var publishedDate: String = "",
    var pageCount: Int = 0
) {

    companion object {

        fun getBooks(): List<BookUi> = listOf(
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                description = "Good, interesting one"
            ),
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                description = "Good, interesting one"
            ),
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                description = "Good, interesting one"
            ),
            BookUi(
                id = "12",
                title = "Running",
                photoUrl = "http://books.google.com/books/content?id=Wu5qDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                authors = "Jacob",
                description = "Good, interesting one"
            )
        )

    }

}