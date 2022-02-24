package wordle

fun main(args: Array<String>) {
  println("\u001b[37m")
  println("Welcome to Wordle")
  println()

  val wordLength = if (args.size >= 1) args[0].toInt() else 5
  println("Word length: ${wordLength}")
  val wordle = Wordle(wordLength)
  while (wordle.hasTurn()) {
    wordle.turn()
  }
}

