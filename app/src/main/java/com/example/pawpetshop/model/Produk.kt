package com.example.pawpetshop.model

import java.io.Serializable

//Menampung Data Produk
class Produk : Serializable {
    var id:Int = 0
    lateinit var name:String
    lateinit var harga:String
    lateinit var deskripsi:String
    var category_id:Int  = 0
    lateinit var image:String
    lateinit var created_at:String
    lateinit var update_at:String
}