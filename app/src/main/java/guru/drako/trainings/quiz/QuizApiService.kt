package guru.drako.trainings.quiz

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuizApiService {
  @GET("api.php?type=multiple&encode=url3986&amount=1")
  fun getRandomQuestion(
    // @Query("amount") amount: Int = 10,
    @QueryMap options: Map<String, String> = mapOf()
  ): Call<QuestionList>
}
