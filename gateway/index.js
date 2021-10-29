const { ApolloServer } = require('apollo-server')
const { ApolloGateway } = require('@apollo/gateway')
const { readFileSync } = require('fs')
const { ApolloServerPluginLandingPageGraphQLPlayground } = require('apollo-server-core');


const graphSdl = readFileSync('./supergraph.graphql').toString()

const gateway = new ApolloGateway({
    supergraphSdl: graphSdl
})

const server = new ApolloServer({
    gateway,
    plugins: [
        ApolloServerPluginLandingPageGraphQLPlayground()
    ]
})

server.listen().then((ctx) => {
    console.log(`ready at url ${ctx.url}`)
}).catch(e => console.error(e))