package com.example.practicaltest.di.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicaltest.di.app.scope.AppScoped
import javax.inject.Inject
import javax.inject.Provider

@AppScoped
class ViewModelFactory @Inject
constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    var creator: Provider<out ViewModel>? = creators[modelClass]
    if (creator == null) {
      for ((key, value) in creators) {
        if (modelClass.isAssignableFrom(key)) {
          creator = value
          break
        }
      }
    }
    if (creator == null) {
      throw IllegalArgumentException("unknown model class " + modelClass)
    }
    try {
      @Suppress("UNCHECKED_CAST")
      return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }

  }
}
