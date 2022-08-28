package com.example.storeapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storeapplication.R
import com.example.storeapplication.entities.MyCart
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_order_successfull.*
import kotlinx.android.synthetic.main.toolbar.*

class OrderSuccessfullActivity : AppCompatActivity() {

    val activity=this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_successfull)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        titleText.text = resources.getString(R.string.order_successful)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        SugarRecord.deleteAll(MyCart::class.java)

        gotoHomeBtn.setOnClickListener {
            onBackPressed()
        }

    }


    override fun onBackPressed() {
//        super.onBackPressed()
        finishAffinity()
        startActivity(Intent(activity,MainActivity::class.java))
    }
}