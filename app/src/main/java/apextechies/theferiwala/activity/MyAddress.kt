package apextechies.theferiwala.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import apextechies.theferiwala.R
import apextechies.theferiwala.payment.PaymentActivity
import kotlinx.android.synthetic.main.address_item.*

class MyAddress: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                submitButton.text = "Poceed To Pay"
            }
            else{
                submitButton.text = "Add New Address"
            }
        }

        submitButton.setOnClickListener {
            if (submitButton.text.toString().equals("Add New Address")){
                startActivityForResult(Intent(this@MyAddress, MapActivity::class.java),2)
            }else if(submitButton.text.toString().equals("Poceed To Pay")){

                startActivity(Intent(this@MyAddress, PaymentActivity::class.java))
            }
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            val address = data!!.getStringExtra("MESSAGE")
            val postal = data!!.getStringExtra("postal")
            val city = data!!.getStringExtra("city")
            val lat = data!!.getStringExtra("lat")
            val lon = data!!.getStringExtra("lon")
            addressTV.text  = address+","+city+","+postal
        }
    }


}