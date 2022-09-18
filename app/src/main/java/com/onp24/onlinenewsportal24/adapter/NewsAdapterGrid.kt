package com.onp24.onlinenewsportal24.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.onp24.onlinenewsportal24.model.data.News
import com.onp24.onlinenewsportal24.R
import com.onp24.onlinenewsportal24.databinding.RvNewsGridBinding
import com.onp24.onlinenewsportal24.util.RvItemClickListener

class NewsAdapterGrid(private val data: Array<News>, val listener: RvItemClickListener) :
    RecyclerView.Adapter<NewsAdapterGrid.NewsViewHolder>() {

    class NewsViewHolder(val rvNewsBinding: RvNewsGridBinding) :
        RecyclerView.ViewHolder(rvNewsBinding.root)

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        context = parent.context
        return NewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_news_grid,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.rvNewsBinding.news = data[position]

        holder.rvNewsBinding.llRoot.setOnClickListener {
            listener.rvItemClick(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}