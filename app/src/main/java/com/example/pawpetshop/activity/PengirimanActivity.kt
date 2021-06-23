package com.example.pawpetshop.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pawpetshop.R
import com.example.pawpetshop.helper.Helper
import kotlinx.android.synthetic.main.activity_pengiriman.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class PengirimanActivity: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_pengiriman)

            mainButton()
        }

    private fun mainButton(){
        btn_tambahAlamat.setOnClickListener{
            startActivity(Intent(this, ListAlamatActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //fungsi tombol kembali kehome saat di detail produk
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}