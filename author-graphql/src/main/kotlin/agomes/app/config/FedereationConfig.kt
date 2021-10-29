package agomes.app.config

import agomes.domain.book.Author
import agomes.domain.book.AuthorService
import com.apollographql.federation.graphqljava.Federation
import com.apollographql.federation.graphqljava.SchemaTransformer
import com.apollographql.federation.graphqljava._Entity
import com.apollographql.federation.graphqljava.tracing.FederatedTracingInstrumentation
import graphql.Scalars
import graphql.kickstart.tools.SchemaParser
import graphql.kickstart.tools.SchemaParserDictionary
import graphql.kickstart.tools.SchemaParserOptions
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.validation.constraints.NotNull


@Configuration
class FedereationConfig {
    @Bean
    fun schemaParserDictionary(): SchemaParserDictionary? {
        return SchemaParserDictionary()
            .add("Author", Author::class.java)
    }

    @Bean
    fun schemaParserOptionsBuilderPostProcessor(): BeanPostProcessor? {
        return object : BeanPostProcessor {
            @Throws(BeansException::class)
            override fun postProcessAfterInitialization(@NotNull bean: Any, @NotNull beanName: String): Any? {
                return if (bean is SchemaParserOptions.Builder) (bean as SchemaParserOptions.Builder).includeUnusedTypes(
                    true
                ) else bean
            }
        }
    }

    @Bean
    fun schemaTransformer(schemaParser: SchemaParser): SchemaTransformer? {
        val (query, mutation, subscription, dictionary, codeRegistryBuilder) = schemaParser.parseSchemaObjects()
        val queryTypeIsEmpty = query.fieldDefinitions.isEmpty()
        val newQuery = if (queryTypeIsEmpty) query
            .transform { graphQLObjectTypeBuilder: GraphQLObjectType.Builder ->
                graphQLObjectTypeBuilder.field(
                    GraphQLFieldDefinition.newFieldDefinition()
                        .name("_dummy")
                        .type(Scalars.GraphQLString)
                        .build()
                )
            } else query
        val graphQLSchema = GraphQLSchema.newSchema()
            .query(newQuery)
            .mutation(mutation)
            .subscription(subscription)
            .additionalTypes(dictionary)
            .codeRegistry(codeRegistryBuilder.build())
            .build()
        return Federation.transform(graphQLSchema, queryTypeIsEmpty)
    }

    @Bean
    fun graphQLSchema(schemaTransformer: SchemaTransformer, federationResolver: FederationResolver): GraphQLSchema? {
        return schemaTransformer
            .fetchEntities {
                it.getArgument<List<Map<String, Any>>>(_Entity.argumentName).map {
                    if ("Author" == it["__typename"])
                        return@map federationResolver.author(it["id"].toString().toInt())
                    return@map null
                }
            }
            .resolveEntityType ret@ {
                val src = it.getObject<Any>()
                if (src is Author) {
                    return@ret it.schema.getObjectType("Author")
                }
                return@ret null
            }
            .build()
    }

    @Bean
    fun federatedTracingInstrumentation(): FederatedTracingInstrumentation? {
        return FederatedTracingInstrumentation(FederatedTracingInstrumentation.Options(true))
    }
}

@Component
class FederationResolver(
    private val service: AuthorService
) {
    fun author(id: Int) = service.findById(id)
}