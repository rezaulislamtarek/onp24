package com.onp24.onlinenewsportal24.adapter

import android.util.Log
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("image")
fun setImage(view: ImageView, url: String ){
    Log.d("Image",url)
    Glide.with(view).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(view)
}

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter( "loadData")
fun WebView.loadData( data:String){
   loadDataWithBaseURL(null,data,"text/html", "UTF-8", null);
}