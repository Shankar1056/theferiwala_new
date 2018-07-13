package apextechies.theferiwala.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import apextechies.theferiwala.R
import kotlinx.android.synthetic.main.actvity_details.*

class ProductDetails: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        submit.setOnClickListener {
            startActivity(Intent(this@ProductDetails, MyCartActivity::class.java))
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}