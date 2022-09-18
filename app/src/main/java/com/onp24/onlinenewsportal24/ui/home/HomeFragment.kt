package com.onp24.onlinenewsportal24.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.onp24.onlinenewsportal24.R
import com.onp24.onlinenewsportal24.adapter.MainAdapter
import com.onp24.onlinenewsportal24.adapter.NewsAdapter
import com.onp24.onlinenewsportal24.databinding.FragmentHomeBinding
import com.onp24.onlinenewsportal24.model.data.Categories
import com.onp24.onlinenewsportal24.model.data.News
import com.onp24.onlinenewsportal24.repository.NewsHomeRepository
import com.onp24.onlinenewsportal24.util.*

class HomeFragment : Fragment(), RvItemClickListener, RvMainAdapterClickListener, OperationCallBack {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var model: NewsHomeViewModel
    private lateinit var categoryList: Array<Categories>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        var isNet: Boolean = activity?.isInternetAvailable()!!
        checkInternetAndAction(isNet)
        val repository = NewsHomeRepository()
        val factory = NewsViewModelFactory(repository)
        val listener: RvItemClickListener = this
        val listener2: RvMainAdapterClickListener = this
        binding.lifecycleOwner = this
        if(isNet){
            model = ViewModelProvider(this, factory).get(NewsHomeViewModel::class.java)
            model.listener = this
            model.newsDrtList.observe(viewLifecycleOwner, Observer { news ->
                binding.viewPager.also {
                    it.adapter = NewsAdapter(news,listener)
                    it?.clipToPadding = false
                    it?.clipChildren = false
                    it?.offscreenPageLimit = 1
                    it?.getChildAt(0)?.overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER
                    val compositePageTransformer = CompositePageTransformer()
                    compositePageTransformer.addTransformer(MarginPageTransformer(0))
                    compositePageTransformer.addTransformer { page, position ->
                        val r: Float = 1 - Math.abs(position)
                        page.scaleY = 0.85f + r * 0.15f
                        //page.scaleX = 0.85f + r * 0.15f
                    }
                    it?.setPageTransformer(compositePageTransformer)
                }
            })
            model.allNewsList.observe(viewLifecycleOwner, Observer {
                binding.sp.rlSpl.hide()
                binding.rvNews.apply {
                    Log.d("it size", it.size.toString())
                    adapter = MainAdapter(it,listener,listener2)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            })
            model.categories.observe(viewLifecycleOwner, Observer {
                categoryList = it
            })
        }

        binding.apply {
            navMenu.setOnClickListener{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavDrawerFragment())
            }
        }
        return binding.root
    }

    override fun rvItemClick(news: News) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(news))
    }
    private fun checkInternetAndAction(available: Boolean) {
        if(!available){
            binding.apply {
                llRoot.hide()
                sp.rlSpl.hide()
                internet.noInternetConnection.show()
                internet.tryAgain.setOnClickListener {
                    val navController = findNavController()
                    navController.run {
                        popBackStack()
                        navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }

    override fun clickListener(type: String) {
        for(cat in categoryList){
            Log.d("CAT",cat.categoryName)
            if(cat.categoryName == type) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoryNewsFragment(cat))
            }
        }
    }

    override fun onError(s: String) {
        binding.llRoot.toastSnack(s)
    }


}