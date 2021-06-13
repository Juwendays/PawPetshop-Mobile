package com.example.paw_petshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pawpetshop.R
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
        tvNama.text = s.getString(s.nama)
        tvEmail.text = s.getString(s.email)
        tvPhone.text = s.getString(s.phone)
    }

    // Menampilkan data user di akun
    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_nama)
        tvEmail = view.findViewById(R.id.tv_email)
        tvPhone = view.findViewById(R.id.tv_phone)
    }
}