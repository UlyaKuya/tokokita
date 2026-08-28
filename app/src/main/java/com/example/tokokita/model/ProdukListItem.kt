package com.example.tokokita.model

sealed class ProdukListItem {

    data class Header(
        val kategori: String
    ) : ProdukListItem()

    data class Item(
        val produk: Produk
    ) : ProdukListItem()

    object Banner : ProdukListItem()
}