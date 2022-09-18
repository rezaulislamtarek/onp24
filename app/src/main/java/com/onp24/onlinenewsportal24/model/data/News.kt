package com.onp24.onlinenewsportal24.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    var category:String,
    var title: String,
    var image: String,
    var detailsLink:String,
    var paragraph: String,
    var date: String = ""
) : Parcelable {
    constructor():this("","","","","")
}