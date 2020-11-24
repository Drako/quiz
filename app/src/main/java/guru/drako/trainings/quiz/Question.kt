package guru.drako.trainings.quiz

import com.google.gson.annotations.SerializedName
import java.net.URLDecoder

enum class AnswerType {
  Correct,
  Incorrect
}

data class Question(
  val question: String,
  @field:SerializedName("correct_answer")
  val correctAnswer: String,
  @field:SerializedName("incorrect_answers")
  val incorrectAnswers: List<String>
) {
  private fun String.urlDecode(): String = URLDecoder.decode(this, "UTF-8")

  val decodedQuestion: String
    get() = question.urlDecode()

  val answers: List<Pair<String, AnswerType>>
    get() = (incorrectAnswers.asSequence().map { it to AnswerType.Incorrect } + (correctAnswer to AnswerType.Correct))
      .map { (text, type) -> text.urlDecode() to type }
      .shuffled()
      .toList()

  override fun toString(): String {
    return "[question=\"$decodedQuestion\"" +
        ", correctAnswer=\"${correctAnswer.urlDecode()}\"" +
        ", incorrectAnswers=${incorrectAnswers.map { "\"" + it.urlDecode() + "\"" }}" +
        "]"
  }
}

data class QuestionList(
  val results: List<Question>
)
