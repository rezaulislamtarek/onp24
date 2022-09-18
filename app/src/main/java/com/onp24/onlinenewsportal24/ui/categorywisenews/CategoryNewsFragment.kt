package com.onp24.onlinenewsportal24.ui.categorywisenews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.onp24.onlinenewsportal24.MainActivity
import com.onp24.onlinenewsportal24.R
import com.onp24.onlinenewsportal24.adapter.CatNewsAdapter
import com.onp24.onlinenewsportal24.databinding.FragmentCategorywizeNewsBinding
import com.onp24.onlinenewsportal24.model.data.News
import com.onp24.onlinenewsportal24.repository.NewsCategoryRepository
import com.onp24.onlinenewsportal24.util.*

class CategoryNewsFragment : Fragment(), RvItemClickListener,OperationCallBack {

    lateinit var model: CategoryNewsViewModel
    lateinit var binding: FragmentCategorywizeNewsBinding
    val args: CategoryNewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategorywizeNewsBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val repository = NewsCategoryRepository()
        val rvItemClickListener: RvItemClickListener = this
        val factory = CatNewsFactory(repository, args.category.url)
        var isNet: Boolean = activity?.isInternetAvailable()!!
        checkInternetAndAction(isNet)
        if (isNet) {
            model = ViewModelProvider(this, factory).get(CategoryNewsViewModel::class.java)
            model.listener = this
            model.news.observe(viewLifecycleOwner, Observer {
                binding.sp.rlSpl.hide()
                binding.internet.noInternetConnection.hide()
                binding.rvNews.apply {
                    adapter = CatNewsAdapter(it, rvItemClickListener)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            })
        }
        binding.category = args.category
        binding.lifecycleOwner = this


        binding.apply {
            tb.activity = activity as MainActivity
            tb.toolbarTitle.text = args.category.categoryName
        }

    }

    override fun rvItemClick(news: News) {
        findNavController().navigate(
            CategoryNewsFragmentDirections.actionCategoryNewsFragmentToDetailsFragment(
                news
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        findNavController().popBackStack(R.id.homeFragment, false)
    }

    private fun checkInternetAndAction(available: Boolean) {
        if (!available) {
            binding.apply {
                // llRoot.hide()
                sp.rlSpl.hide()
                internet.noInternetConnection.show()
                internet.tryAgain.setOnClickListener {
                    //binding.sp.rlSpl.show()
                    onResume()
                }
            }
        }
    }

    override fun onError(s: String) {
        binding.rlRoot.toastSnack(s)
    }

}