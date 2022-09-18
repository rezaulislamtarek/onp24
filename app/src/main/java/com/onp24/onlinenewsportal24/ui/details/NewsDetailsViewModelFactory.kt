package com.onp24.onlinenewsportal24.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onp24.onlinenewsportal24.repository.NewsDetailsRepository

class NewsDetailsViewModelFactory(val  repository: NewsDetailsRepository, val url : String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsDetailsViewModel(repository, url) as T
    }
}