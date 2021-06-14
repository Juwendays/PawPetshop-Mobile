package com.example.paw_petshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpetshop.R
import com.example.pawpetshop.adapter.AdapterKeranjang
import com.example.pawpetshop.adapter.AdapterProduk
import com.example.pawpetshop.room.MyDatabase

class KeranjangFragment : Fragment(){

    // display produk dipanggil sekali ketika activity aktif,karena fragment berada di dalam activity disebut LifeCycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)

        init(view)
        mainButton()
        displayProduk()
        return view
    }

    private fun displayProduk(){
        val myDb = MyDatabase.getInstance(requireActivity())
        val listProduk = myDb!!.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvProduk.adapter = AdapterKeranjang(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManager
    }

    private fun mainButton(){
        btnDelete.setOnClickListener{

        }
        btnBayar.setOnClickListener{

        }
    }
    lateinit var btnDelete: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var btnBayar: TextView

    private fun init(view: View){
        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
    }

    override fun onResume() {
        displayProduk()
        super.onResume()
    }
}