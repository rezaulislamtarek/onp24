package com.onp24.onlinenewsportal24.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.onp24.onlinenewsportal24.model.data.News
import com.onp24.onlinenewsportal24.R
import com.onp24.onlinenewsportal24.databinding.RvNavItemBinding
import com.onp24.onlinenewsportal24.databinding.RvNewsBinding
import com.onp24.onlinenewsportal24.model.data.Categories
import com.onp24.onlinenewsportal24.util.RvItemClickListener
import com.onp24.onlinenewsportal24.util.RvNavItemClickListener

class CategoryAdapter(private val data: Array<Categories>, val listener: RvNavItemClickListener) :
    RecyclerView.Adapter<CategoryAdapter.NewsViewHolder>() {
    class NewsViewHolder(val rvBinding: RvNavItemBinding) : RecyclerView.ViewHolder(rvBinding.root)
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        context = parent.context
        return NewsViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.rv_nav_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.rvBinding.category = data[position]
        holder.rvBinding.tv.setOnClickListener {
           listener.navItemClick(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}