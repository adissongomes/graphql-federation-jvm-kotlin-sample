package agomes.domain.book

import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl : AuthorService {

    private val data: MutableMap<Int, Author> = mutableMapOf()

    init {
        data.put(1, Author(1, "Fowler"))
        data.put(2, Author(2, "Tanenbaum"))
        data.put(3, Author(3, "Evans"))
    }

    override fun fetchAll() = data.values.toList()

    override fun findById(id: Int) = data[id]!!
}