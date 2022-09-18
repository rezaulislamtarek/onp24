package com.onp24.onlinenewsportal24.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Categories(var categoryName: String, var url: String) : Parcelable
