package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //benderImage = findViewById(R.id.iv_bender)
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message

        sendBtn = iv_send
        sendBtn.setOnClickListener(this)

        benderObj = Bender()

        val status = savedInstanceState?.getString("benderObj.status.name") ?: benderObj.status.name
        val question = savedInstanceState?.getString("benderObj.question.name") ?: benderObj.question.name
        benderObj.status = Bender.Status.valueOf(status)
        benderObj.question = Bender.Question.valueOf(question)

        applyColorFilter()

        Log.d("qxov_MainActivity $status / $question", "onCreate")

        textTxt.text = benderObj.askQuestion()
    }

    override fun onPause() {
        super.onPause()
        Log.d("qxov_MainActivity", "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("qxov_MainActivity", "onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d("qxov_MainActivity", "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.d("qxov_MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("qxov_MainActivity", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("qxov_MainActivity", "onRestart")
    }

    fun applyColorFilter() {
        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY);
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            val phrase = benderObj.listenAnswer(messageEt.text.toString().toLowerCase()).first
            messageEt.setText("")
            textTxt.text = phrase
            applyColorFilter()

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("benderObj.status.name", benderObj.status.name)
        outState.putString("benderObj.question.name", benderObj.question.name)
        Log.d("qxov_MainActivity", "onSaveInstanceState ${benderObj.status.name} / ${benderObj.question.name}")
    }
}