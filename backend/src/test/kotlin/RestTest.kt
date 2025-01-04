import de.mathes.kanban.backend.BackendApplication
import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.rest.BoardRepository
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus.OK


@SpringBootTest(classes = [BackendApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTest {

    @LocalServerPort
    var serverPort: Int = 0

    @MockBean
    lateinit var boardRepository: BoardRepository

    @BeforeEach
    fun setUp() {
        `when`(boardRepository.findAll()).thenReturn(listOf(Board("4712", "First Board", listOf())))
    }

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
            .body(`is`("""[{"id":"4712","name":"First Board"}]"""))
    }
}