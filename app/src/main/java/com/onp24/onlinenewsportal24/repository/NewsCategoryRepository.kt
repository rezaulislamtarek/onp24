package com.onp24.onlinenewsportal24.repository

import android.util.Log
import com.onp24.onlinenewsportal24.model.data.News
import org.jsoup.select.Elements
import java.lang.Exception

class NewsCategoryRepository {
    fun getNews(ele: Elements): Array<News> {
        var news : Array<News> = arrayOf()
        val tNp = ele[0].getElementsByClass("DCategoryTopNews")[0].getElementsByClass("col-sm-5")[0].getElementsByTag("a")[0]
        val p = tNp.text()
        val oth = ele[0].getElementsByClass("DCategoryTopNews")[0].getElementsByClass("col-sm-7")[0].getElementsByTag("a")[0]
        val dUrl = oth.attributes()["href"]
        val tmpOth = oth.getElementsByTag("img")[0]
        val tmpImg = tmpOth.attr("src")
        val tit = tmpOth.attr("title")
        news += News(category = "",title = tit,image = tmpImg, detailsLink = dUrl, paragraph = p)

        //this is time to fetch all news list
        val rows = ele[0].getElementsByClass("DCategoryListNews")
        for(row in rows){
            val tmpAll = row.getElementsByTag("a")[0]
            val url = tmpAll.attr("href")
            val tmpAl2 = tmpAll.getElementsByTag("img")[0]
            val img = tmpAl2.attr("src")
            val title = tmpAl2.attr("title")
            var para: Elements
            var paragraph = ""
            var date = ""

            try {
                 para = row.getElementsByTag("p")
                 paragraph = para[0].text()
                date = para[1].text()
            }catch (e : Exception){}


            news += News(category = "",title = title,image = img, detailsLink = url, paragraph = paragraph, date = date)
        }


        return news

    }
}