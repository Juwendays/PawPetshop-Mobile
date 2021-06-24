package com.example.pawpetshop.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pawpetshop.R
import com.example.pawpetshop.helper.Helper
import com.example.pawpetshop.model.Produk
import com.example.pawpetshop.room.MyDatabase
import com.example.pawpetshop.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailProdukActivity: AppCompatActivity() {

    lateinit var myDb: MyDatabase
    lateinit var produk : Produk

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail_produk)

            myDb = MyDatabase.getInstance(this)!! // call database
            getInfo()
            mainButton()
            checkkeranjang()
        }

    // tombol keranjang
    fun mainButton(){
        btn_keranjang.setOnClickListener {
            // Manipulasi agar barang di keranjang tetap satu hanya bertambah jumlahnya jika beli lebih dari 2
            val data = myDb.daoKeranjang().getProduk(produk.id)
            if (data == null){
                insert()
            }else{
                data.jumlah += 1
                update(data)
            }
        }

        //CEK
        btn_favorit.setOnClickListener {
            val listData = myDb.daoKeranjang().getAll() // get All data atau memanggil querynya
            for(note :Produk in listData){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }

        btn_toKeranjang.setOnClickListener{
            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }

    // Fungsi Menambah ke keranjang dari detail produk
    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkkeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil ditambah ke Keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data:Produk){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkkeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil ditambah ke Keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkkeranjang(){
        var datakeranjang = myDb.daoKeranjang().getAll()
        if(datakeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = datakeranjang.size.toString()
        }
        else{
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo(){
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

//        val nama = intent.getStringExtra("extra")
//        val harga = intent.getStringExtra("harga")

        // set Value
        tv_nama.text = produk.name
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        val img = Config.productUrl + produk.image
        //libary picasso
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400,400)
            .into(image)

        // Set toolbar_nocustom detail produk
        Helper().setToolbar(this, toolbar, produk.name)
    }

    override fun onSupportNavigateUp(): Boolean {
        //fungsi tombol kembali kehome saat di detail produk
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}