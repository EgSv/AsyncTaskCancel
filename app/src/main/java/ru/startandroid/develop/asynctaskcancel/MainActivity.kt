package ru.startandroid.develop.asynctaskcancel

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

const val LOG_TAG = "myLogs"

class MainActivity : AppCompatActivity() {

    private var mt: MyTask? = null
    var tvInfo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInfo = findViewById<View>(R.id.tvInfo) as TextView
    }
    fun onClick(v: View) {
        when(v.id) {
            R.id.btnStart -> {
                mt = MyTask()
                mt!!.execute()
            }
            R.id.btnCancel -> cancelTask()
            else -> {}
        }
    }

    private fun cancelTask() {
        if (mt == null) return
        Log.d(LOG_TAG, "cancel result: ${mt!!.cancel(true)}")
    }

    internal inner class MyTask :
        AsyncTask<Unit, Unit, Unit>() {
        override fun onPreExecute() {
            super.onPreExecute()
            tvInfo!!.text = "Begin"
            Log.d(LOG_TAG, "Begin")
        }

        override fun doInBackground(vararg params: Unit): Unit? {
            try {
                for (i in 1..4) {
                    Thread.sleep(1000)
                    if (isCancelled) return null
                    Log.d(LOG_TAG, "isCancelled: $isCancelled")
                }
            } catch (e:InterruptedException) {
                Log.d(LOG_TAG, "Interrupted")
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            tvInfo!!.text = "End"
            Log.d(LOG_TAG, "End")
        }

        override fun onCancelled() {
            super.onCancelled()
            tvInfo!!.text = "Cancel"
            Log.d(LOG_TAG, "Cancel")
        }
    }
}