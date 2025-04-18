package de.mathes.kanban.backend

import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.model.User
import de.mathes.kanban.backend.model.UserRepository
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest(classes = [BackendApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiResourceTest {

    @LocalServerPort
    var serverPort: Int = 0

    @MockBean
    lateinit var userRepository: UserRepository

    private val passwordEncoder = BCryptPasswordEncoder()

    @BeforeEach
    fun setUp() {
        val user = User()
        user.name = "user"
        user.password = passwordEncoder.encode("password")
        user.boards = mutableListOf(
            Board("4712", "First Board", listOf())
        )

        `when`(userRepository.findByName("user")).thenReturn(user)
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
} 