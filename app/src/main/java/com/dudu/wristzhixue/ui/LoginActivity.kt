package com.dudu.wristzhixue.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.akexorcist.roundcornerprogressbar.indeterminate.IndeterminateCenteredRoundCornerProgressBar
import com.dudu.wristzhixue.R
import com.dudu.wristzhixue.util.NetworkUtil
import com.dudu.wristzhixue.util.Urls
import org.json.JSONObject
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usr:EditText = findViewById(R.id.et_account_number)
        val pwd:EditText = findViewById(R.id.et_pwd)
        val login:Button = findViewById(R.id.bt_login)
        val pg:IndeterminateCenteredRoundCornerProgressBar = findViewById(R.id.pg_login)

        login.setOnClickListener {
            pg.visibility = View.VISIBLE
            login.visibility = View.GONE
            thread {
                val response = NetworkUtil.get("${Urls.student}?usr=${usr.text}&pwd=${pwd.text}").body.string()
                val jsonObject = JSONObject(response)
                val statusJSON = jsonObject.optJSONObject("status")
                val status = statusJSON?.optInt("code")
                if (status == -1) {
                    runOnUiThread {
                        Toast.makeText(this, "登陆失败，请检查账户密码", Toast.LENGTH_SHORT).show()
                        usr.setText("")
                        pwd.setText("")
                        pg.visibility = View.GONE
                        login.visibility = View.VISIBLE
                    }
                }
                if (status == 0){
                    runOnUiThread {
                        Toast.makeText(this, "登陆成功:${jsonObject.optJSONObject("result")?.optString("name")}", Toast.LENGTH_SHORT).show()
                        val SPEditor = getSharedPreferences("login-information", MODE_PRIVATE).edit()
                        SPEditor.putString("usr",usr.text.toString())
                        SPEditor.putString("pwd",pwd.text.toString())
                        SPEditor.apply()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}