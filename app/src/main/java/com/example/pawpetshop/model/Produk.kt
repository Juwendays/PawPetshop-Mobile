package com.example.pawpetshop.model

import java.io.Serializable

//Menampung Data Produk
class Produk : Serializable {
    lateinit var nama:String
    lateinit var harga:String
    var gambar:Int = 0
}