package com.onp24.onlinenewsportal24.ui.categorywisenews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onp24.onlinenewsportal24.repository.NewsCategoryRepository

class CatNewsFactory(val repository: NewsCategoryRepository, val  url: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryNewsViewModel(repository, url) as T
    }
}
