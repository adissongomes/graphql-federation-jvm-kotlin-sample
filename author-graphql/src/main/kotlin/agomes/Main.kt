package agomes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping

@SpringBootApplication
class App


fun main(args: Array<String>) {
    runApplication<App>(*args)
}