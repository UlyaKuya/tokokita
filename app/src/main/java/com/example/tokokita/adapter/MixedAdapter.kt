package com.example.tokokita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tokokita.databinding.ItemBannerBinding
import com.example.tokokita.databinding.ItemHeaderBinding
import com.example.tokokita.databinding.ItemProdukBinding
import com.example.tokokita.model.ProdukListItem

class MixedAdapter(
    private val onProdukClick: (ProdukListItem.Item) -> Unit,
    private val onFavoritClick: (Int) -> Unit
) : ListAdapter<ProdukListItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {

        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_BANNER = 2

        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ProdukListItem>() {

                override fun areItemsTheSame(
                    oldItem: ProdukListItem,
                    newItem: ProdukListItem
                ): Boolean {

                    return when {
                        oldItem is ProdukListItem.Header &&
                                newItem is ProdukListItem.Header ->
                            oldItem.kategori == newItem.kategori

                        oldItem is ProdukListItem.Item &&
                                newItem is ProdukListItem.Item ->
                            oldItem.produk.id == newItem.produk.id

                        oldItem is ProdukListItem.Banner &&
                                newItem is ProdukListItem.Banner ->
                            true

                        else -> false
                    }
                }

                override fun areContentsTheSame(
                    oldItem: ProdukListItem,
                    newItem: ProdukListItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProdukListItem.Header -> TYPE_HEADER
            is ProdukListItem.Item -> TYPE_ITEM
            is ProdukListItem.Banner -> TYPE_BANNER
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {

            TYPE_HEADER -> {
                val binding = ItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }

            TYPE_ITEM -> {
                val binding = ItemProdukBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProdukViewHolder(
                    binding,
                    onFavoritClick
                )
            }

            else -> {
                val binding = ItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BannerViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        when (val item = getItem(position)) {

            is ProdukListItem.Header -> {
                (holder as HeaderViewHolder).bind(item)
            }

            is ProdukListItem.Item -> {
                (holder as ProdukViewHolder).bind(item)

                holder.itemView.setOnClickListener {
                    onProdukClick(item)
                }
            }

            is ProdukListItem.Banner -> {
                (holder as BannerViewHolder).bind()
            }
        }
    }

    class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProdukListItem.Header) {
            binding.tvHeader.text = item.kategori
        }
    }

    class ProdukViewHolder(
        private val binding: ItemProdukBinding,
        private val onFavoritClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProdukListItem.Item) {

            val produk = item.produk

            binding.tvNama.text = produk.nama
            binding.tvHarga.text = "Rp ${produk.harga}"
            binding.tvStok.text = "Stok: ${produk.stok}"
            binding.tvKategori.text = "Kategori: ${produk.kategori}"

            binding.tvFavorit.text =
                if (produk.isFavorit) "⭐" else "☆"

            binding.tvFavorit.setOnClickListener {
                onFavoritClick(produk.id)
            }
        }
    }

    class BannerViewHolder(
        private val binding: ItemBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.tvBanner.text = "🎉 Promo TokoKita"
        }
    }
}