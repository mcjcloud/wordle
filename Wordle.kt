package wordle

import java.util.Scanner

class Wordle(val wordLength: Int, val guessLimit: Int = 6) {
  val WORD_GENERATOR = WordGenerator(wordLength)
  val TARGET_WORD = WORD_GENERATOR.generate()

  var guesses: MutableList<String> = mutableListOf()
  var won = false

  fun turn() {
    if (!this.hasTurn()) {
      return
    }

    if (guesses.size == 0) {
      printBoard()
    }
    printBoard()

    var guess: String
    do {
      guess = prompt()
      val isValid = WORD_GENERATOR.isValidWord(guess)
      if (!isValid) {
        println("Invalid word")
      }
    } while (!isValid)
    guesses.add(guess)
    
    if (guess.equals(TARGET_WORD)) {
      printBoard()
      won = true
      println("You win!")
    } else if (!this.hasTurn()) {
      println("Game over :(")
      println("The word was: ${TARGET_WORD}")
    }
  }

  fun hasTurn(): Boolean {
    return !won && guesses.size < guessLimit
  }

  fun prompt(): String {
    var guess: String?
    do {
      print("> ")
      val input = readLine()?.uppercase()
      guess = validate(input)
    } while (guess == null)
    return guess
  }

  private fun printBoard() {
    print("\u001b[H\u001b[2J") // clear screen unix
    printSolidDivider()
    for (i in 0 until guessLimit) {
      val guess = if (i < guesses.size) guesses[i] else " ".repeat(wordLength)
      printWord(guess)
      if (i < guessLimit - 1) {
        printDivider()
      }
    }
    printSolidDivider()
  }

  private fun printWord(word: String) {
    val colors = getLetterColors(word)
    val white = "\u001b[37m"
    print("|")
    for (i in 0 until word.length) {
      print(" ${colors[i]}${word[i].uppercaseChar()}${white} ")
      if (i < word.length - 1) {
        print(" ")
      }
    }
    println("|")
  }

  private fun printDivider() {
    print("+")
    for (i in 0 until wordLength) {
      print("   +")
    }
    println()
  }

  private fun printSolidDivider() {
    print("+")
    for (i in 0 until wordLength) {
      print("---+")
    }
    println()
  }

  private fun validate(word: String?): String? {
    if (word == null) {
      println("word is null")
      return null
    }
    if (word.length != wordLength) {
      println("word must be of length ${wordLength}")
      return null
    }
    return word
  }

  private fun getLetterColors(word: String): Array<String> {
    val result = Array<String>(word.length) { "" }
    var occuranceCount: MutableMap<Char, Int> = mutableMapOf()
    for (i in 0 until word.length) {
      occuranceCount[word[i]] = 0
    }

    for (i in 0 until word.length) {
      val currOcc = occuranceCount.getOrDefault(word[i], 0)
      if (word[i].equals(TARGET_WORD[i])) {
        occuranceCount.put(word[i], currOcc + 1)
        result[i] = "\u001b[32m" // Green
      } else {
        result[i] = "\u001b[37m" // White
      }
    }

    for (i in 0 until word.length) {
      val currOcc = occuranceCount.getOrDefault(word[i], 0)
      if (currOcc < occurances(TARGET_WORD, word[i])) {
        occuranceCount.put(word[i], currOcc + 1)
        result[i] = "\u001b[33m" // Yellow
      }
    }
    return result
  }

  private fun occurances(s: String, ch: Char): Int {
    val n =  s.filter { it == ch }.count()
    return n
  }
}

