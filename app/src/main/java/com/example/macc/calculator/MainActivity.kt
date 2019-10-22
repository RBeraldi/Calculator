package com.example.macc.calculator

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var output: TextView
    private var op = 0
    private var operator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id._0).setOnClickListener(this)
        findViewById<Button>(R.id._1).setOnClickListener(this)
        findViewById<Button>(R.id._2).setOnClickListener(this)
        findViewById<Button>(R.id._3).setOnClickListener(this)
        findViewById<Button>(R.id._4).setOnClickListener(this)
        findViewById<Button>(R.id._5).setOnClickListener(this)
        findViewById<Button>(R.id._6).setOnClickListener(this)
        findViewById<Button>(R.id._7).setOnClickListener(this)
        findViewById<Button>(R.id._8).setOnClickListener(this)
        findViewById<Button>(R.id._9).setOnClickListener(this)
        findViewById<Button>(R.id._C).setOnClickListener(this)
        findViewById<Button>(R.id._AC).setOnClickListener(this)
        findViewById<Button>(R.id._F).setOnClickListener(this)
        findViewById<Button>(R.id._plus).setOnClickListener(this)
        findViewById<Button>(R.id._minus).setOnClickListener(this)
        findViewById<Button>(R.id._times).setOnClickListener(this)
        findViewById<Button>(R.id._div).setOnClickListener(this)
        findViewById<Button>(R.id._equal).setOnClickListener(this)
        output=findViewById(R.id.output)

    }

    override fun onClick(v: View?) {

        if (!output.text.isDigitsOnly()) output.text=""
        if (output.text.isEmpty()) output.text=""
        if (!output.text.isEmpty() && (output.text.toString().toDouble()==0.0)) output.text=""

        //if ((output.length()==0) or (output.text.toString().toDouble()==0.0)) output.text=""
        when(v?.id){
            R.id._0 -> output.append("0")
            R.id._1 -> output.append("1")
            R.id._2 -> output.append("2")
            R.id._3 -> output.append("3")
            R.id._4 -> output.append("4")
            R.id._5 -> output.append("5")
            R.id._6 -> output.append("6")
            R.id._7 -> output.append("7")
            R.id._8 -> output.append("8")
            R.id._9 -> output.append("9")
            R.id._C -> output.text="0"

            R.id._AC -> {if (output.text.isEmpty()) return
                output.text=output.text.subSequence(0,output.length()-1)}

            R.id._plus -> {if (output.text.isEmpty()) return
                             op=output.text.toString().toInt()
                             operator="+"
                             output.text=""}

            R.id._minus -> {if (output.text.isEmpty()) return
                op=output.text.toString().toInt()
                operator="-"
                output.text=""}

            R.id._div -> {if (output.text.isEmpty()) return
                op=output.text.toString().toInt()
                operator="/"
                output.text=""}

            R.id._times -> {if (output.text.isEmpty()) return
                op=output.text.toString().toInt()
                operator="*"
                output.text=""}

            R.id._equal -> {
                if (output.text.isEmpty()) return
                when (operator) {
                    "+" -> output.text = (op+output.text.toString().toInt() ).toString()
                    "-" -> output.text = (op-output.text.toString().toInt()).toString()
                    "/" -> output.text = (op/output.text.toString().toInt()).toString()
                    "*" -> output.text = (output.text.toString().toInt() * op).toString()

                }
            }
            R.id._F -> factors(output.text.toString())
        }
    }


    fun factors (x: String){

        val queue = Volley.newRequestQueue(this)
        val url = "https://helloacm.com/api/factor/?cached&n="+x

        var reply : String = ""

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                reply = JSONObject(response.toString())["result"].toString()
                output.text=reply
            },
            Response.ErrorListener { error: VolleyError? ->  output.text = error.toString() })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

}
