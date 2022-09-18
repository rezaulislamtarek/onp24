package com.onp24.onlinenewsportal24.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onp24.onlinenewsportal24.repository.NewsHomeRepository

class NewsViewModelFactory(val repository: NewsHomeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsHomeViewModel(repository) as T
    }
}