package com.onp24.onlinenewsportal24.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onp24.onlinenewsportal24.model.data.Categories
import com.onp24.onlinenewsportal24.model.data.MainModel
import com.onp24.onlinenewsportal24.model.data.News
import com.onp24.onlinenewsportal24.repository.NewsHomeRepository
import com.onp24.onlinenewsportal24.util.OperationCallBack

import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class NewsHomeViewModel(private val repository: NewsHomeRepository) : ViewModel() {
    var newsDrtList = MutableLiveData<Array<News>>()
    var newsDTopList = MutableLiveData<Array<News>>()
    private var newsJatioList = MutableLiveData<Array<News>>()
    var allNewsList = MutableLiveData< Array<MainModel>>()
    var categories = MutableLiveData< Array<Categories>>()
    var listener : OperationCallBack? = null


    init {
        GlobalScope.launch {
            getNewsOperation()
        }
    }

    private suspend fun getNewsOperation(){
        var drt: Array<News>
        var dTop: Array<News>
        var dJatio: Array<News>
        var allMainNews: Array<MainModel> = arrayOf()

        with(Dispatchers.IO) {

            try {


                var doc = getDocumentFromUrl("https://www.onp24.com/")

                // Top News
                var docDTopNewsList = doc.body().getElementsByClass("DTopNewsList")
                dTop = repository.getDTopNewsList(docDTopNewsList, doc)


                // Fetcher news
                var docDrtNewsList = doc.body().getElementsByClass("DTRNewsList").html()
                drt = repository.getDTRNewsList(docDrtNewsList)
                dTop += drt
                newsDrtList.postValue(dTop)
                newsDTopList.postValue(drt)


                // Jatio news
                var docJatioNewsList = doc.body().getElementsByClass("DCategoryNews")
                dJatio = repository.getNewsByCategory(docJatioNewsList, 0)
                newsJatioList.postValue(dJatio)
                allMainNews += MainModel("জাতীয়", dJatio)


                //2 rajniti 3 islam 8 special
                dJatio = repository.getNewsByCategory(docJatioNewsList, 2)
                newsJatioList.postValue(dJatio)
                allMainNews += MainModel("রাজনীতি", dJatio)

                //islam
                dJatio = repository.getNewsByCategory(docJatioNewsList, 3)
                newsJatioList.postValue(dJatio)
                allMainNews += MainModel("ইসলাম", dJatio)

                //islam
                dJatio = repository.getNewsByCategory(docJatioNewsList, 8)
                newsJatioList.postValue(dJatio)
                allMainNews += MainModel("স্পেশাল", dJatio)

                allNewsList.postValue(allMainNews)

                //get all category
                val cat = repository.getAllCategory(doc.getElementsByClass("nav navbar-nav")[0])
                categories.postValue(cat)
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