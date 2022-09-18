package com.onp24.onlinenewsportal24.repository

import com.onp24.onlinenewsportal24.model.data.DetailsNews
import org.jsoup.select.Elements

class NewsDetailsRepository {
    fun getDetailsNews(ele: Elements): DetailsNews? {
        val view = ele[0].getElementsByTag("span")[0].text()

        val published = ele[0].getElementsByClass("DDateDetails")[0].text()

        val detailsBody = ele[0].getElementsByClass("DDetailsBody")[0].text()
        val detailsNews = DetailsNews(published,view,detailsBody)
        return detailsNews
    }
}