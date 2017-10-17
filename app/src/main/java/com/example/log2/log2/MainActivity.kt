package com.example.log2.log2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.util.Log
import android.os.Handler
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException


class MainActivity : AppCompatActivity() {

    // Create the Handler object (on the main thread by default)
    var handler = Handler()
    var x:Int =0
    var fText:String = ""

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myTextView.setText("Hello World")


        gen_button.setOnClickListener {

            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            handler.post(runnableCode);
            gen_button.setEnabled(false);

        }

        stop_button.setOnClickListener{

            handler.removeCallbacks(runnableCode);  // removes all the messages in the queue
            x =0
            gen_button.setEnabled(true);
        }


    }

    private val runnableCode = object : Runnable {
        override fun run() {
            // Do something here on the main thread
            Log.d("Handlers", "Called on main thread")
            myTextView.setText("Hello World " + x)
            fText = fText + "Testing123," + x + "," + (x*2) + "," + (x*x) + "\n"
            write("CSV_Text_Test", fText)

            x++

            // Repeat this the same runnable code block again another 2 seconds
            // 'this' is referencing the Runnable object
            handler.postDelayed(this, 1000)   // recursively runs a new thread after 2 seconds
        }
    }

    fun write(fname: String, fcontent: String): Boolean? {
        try {

            val fpath = "/storage/emulated/0/DriveSyncFiles/$fname.csv"
            val file = File(fpath)

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile()
            }
            Log.d("GO", "Sucess")

            val fw = FileWriter(file.getAbsoluteFile())
            val bw = BufferedWriter(fw)
            bw.write(fcontent)
            bw.close()

            return true
        }
        catch (e: IOException) {
            Log.d("NO GO", "Error Testing123")
            Log.d("NO GO" , e.message)
            e.printStackTrace()
            return false
        }

    }



}
