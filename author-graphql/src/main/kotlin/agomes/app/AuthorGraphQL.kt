package agomes.app

import agomes.domain.book.Author
import agomes.domain.book.AuthorService
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetcher
import graphql.schema.DataFetcherFactory
import graphql.schema.DataFetcherFactoryEnvironment
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class AuthorGraphQL(
    private val service: AuthorService
) : GraphQLQueryResolver {
    fun authors() = service.fetchAll()

    fun authorById(id: Int) = Mono.just(service.findById(id))

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthorGraphQL::class.java)
    }
}
