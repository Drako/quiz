package guru.drako.trainings.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule


@AndroidEntryPoint(AppCompatActivity::class)
class MainActivity : Hilt_MainActivity(), View.OnClickListener {
  val answers = mutableListOf<Button>()

  @Inject
  lateinit var quizApiService: QuizApiService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    answers.clear()
    answers += sequenceOf(answer1, answer2, answer3, answer4)

    answers.forEach { button ->
      button.setOnClickListener(this)
    }

    loadQuestion()
  }

  private fun loadQuestion() {
    quizApiService.getRandomQuestion().enqueue(object : Callback<QuestionList> {
      override fun onResponse(call: Call<QuestionList>, response: Response<QuestionList>) {
        val questionList = response.body() ?: return loadQuestion()
        val q = questionList.results.first()

        this@MainActivity.logInfo("Got question: $q")

        showLoadedQuestion(q)
      }

      override fun onFailure(call: Call<QuestionList>, t: Throwable) {
        this@MainActivity.logError("Could not get question.", t)
      }
    })
  }

  private fun showLoadedQuestion(q: Question) {
    question.text = q.decodedQuestion

    answers.asSequence().zip(q.answers.asSequence())
      .forEach { (button, answer) ->
        val (text, type) = answer
        button.isEnabled = true
        button.text = text
        button.tag = type
      }

    result.text = ""
  }

  override fun onClick(v: View) {
    if (v !is Button) {
      return
    }

    if (v.tag == AnswerType.Correct) {
      result.text = getString(R.string.right)
    } else {
      result.text = getString(R.string.wrong)
    }

    answers.forEach {
      it.isEnabled = false
    }

    Timer("reload-question-timer")
      .schedule(3000L) {
        loadQuestion()
      }
  }
}
