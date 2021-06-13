package com.example.paw_petshop.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pawpetshop.MainActivity
import com.example.pawpetshop.R
import com.example.pawpetshop.activity.LoginActivity
import com.example.pawpetshop.helper.SharedPref

class AkunFragment : Fragment(){

    lateinit var s:SharedPref
    lateinit var btnLogout: TextView
    lateinit var tvNama: TextView
    lateinit var tvEmail: TextView
    lateinit var tvPhone: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

//        Inflater the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
//        btnLogout = view.findViewById(R.id.btn_logout)
        init(view)

        s = SharedPref(activity!!)

        // Perintah tomblo logout
        btnLogout.setOnClickListener {
            s.setStatusLogin(false)
        }

        setData()
        return view
    }

    // Mengambil data untuk dimunculkan akun
    fun setData() {

        if (s.getUser() == null){
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }

        val user = s.getUser()!!

        tvNama.text = user.name
        tvEmail.text = user.email
        tvPhone.text = user.phone
    }

    // Menampilkan data user di akun
    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_nama)
        tvEmail = view.findViewById(R.id.tv_email)
        tvPhone = view.findViewById(R.id.tv_phone)
    }
}