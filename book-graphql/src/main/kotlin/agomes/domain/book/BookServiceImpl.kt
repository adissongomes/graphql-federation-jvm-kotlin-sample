package agomes.domain.book

import org.springframework.stereotype.Service
import java.util.*

@Service
class BookServiceImpl : BookService {

    private val data: MutableMap<Int, Book> = mutableMapOf()

    init {
        data.put(1, Book(1, "Refactoring", 1))
        data.put(2, Book(2, "Operating systems", 2))
        data.put(3, Book(3 , "DDD", 3))
    }

    override fun fetchAll() = data.values.map { it.toDto() }
}