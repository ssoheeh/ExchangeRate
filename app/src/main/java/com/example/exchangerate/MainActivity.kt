package com.example.exchangerate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.exchangerate.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutionException

class MainActivity : AppCompatActivity() {
    private val currencyList = arrayOf("KRW", "USD", "EUR", "CAD")
    private lateinit var et_from: TextView
    private lateinit var tv_to: TextView
    private lateinit var btn_exchange: Button
    private val fromto = arrayOfNulls<String>(2)
    private lateinit var tv_test: TextView
    private var currencyRate = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)

        et_from = findViewById(R.id.et_from)
        tv_to = findViewById(R.id.tv_to)

        btn_exchange = findViewById(R.id.btn_exchange)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
                fromto[0] = currencyList[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        spinner2.adapter = adapter
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
                fromto[1] = currencyList[i]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        btn_exchange.setOnClickListener {
            GlobalScope.launch {
                try {
                    val task = Task()
                    currencyRate = task.executeAsync(*fromto)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                }

                val input = et_from.text.toString().toDouble()
                val result = Math.round(input * currencyRate * 100.0) / 100.0

                tv_to.text = result.toString()
            }

        }
    }



}