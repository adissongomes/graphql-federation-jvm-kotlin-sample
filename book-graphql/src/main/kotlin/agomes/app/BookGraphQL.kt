package agomes.app

import agomes.domain.book.BookService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class BookGraphQL(
    private val service: BookService
): GraphQLQueryResolver {

    fun books() = service.fetchAll()
}