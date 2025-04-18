package de.mathes.kanban.backend

import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.model.User
import de.mathes.kanban.backend.model.UserRepository
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest(classes = [BackendApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiResourceTest {

    @LocalServerPort
    var serverPort: Int = 0

    @MockBean
    lateinit var userRepository: UserRepository

    private val passwordEncoder = BCryptPasswordEncoder()
    private val testBoard = Board("4712", "First Board", listOf())

    @BeforeEach
    fun setUp() {
        val user = User()
        user.name = "user"
        user.password = passwordEncoder.encode("password")
        user.boards = mutableListOf(testBoard)

        `when`(userRepository.findByName("user")).thenReturn(user)
        doAnswer { invocation ->
            return@doAnswer invocation.arguments[0] as User
        }.`when`(userRepository).save(any())
    }

    @Test
    fun `API resources are served`() {
        given()
            .`when`()
            .port(serverPort)
            .auth().preemptive().basic("user", "password")
            .get("/api/boards")
            .then()
            .statusCode(200)
            .body("size()", `is`(1))
            .body("[0].id", `is`("4712"))
            .body("[0].name", `is`("First Board"))
    }

    @Test
    fun `get single board returns correct board`() {
        given()
            .`when`()
            .port(serverPort)
            .auth().preemptive().basic("user", "password")
            .get("/api/board/4712")
            .then()
            .statusCode(200)
            .body("id", `is`("4712"))
            .body("name", `is`("First Board"))
    }

    @Test
    fun `create board returns updated board list`() {
        given()
            .`when`()
            .port(serverPort)
            .auth().preemptive().basic("user", "password")
            .post("/api/board/NewBoard")
            .then()
            .statusCode(200)
            .body("size()", `is`(2))
    }

    @Test
    fun `update board returns updated board`() {
        val updatedBoard = Board("4712", "Updated Board", listOf())
        
        given()
            .`when`()
            .port(serverPort)
            .auth().preemptive().basic("user", "password")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(updatedBoard)
            .put("/api/board/4712")
            .then()
            .statusCode(200)
            .body("id", `is`("4712"))
            .body("name", `is`("Updated Board"))
    }

    @Test
    fun `delete board removes the board`() {
        given()
            .`when`()
            .port(serverPort)
            .auth().preemptive().basic("user", "password")
            .delete("/api/board/4712")
            .then()
            .statusCode(200)
    }

    @Test
    fun `register new user returns 204`() {
        given()
            .`when`()
            .port(serverPort)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"name": "newuser", "password": "newpassword"}""")
            .post("/auth/register")
            .then()
            .statusCode(204)
    }

    @Test
    fun `login returns 204 for valid credentials`() {
        given()
            .`when`()
            .port(serverPort)
            .auth().preemptive().basic("user", "password")
            .get("/auth/login")
            .then()
            .statusCode(204)
    }

    @Test
    fun `config endpoint returns hasUsers status`() {
        `when`(userRepository.count()).thenReturn(1L)
        
        given()
            .`when`()
            .port(serverPort)
            .get("/api/config")
            .then()
            .statusCode(200)
            .body("hasUsers", `is`(true))
    }
} 