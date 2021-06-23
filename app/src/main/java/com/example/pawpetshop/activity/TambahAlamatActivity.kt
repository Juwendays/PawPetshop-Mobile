package com.example.pawpetshop.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pawpetshop.R
import com.example.pawpetshop.helper.Helper
import kotlinx.android.synthetic.main.toolbar_custom.*

class TambahAlamatActivity: AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_tambah_alamat)

        }

    override fun onSupportNavigateUp(): Boolean {
        //fungsi tombol kembali kehome saat di detail produk
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}