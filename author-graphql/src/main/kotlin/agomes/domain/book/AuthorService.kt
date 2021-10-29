package agomes.domain.book

interface AuthorService {
    fun fetchAll(): List<Author>
    fun findById(id: Int): Author
}
