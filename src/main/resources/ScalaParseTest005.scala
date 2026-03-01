class ParenTest private (initialValA: Int = 0, initialValB: Int = 0) {

	def printTwice[T](value: T): Unit = {
		println(value)
		println(value)
	}

	printTwice[Int](42)
	printTwice[String]("hello")

	def add(a: Int)(b: Int): Int = a + b

	implicit val defaultFactor: Int = 3

	def multiply(x: Int)(implicit factor: Int): Int = factor * x


	printTwice[Int](42)
	printTwice[String]("hello")
	println(add(2)(3)) // 5
	println(multiply(4)) // 12
}
