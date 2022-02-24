package wordle

import java.io.File

class WordGenerator(val wordLength: Int) {
  var wordMap: MutableMap<Int, MutableList<String>> = mutableMapOf()

  init {
    for (line in File("wordlist.txt").readLines()) {
      val len = line.length
      if (wordMap[len] == null) {
        wordMap[len] = mutableListOf()
      }
      wordMap[len]!!.add(line)
    }
  }

  fun generate(): String {
    val wordlist = wordMap[wordLength]
    if (wordlist == null) {
      return "ERROR"
    }
    return wordlist.random().uppercase()
  }

  // TODO: add a network call to implement this
  fun isValidWord(word: String): Boolean {
    return true
    /*
    val wordlist = wordMap[wordLength]
    if (wordlist == null) {
      println("wordlist not found")
      return false
    }
    return wordlist.contains(word)
    */
  }
}
