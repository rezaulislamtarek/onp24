package com.onp24.onlinenewsportal24.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.onp24.onlinenewsportal24.model.data.MainModel
import com.onp24.onlinenewsportal24.R
import com.onp24.onlinenewsportal24.databinding.RvMainBinding
import com.onp24.onlinenewsportal24.util.RvItemClickListener
import com.onp24.onlinenewsportal24.util.RvMainAdapterClickListener

class MainAdapter(var data: Array<MainModel>, val listener: RvItemClickListener, val clickListener: RvMainAdapterClickListener) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    class MainViewHolder(val rvMainBinding: RvMainBinding) :
        RecyclerView.ViewHolder(rvMainBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_main,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.rvMainBinding.main = data[position]
        holder.rvMainBinding.adapter = NewsAdapterGrid(data[position].mainData, listener)
        holder.rvMainBinding.moreNews.setOnClickListener {
            clickListener.clickListener(data[position].title)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}