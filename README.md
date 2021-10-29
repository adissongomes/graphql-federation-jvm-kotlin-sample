# Graphql Federation Sample

This graphql project is using [Apollo Federation](https://www.apollographql.com/docs/federation/) for a simple book catalog application.

**Projects**
- book-graphql (Kotlin, Springboot, Federation JVM) [book graphql schema](book-graphql/src/main/resources/graphql/query.graphqls)
- author-graphql (Kotlin, Springboot, Federation JVM) [author graphql schema](author-graphql/src/main/resources/graphql/query.graphqls)
- gateway (Node, Graphql, Apollo Federation)

The *book-graphql* and *author-graphql* are independent microservices which should be federated
to generate a final graph that will be exposed by gateway allowing to query the book and its author data in a single query.

```graphql
{
    books {
        id
        title
        author {
            id
            name
        }
    }
}
```