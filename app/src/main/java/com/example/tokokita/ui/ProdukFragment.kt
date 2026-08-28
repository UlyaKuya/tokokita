package com.example.tokokita.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokokita.adapter.MixedAdapter
import com.example.tokokita.databinding.FragmentProdukBinding
import com.example.tokokita.viewmodel.ProdukViewModel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.example.tokokita.decoration.SpaceItemDecoration



class ProdukFragment : Fragment() {

    private var _binding: FragmentProdukBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProdukViewModel by viewModels()

    private lateinit var adapter: MixedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProdukBinding.inflate(
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
        observeData()
        setupRefresh()
        setupSwipeToDelete()
    }

    private fun setupRecyclerView() {

        adapter = MixedAdapter(
            onProdukClick = { item ->

                val produk = item.produk

                android.widget.Toast.makeText(
                    requireContext(),
                    produk.nama,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            },

            onFavoritClick = { id ->
                viewModel.toggleFavorit(id)
            }
        )

        binding.recyclerViewProduk.layoutManager =
            LinearLayoutManager(requireContext())

        if (binding.recyclerViewProduk.itemDecorationCount == 0) {

            val spacing = (8 * resources.displayMetrics.density).toInt()

            binding.recyclerViewProduk.addItemDecoration(
                SpaceItemDecoration(spacing)
            )
        }

        binding.btnToggleLayout.setOnClickListener {

            val currentLayout =
                binding.recyclerViewProduk.layoutManager

            if (currentLayout is androidx.recyclerview.widget.GridLayoutManager) {

                // Kembali ke List
                binding.recyclerViewProduk.layoutManager =
                    LinearLayoutManager(requireContext())

                binding.btnToggleLayout.text = "Grid"

            } else {

                // Ubah ke Grid
                binding.recyclerViewProduk.layoutManager =
                    androidx.recyclerview.widget.GridLayoutManager(
                        requireContext(),
                        2
                    )

                binding.btnToggleLayout.text = "List"
            }
        }

        binding.recyclerViewProduk.adapter = adapter
    }

    private fun observeData() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.uiState.collect { produkList ->

                    adapter.submitList(produkList)
                }
            }
        }
    }

    private fun setupRefresh() {

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.isRefreshing.collect { refreshing ->

                    binding.swipeRefresh.isRefreshing = refreshing
                }
            }
        }
    }
    private fun setupSwipeToDelete() {

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {

                    val position = viewHolder.bindingAdapterPosition

                    if (position == RecyclerView.NO_POSITION) {
                        return
                    }

                    val item = adapter.currentList.getOrNull(position)

                    if (item !is com.example.tokokita.model.ProdukListItem.Item) {

                        adapter.notifyItemChanged(position)
                        return
                    }

                    val produkId = item.produk.id
                    val namaProduk = item.produk.nama

                    viewModel.deleteProduk(produkId)

                    Snackbar.make(
                        binding.root,
                        "$namaProduk dihapus",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("UNDO") {
                            viewModel.undoDelete()
                        }
                        .show()
                }
            }
        )

        itemTouchHelper.attachToRecyclerView(
            binding.recyclerViewProduk
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}