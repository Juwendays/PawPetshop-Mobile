package com.example.pawpetshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpetshop.R
import com.example.pawpetshop.activity.DetailProdukActivity
import com.example.pawpetshop.helper.Helper
import com.example.pawpetshop.model.Produk
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterKeranjang(var activity: Context, var data: ArrayList<Produk>) : RecyclerView.Adapter<AdapterKeranjang.Holder>() {

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
        holder.tvNama.text = data[position].name

        //Membuat harga rupiah dan dengan format titik yang benar
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)

        //Membuat Penambahan kuantity barang di keranjang
        var jumlah = data[position].jumlah
        holder.tvJumlah.text = jumlah.toString()

//      holder.imgProduk.setImageResource(data[position].gambar)
        val image = "http://192.168.0.104/PawPetshop/public/storage/produk/" + data[position].image
        //libary picasso
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .into(holder.imgProduk)

        holder.layout.setOnClickListener {
            val activiti = Intent(activity, DetailProdukActivity::class.java)

            //membawa informasi dari satu activity ke acticity lain dengan lebih efektif
            val str = Gson().toJson(data[position], Produk::class.java)
            activiti.putExtra("extra", str )

            //membawa informasi dari satu activity ke acticity lain
//            activiti.putExtra("nama", data[position].name )
//            activiti.putExtra("harga", data[position].harga )

            activity.startActivity(activiti)
        }
    }
}