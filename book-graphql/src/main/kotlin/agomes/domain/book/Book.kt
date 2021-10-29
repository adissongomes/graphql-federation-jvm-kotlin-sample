package agomes.domain.book

data class Book(
    val id: Int,
    val title: String,
    val authorId: Int,
)

data class BookDTO(
    val id: Int,
    val title: String,
    val author: AuthorDTO,
)

data class AuthorDTO(
    val id: Int,
)

fun Book.toDto() = BookDTO(id, title, AuthorDTO(authorId))