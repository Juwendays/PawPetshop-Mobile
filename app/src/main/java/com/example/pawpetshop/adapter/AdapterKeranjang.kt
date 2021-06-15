package com.example.pawpetshop.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpetshop.R
import com.example.pawpetshop.activity.DetailProdukActivity
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
import kotlin.collections.ArrayList

class AdapterKeranjang(var activity: Context, var data: ArrayList<Produk>,var listener : Listeners) : RecyclerView.Adapter<AdapterKeranjang.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)

        val btnTambah = view.findViewById<ImageView>(R.id.btn_tambah)
        val btnKurang = view.findViewById<ImageView>(R.id.btn_kurang)
        val btnDelete = view.findViewById<ImageView>(R.id.btn_delete)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_keranjang, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val produk = data[position]


        holder.tvNama.text = data[position].name

        //Membuat harga rupiah dan dengan format titik yang benar
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)

        //Membuat Penambahan kuantity barang di keranjang
        var jumlah = data[position].jumlah
        holder.tvJumlah.text = jumlah.toString()

//      holder.imgProduk.setImageResource(data[position].gambar)
        val image = Config.productUrl + data[position].image
        //libary picasso
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .into(holder.imgProduk)

        // Holder di Nonaktifkan karena tambah diarahkan ke detail produk
//        holder.layout.setOnClickListener {
//            val activiti = Intent(activity, DetailProdukActivity::class.java)
//
//            //membawa informasi dari satu activity ke acticity lain dengan lebih efektif
//            val str = Gson().toJson(data[position], Produk::class.java)
//            activiti.putExtra("extra", str )
//
//            //membawa informasi dari satu activity ke acticity lain
////            activiti.putExtra("nama", data[position].name )
////            activiti.putExtra("harga", data[position].harga )
//
//            activity.startActivity(activiti)
//        }

        // Tampilan penmabhan pada keranjang
        holder.btnTambah.setOnClickListener {
            //Pencegahan agar penambahan tidak lebih dari jumlah stok misal stok = 10
            //if (jumlah >= 10) return@setOnClickListener
            jumlah++
            //pemanggilan fungsi update
            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()
        }

        // Tampilan pengurangan pada keranjang
        holder.btnKurang.setOnClickListener {
            //Pencegahan agar pengurangan tidak kurang dari 1
            if (jumlah <= 1) return@setOnClickListener
            jumlah--
            //pemanggilan fungsi update
            produk.jumlah = jumlah
            update(produk)
            holder.tvJumlah.text = jumlah.toString()

        }

        // Tampilan penghapusan pada keranjang
        holder.btnDelete.setOnClickListener {
            delete(produk)
        }
    }

        interface Listeners{
            fun onUpdate()
            fun onDelete()
        }

        // Fungsi agar penambah dan pengurangan terupadate atau tersimpan
        private fun update(data: Produk) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
        }

    // Fungsi agar penambah dan pengurangan terupadate atau tersimpan
        private fun delete(data: Produk) {
        val myDb = MyDatabase.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onDelete()
            })
    }

    }
