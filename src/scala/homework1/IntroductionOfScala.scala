package scala.scala4

object First extends App {
  val age : Int = 17
  val weight : Double = 53
  val name : String = "Alice"
  val isStudent : Boolean = true

  println(age)
  println(weight)
  println(name)
  println(isStudent)

  val add = (x: Int, y: Int) => { x + y }
  println(add(2, 4))

  val amIYoung = (age: Int) =>  if (age > 30) "Old" else "Young"
  println(amIYoung(29))

  for (i <- 1 to 10) {
    println(i)
  }

  val studentNames = List("Alice", "Bob", "Andrew")
  studentNames.foreach(name => println(name))

  println("Enter you age")
  val userAge = scala.io.StdIn.readInt()
  var young = (amIYoung(userAge))

  println("Enter you name")
  val userName = scala.io.StdIn.readLine()
  println(s"hi, i am $userName and i am $userAge years old. I am really $young")

  val numbers = List(1,2,3,4,5,6,7,8,9,10)
  val doubled = numbers.map(x => x*x);

  println(doubled)

  val evenNumbers = for {
    number <- numbers if number % 2 == 0
  } yield (number)

  println(evenNumbers)
}

