

object MainApp extends ZIOAppDefault {
	def run =
		for {
			_ <- printLine("Please enter your name: ")
			name <- readLine
			_ <- printLine(s"Hello, $name!")
		} yield ()
}