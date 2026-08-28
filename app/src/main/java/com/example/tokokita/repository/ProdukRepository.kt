package com.example.tokokita.repository

import com.example.tokokita.model.Produk

class ProdukRepository {

    fun getProduk(): List<Produk> {
        return listOf(
            Produk(
                id = 1,
                nama = "Smartphone Android",
                harga = 2500000.0,
                kategori = "Elektronik",
                stok = 10
            ),
            Produk(
                id = 2,
                nama = "Laptop",
                harga = 7500000.0,
                kategori = "Elektronik",
                stok = 5
            ),
            Produk(
                id = 3,
                nama = "Headset Bluetooth",
                harga = 350000.0,
                kategori = "Elektronik",
                stok = 15
            ),
            Produk(
                id = 4,
                nama = "Kaos Casual",
                harga = 120000.0,
                kategori = "Fashion",
                stok = 20
            ),
            Produk(
                id = 5,
                nama = "Sepatu Sneakers",
                harga = 450000.0,
                kategori = "Fashion",
                stok = 8
            ),
            Produk(
                id = 6,
                nama = "Tas Ransel",
                harga = 275000.0,
                kategori = "Fashion",
                stok = 12
            )
        )
    }
}