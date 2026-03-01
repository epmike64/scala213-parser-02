package claude


import zio._
import zio.test._

object UserServiceSpec extends ZIOSpecDefault {

	def spec = suite("UserService")(
		test("should create a user") {
			for {
				request <- ZIO.succeed(CreateUserRequest("Alice", "alice@example.com"))
				response <- UserService.createUser(request)
			} yield assertTrue(
				response.name == "Alice",
				response.email == "alice@example.com",
				response.status == "created"
			)
		},

		test("should get a user by id") {
			for {
				request <- ZIO.succeed(CreateUserRequest("Bob", "bob@example.com"))
				created <- UserService.createUser(request)
				retrieved <- UserService.getUser(created.id)
			} yield assertTrue(
				retrieved.isDefined,
				retrieved.get.name == "Bob"
			)
		},

		test("should return None for non-existent user") {
			for {
				result <- UserService.getUser(999L)
			} yield assertTrue(result.isEmpty)
		}
	).provide(UserService.live)
}