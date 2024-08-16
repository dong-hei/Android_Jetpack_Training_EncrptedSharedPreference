
package com.dk.encrptedsharedpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * 중요한 값의 노출을 막기 위함
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //암호화된 키값 생성
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sharedPreferences = EncryptedSharedPreferences.create(
            "secret_pref_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val sv1 = findViewById<Button>(R.id.sv1)
        sv1.setOnClickListener {
            val editTxt = findViewById<EditText>(R.id.editTxt1)
            with(sharedPreferences.edit()) {
                putString("valueTest", editTxt.text.toString())
                apply()
            }
        }

        val read1 = findViewById<Button>(R.id.read1)
        read1.setOnClickListener {
            val sharedVal = sharedPreferences.getString("valueTest", "defaultVal")

            val readTxt1 = findViewById<TextView>(R.id.readTxt1)
            readTxt1.text = sharedVal
        }
    }
}