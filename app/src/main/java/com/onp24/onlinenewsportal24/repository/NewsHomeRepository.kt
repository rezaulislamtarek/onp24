package com.onp24.onlinenewsportal24.repository

import android.util.Log
import com.onp24.onlinenewsportal24.model.data.Categories
import com.onp24.onlinenewsportal24.model.data.News
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class NewsHomeRepository() {
    fun getDTRNewsList(htmlText: String): Array<News> {
        Log.d("html text", htmlText)
        val document = Jsoup.parse(htmlText)
        val rows = document.getElementsByClass("row")
        var res: Element
        var resource: Element
        var newResource: Element
        var detailsLink: String
        var image: String
        var title: String
        var news: Array<News> = arrayOf()

        for (row in rows) {
            res = row.getElementsByTag("div")[0]
            resource = res.getElementsByTag("a")[0]
            newResource = resource.getElementsByTag("img")[0]
            detailsLink = resource.attributes()["href"]
            image = newResource.attributes()["src"].replace("-thumb", "")
            title = newResource.attributes()["title"]
            Log.d("response", image)
            news += News("category", title, image, detailsLink, "")
        }
        return news
    }

    fun getDTopNewsList(docDTopNews: Elements, doc: Document): Array<News> {

        var resource: Element
        var newResource: Element
        var detailsLink: String
        var image: String
        var title: String
        var news: Array<News> = arrayOf()
        var topSingleNews = getDTopNews(doc);
        news += topSingleNews

        for (row in docDTopNews) {
            resource = row.getElementsByTag("a")[0]
            newResource = resource.getElementsByTag("img")[0]
            detailsLink = resource.attributes()["href"]
            image = newResource.attributes()["src"].replace("-SM", "")
            title = newResource.attributes()["title"]

            news += News("category", title, image, detailsLink, "")
        }

        return news
    }

    private fun getDTopNews(doc: Document): News {
        val topNews = doc.body().getElementsByClass("DTopNews")
        var newResource: Element
        var detailsLink: String
        var news: News

        var resource: Element = topNews[0].getElementsByTag("a")[0]
        newResource = resource.getElementsByTag("img")[0]
        detailsLink = resource.attributes()["href"]
        var image: String = newResource.attributes()["src"]
        var title: String = newResource.attributes()["title"]
        Log.d("response", image)
        news = News("category", title, image, detailsLink, "")

        return news
    }

    fun getNewsByCategory(elements: Elements, i: Int): Array<News> {
        var news: Array<News> = arrayOf()
        val content = elements[i].getElementsByClass("row")[0]

        //getting single jatio news
        val mainNews = content.getElementsByClass("DCatMainNews")
        var resourceMainNews = mainNews[0].getElementsByTag("a")[0]
        var newResourceMainNews = resourceMainNews.getElementsByTag("img")[0]


        val mNews = News(
            "category",
            newResourceMainNews.attributes()["title"],
            newResourceMainNews.attributes()["src"],
            resourceMainNews.attributes()["href"],
            ""
        )

        news += mNews


        val rows = content.getElementsByClass("DCategoryNewsList")
        var res: Element
        var resource: Element
        var newResource: Element
        var detailsLink: String
        var image: String
        var title: String

        for ((ind, row) in rows.withIndex()) {
            if (ind == 3) return news
            res = row.getElementsByTag("div")[0]
            resource = res.getElementsByTag("a")[0]
            newResource = resource.getElementsByTag("img")[0]
            detailsLink = resource.attributes()["href"]
            image = newResource.attributes()["src"].replace("-thumb", "")
            title = newResource.attributes()["title"]
            Log.d("response", image)
            news += News("category", title, image, detailsLink, "")
        }
        return news
    }

    fun getAllCategory(element: Element) : Array<Categories> {
        var data : Array<Categories> = arrayOf()
        var allA = element.getElementsByTag("a")
        for((ind,a) in allA.withIndex()){
            if(ind==0) continue
            var cat = a.text()
            var url = a.attributes()["href"]
            data+= Categories(cat,url)
        }
        return data
    }


}