package com.example.tokokita.model

data class Produk(
    val id: Int,
    val nama: String,
    val harga: Double,
    val kategori: String,
    val stok: Int,
    val isFavorit: Boolean = false,
    val imageUrl: String = ""
)