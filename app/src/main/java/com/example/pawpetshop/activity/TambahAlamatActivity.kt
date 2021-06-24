package com.example.pawpetshop.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.pawpetshop.R
import com.example.pawpetshop.app.ApiConfig
import com.example.pawpetshop.app.ApiConfigAlamat
import com.example.pawpetshop.helper.Helper
import com.example.pawpetshop.model.ResponModel
import kotlinx.android.synthetic.main.activity_tambah_alamat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahAlamatActivity: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_tambah_alamat)
            Helper().setToolbar(this, toolbar,"Tambah Alamat")

            getProvinsi()
        }

    private fun getProvinsi(){
        ApiConfigAlamat.instanceRetrofit.getProvinsi().enqueue(object :
            Callback<ResponModel> {

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                if(response.isSuccessful){

                    pb.visibility = View.GONE
                    div_provinsi.visibility = View.VISIBLE

                    val res = response.body()!!
                    val arryString = ArrayList<String>()
                    for (prov in res.provinsi){
                        arryString.add(prov.nama)
                    }

                    val adapter = ArrayAdapter<Any>(this@TambahAlamatActivity, R.layout.item_spinner, arryString.toTypedArray())
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spn_provinsi.adapter = adapter
                }else{
                    Log.d("Error","Gagal Memuat Data:"+response.message())
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        //fungsi tombol kembali kehome saat di detail produk
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}