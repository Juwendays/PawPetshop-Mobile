package com.example.pawpetshop

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.paw_petshop.fragment.AkunFragment
import com.example.paw_petshop.fragment.HomeFragment
import com.example.paw_petshop.fragment.KeranjangFragment
import com.example.pawpetshop.activity.LoginActivity
import com.example.pawpetshop.activity.MasukActivity
import com.example.pawpetshop.helper.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentKeranjang: Fragment = KeranjangFragment()
    private val fragmentAkun: Fragment = AkunFragment()
    private val fm: FragmentManager = supportFragmentManager

    private var active: Fragment = fragmentHome

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private var statusLogin = false

    private lateinit var s:SharedPref

    private var dariDetail : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = SharedPref(this)

        setUpBottomNav()

        // Penerima dari Detail produk
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessage, IntentFilter("event:keranjang"))

    }

    val mMessage : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            //action kita panggil navigation keranjang
            dariDetail = true
        }
    }

    fun setUpBottomNav() {
        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentKeranjang).hide(fragmentKeranjang).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true


        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            //Memindahkan fragment serta hovernya
            when (item.itemId) {
                R.id.navigation_home -> {
                    callFargment(0, fragmentHome)
                }
                R.id.navigation_keranjang -> {
                    callFargment(1, fragmentKeranjang)
                }
                R.id.navigation_akun -> {
                    if (s.getStatusLogin()){
                        callFargment(2, fragmentAkun)
                    }
                    else{
                        startActivity(Intent(this, MasukActivity::class.java))
                    }
                }

            }

            false

            }
        }
        fun callFargment(int: Int, fragment: Fragment) {
            menuItem = menu.getItem(int)
            menuItem.isChecked = true
            fm.beginTransaction().hide(active).show(fragment).commit()
            active = fragment

    }
    
    override fun onResume(){
        if (dariDetail)
            dariDetail = false
            callFargment( 1, fragmentKeranjang)
        super.onResume()
    }
}

