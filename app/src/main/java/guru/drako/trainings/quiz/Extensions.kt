package guru.drako.trainings.quiz

import android.util.Log

inline fun <reified C> simpleName(): String = C::class.java.simpleName

inline fun <reified TagType> TagType.logError(message: String, exception: Throwable? = null) {
  if (exception != null) {
    Log.e(simpleName<TagType>(), message, exception)
  } else {
    Log.e(simpleName<TagType>(), message)
  }
}

inline fun <reified TagType> TagType.logInfo(message: String, exception: Throwable? = null) {
  if (exception != null) {
    Log.i(simpleName<TagType>(), message, exception)
  } else {
    Log.i(simpleName<TagType>(), message)
  }
}
