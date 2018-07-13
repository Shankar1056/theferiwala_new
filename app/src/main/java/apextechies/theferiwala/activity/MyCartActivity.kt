package apextechies.theferiwala.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import apextechies.theferiwala.R
import kotlinx.android.synthetic.main.activity_mycart.*

class MyCartActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycart)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        proceedtopay.setOnClickListener {
            startActivity(Intent(this@MyCartActivity, MyAddress::class.java))
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}