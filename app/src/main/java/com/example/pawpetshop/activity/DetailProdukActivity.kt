package com.example.pawpetshop.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pawpetshop.R
import com.example.pawpetshop.helper.Helper
import com.example.pawpetshop.model.Produk
import com.example.pawpetshop.room.MyDatabase
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailProdukActivity: AppCompatActivity() {

    lateinit var produk : Produk


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail_produk)

            getInfo()
            mainButton()
        }

    // tombol keranjang
    fun mainButton(){
        btn_keranjang.setOnClickListener {
            insert()
        }

        //CEK
        btn_favorit.setOnClickListener {
            val myDb: MyDatabase = MyDatabase.getInstance(this)!! // call database
            val listNote = myDb.daoKeranjang().getAll() // get All data atau memanggil querynya
            for(note :Produk in listNote){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }
    }

    fun insert(){
        val myDb: MyDatabase = MyDatabase.getInstance(this)!! // call database
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("respons", "data inserted")
            })
    }

    fun getInfo(){
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

//        val nama = intent.getStringExtra("extra")
//        val harga = intent.getStringExtra("harga")

        // set Value
        tv_nama.text = produk.name
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        val img = "http://192.168.0.104/PawPetshop/public/storage/produk/" + produk.image
        //libary picasso
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400,400)
            .into(image)

        // Set toolbar detail produk
        setSupportActionBar(toolbar)
        supportActionBar!!.title = produk.name
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        //kembali kehome saat di detail produk
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}