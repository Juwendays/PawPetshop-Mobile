package com.example.pawpetshop.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pawpetshop.R
import com.example.pawpetshop.helper.Helper
import kotlinx.android.synthetic.main.activity_list_alamat.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class ListAlamatActivity: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_list_alamat)

            mainButton()
        }

    private fun mainButton(){
        btn_tambahAlamat.setOnClickListener{
            startActivity(Intent(this, TambahAlamatActivity::class.java))
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        //fungsi tombol kembali kehome saat di detail produk
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}