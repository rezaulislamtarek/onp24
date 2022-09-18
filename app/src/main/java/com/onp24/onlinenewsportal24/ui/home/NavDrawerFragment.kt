package com.onp24.onlinenewsportal24.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.onp24.onlinenewsportal24.R
import com.onp24.onlinenewsportal24.adapter.CategoryAdapter
import com.onp24.onlinenewsportal24.databinding.FragmentNavDrawerBinding
import com.onp24.onlinenewsportal24.model.data.Categories
import com.onp24.onlinenewsportal24.repository.NewsHomeRepository
import com.onp24.onlinenewsportal24.util.*

class NavDrawerFragment : Fragment(), RvNavItemClickListener, OperationCallBack {
    lateinit var binding: FragmentNavDrawerBinding
    lateinit var model: NewsHomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNavDrawerBinding.inflate(inflater, container, false)
        val repository = NewsHomeRepository()
        val factory = NewsViewModelFactory(repository)

        var listener: RvNavItemClickListener = this
        binding.navLayout.llRoot.hide()
        var isNet: Boolean = activity?.isInternetAvailable()!!
        checkInternetAndAction(isNet)
        if (isNet) {
            model = ViewModelProvider(this, factory).get(NewsHomeViewModel::class.java)
            model.listener = this
            model.categories.observe(viewLifecycleOwner, Observer {
                binding.navLayout.sp.rlSpl.hide()
                binding.navLayout.llRoot.show()
                binding.navLayout.list.adapter = CategoryAdapter(it, listener)
                binding.navLayout.list.layoutManager = GridLayoutManager(requireContext(), 3)
            })
        }
        binding.lifecycleOwner = this

        binding.apply {
            navLayout.closeBtn.setOnClickListener {
                activity?.onBackPressed()
            }
        }

        return binding.root
    }

    override fun navItemClick(categories: Categories) {

        findNavController().navigate(
            NavDrawerFragmentDirections.actionNavDrawerFragmentToCategoryNewsFragment(
                categories
            )
        )

    }
    private fun checkInternetAndAction(available: Boolean) {
        if(!available){
            binding.apply {
                navLayout.llRoot.hide()
                navLayout.sp.rlSpl.hide()
                internet.noInternetConnection.show()
                internet.tryAgain.setOnClickListener {
                    val navController = findNavController()
                    navController.run {
                        popBackStack()
                        navigate(R.id.navDrawerFragment)
                    }
                }
            }
        }
    }

    override fun onError(s: String) {
        binding.rlRoot.toastSnack(s)
    }


}