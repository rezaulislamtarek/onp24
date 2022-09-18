package com.onp24.onlinenewsportal24.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onp24.onlinenewsportal24.model.data.DetailsNews
import com.onp24.onlinenewsportal24.model.data.MainModel
import com.onp24.onlinenewsportal24.model.data.News
import com.onp24.onlinenewsportal24.repository.NewsDetailsRepository
import com.onp24.onlinenewsportal24.repository.NewsHomeRepository
import com.onp24.onlinenewsportal24.util.OperationCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class NewsDetailsViewModel(private val repository: NewsDetailsRepository, private val url: String) :
    ViewModel() {
    var newsDetails = MutableLiveData<DetailsNews>()
    var listener: OperationCallBack? = null

    init {
        GlobalScope.launch {
            getNewsOperation()
        }
    }

    private suspend fun getNewsOperation() {
        with(Dispatchers.IO) {
            try {
                var doc = getDocumentFromUrl(url)
                var ele = doc.body().getElementsByClass("DDetailsNews")
                var details = repository.getDetailsNews(ele)
                newsDetails.postValue(details!!)
            }catch (e: Exception){
                listener?.onError(e.message.toString())
            }
        }
    }

    private fun getDocumentFromUrl(url: String): Document {
        val html = Jsoup.connect(url).get()
        return Jsoup.parse(html.toString())
    }
}