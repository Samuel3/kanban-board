import de.mathes.kanban.backend.BackendApplication
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus.OK


@SpringBootTest(classes=[BackendApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTest {

    @LocalServerPort
    var serverPort: Int = 0

    @ParameterizedTest
    @ValueSource(strings = ["", "/", "/list"])
    fun `Static resources are served`(path: String) {
        given()
            .`when`()
            .port(serverPort)
            .get(path)
            .then()
            .statusCode(OK.value())
            .body(`is`("<!DOCTYPE html><html><body><h1>Static Test Resource</h1></body></html>"))
    }

    @Test
    fun `API resources are served`() {
        given()
            .`when`()
            .port(serverPort)
            .get("/api/boards")
            .then()
            .statusCode(OK.value())
            .body(`is`("[]"))
    }
}