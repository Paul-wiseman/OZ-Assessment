package com.example.ozeassessment.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ozeassessment.databinding.FragmentProfileBinding
import com.example.ozeassessment.ui.profile.adapter.ProfileAdapter
import com.example.ozeassessment.util.showSnackBar
import com.example.ozeassessment.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var mAdapter: ProfileAdapter
    private val mDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = ProfileAdapter()

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = mAdapter

        mAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.source.refresh
            binding.recyclerview.isVisible = refreshState is LoadState.NotLoading
            binding.progressBar.isVisible = refreshState is LoadState.Loading
            binding.buttonRetry.isVisible = refreshState is LoadState.Error
            handleError(loadState)
        }
        binding.buttonRetry.setOnClickListener {
            mAdapter.retry()
        }


        mDisposable.add(
            viewModel.users.subscribe {
                mAdapter.submitData(lifecycle, it)
            }

        )

    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
        errorState?.let {
            binding.root.showSnackBar("${it.error}")
        }
    }


    override fun onDestroy() {
        mDisposable.clear()
        mDisposable.dispose()
        super.onDestroy()
        _binding = null
    }
}