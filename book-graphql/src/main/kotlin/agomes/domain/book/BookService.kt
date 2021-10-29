package agomes.domain.book

interface BookService {
    fun fetchAll(): List<BookDTO>
}
