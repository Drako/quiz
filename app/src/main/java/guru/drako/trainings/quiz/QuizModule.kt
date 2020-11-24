package guru.drako.trainings.quiz

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(ActivityComponent::class)
object QuizModule {
  @Provides
  fun provideRetrofit(converterFactory: Converter.Factory): Retrofit = Retrofit.Builder()
    .addConverterFactory(converterFactory)
    .baseUrl("https://opentdb.com/")
    .build()

  @Provides
  fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

  @Provides
  fun provideQuizApiService(retrofit: Retrofit): QuizApiService = retrofit.create()
}
