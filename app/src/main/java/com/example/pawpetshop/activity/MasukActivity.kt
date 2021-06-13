package com.example.pawpetshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pawpetshop.R
import com.example.pawpetshop.helper.SharedPref
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_masuk.*

//import kotlinx.android.synthetic.main.activity_masuk.*
//import kotlinx.android.synthetic.main.activity_masuk.btn_prosesLogin

class MasukActivity : AppCompatActivity() {

    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        s = SharedPref(this)

        mainButton()
    }
    //Intent pada halaman Masuk
        private fun mainButton(){
                btn_prosesLogin.setOnClickListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                }

                btn_register.setOnClickListener {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }

            }


    }
