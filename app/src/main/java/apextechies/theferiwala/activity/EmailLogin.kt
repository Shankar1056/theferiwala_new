package apextechies.theferiwala.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apextechies.theferiwala.R
import kotlinx.android.synthetic.main.activity_login_email.*

class EmailLogin:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        submit.setOnClickListener {
            if (input_email.text.toString().trim().equals(""))
            {
                Toast.makeText(this, "ENter your email id", Toast.LENGTH_SHORT).show()
            }else if (input_password.text.toString().trim().equals(""))
            {
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
            }
            else {
                startActivity(Intent(this@EmailLogin, MainActivity::class.java))
                finish()
            }
        }

        skip.setOnClickListener {
            startActivity(Intent(this@EmailLogin, MainActivity::class.java))
            finish()
        }
    }
}