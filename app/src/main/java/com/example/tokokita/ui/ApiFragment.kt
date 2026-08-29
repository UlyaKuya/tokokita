package com.example.tokokita.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokokita.adapter.PostAdapter
import com.example.tokokita.databinding.FragmentApiBinding
import com.example.tokokita.network.RetrofitClient
import kotlinx.coroutines.launch

class ApiFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentApiBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.btnRetry.setOnClickListener {
            loadPosts()
        }

        loadPosts()
    }

    private fun setupRecyclerView() {

        adapter = PostAdapter()

        binding.recyclerViewApi.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerViewApi.adapter = adapter
    }

    private fun loadPosts() {

        showLoading()

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val posts =
                    RetrofitClient.apiService.getPosts()

                adapter.submitList(posts)

                showContent()

            } catch (e: Exception) {

                showError(
                    e.message ?: "Terjadi kesalahan saat mengambil data"
                )
            }
        }
    }

    private fun showLoading() {

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewApi.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showContent() {

        binding.progressBar.visibility = View.GONE
        binding.recyclerViewApi.visibility = View.VISIBLE
        binding.layoutError.visibility = View.GONE
    }

    private fun showError(message: String) {

        binding.progressBar.visibility = View.GONE
        binding.recyclerViewApi.visibility = View.GONE
        binding.layoutError.visibility = View.VISIBLE

        binding.tvError.text =
            "Gagal mengambil data\n\n$message"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}