package com.example.tokokita.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokokita.model.ProdukListItem
import com.example.tokokita.repository.ProdukRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProdukViewModel : ViewModel() {

    private val repository = ProdukRepository()

    // Data utama RecyclerView
    private val _uiState =
        MutableStateFlow<List<ProdukListItem>>(emptyList())

    val uiState: StateFlow<List<ProdukListItem>> =
        _uiState.asStateFlow()

    // Status Pull-to-Refresh
    private val _isRefreshing =
        MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> =
        _isRefreshing.asStateFlow()

    // Data item yang terakhir dihapus untuk Undo
    private var deletedItem: Pair<Int, ProdukListItem.Item>? = null

    init {
        loadProduk()
    }

    fun deleteAllProduk() {
        _uiState.value = emptyList()
    }

    private fun loadProduk() {

        val produk = repository.getProduk()

        val list = mutableListOf<ProdukListItem>()

        var kategoriSekarang = ""

        produk.forEach { item ->

            if (item.kategori != kategoriSekarang) {

                kategoriSekarang = item.kategori

                list.add(
                    ProdukListItem.Header(
                        kategori = kategoriSekarang
                    )
                )
            }

            list.add(
                ProdukListItem.Item(item)
            )
        }

        // Banner promo
        list.add(
            ProdukListItem.Banner
        )

        _uiState.value = list
    }

    // =========================
    // FAVORIT
    // =========================

    fun toggleFavorit(id: Int) {

        val currentList =
            _uiState.value.toMutableList()

        val index =
            currentList.indexOfFirst {
                it is ProdukListItem.Item &&
                        it.produk.id == id
            }

        if (index != -1) {

            val item =
                currentList[index] as ProdukListItem.Item

            val produkBaru =
                item.produk.copy(
                    isFavorit = !item.produk.isFavorit
                )

            currentList[index] =
                ProdukListItem.Item(produkBaru)

            _uiState.value = currentList
        }
    }

    // =========================
    // DELETE
    // =========================

    fun deleteProduk(id: Int) {

        val currentList =
            _uiState.value.toMutableList()

        val index =
            currentList.indexOfFirst {
                it is ProdukListItem.Item &&
                        it.produk.id == id
            }

        if (index != -1) {

            val item =
                currentList[index] as ProdukListItem.Item

            deletedItem = index to item

            currentList.removeAt(index)

            _uiState.value = currentList
        }
    }

    // =========================
    // UNDO DELETE
    // =========================

    fun undoDelete() {

        val deleted =
            deletedItem ?: return

        val currentList =
            _uiState.value.toMutableList()

        val index =
            deleted.first.coerceAtMost(
                currentList.size
            )

        currentList.add(
            index,
            deleted.second
        )

        _uiState.value = currentList

        deletedItem = null
    }

    // =========================
    // PULL TO REFRESH
    // =========================

    fun refreshData() {

        viewModelScope.launch {

            _isRefreshing.value = true

            delay(1000)

            loadProduk()

            _isRefreshing.value = false
        }
    }
}