package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {
    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        benderObj.tryCnt = savedInstanceState?.getInt("benderObj.tryCnt") ?: benderObj.tryCnt

        applyColorFilter()

        Log.d("qxov_MainActivity $status / $question", "onCreate")

        textTxt.text = benderObj.askQuestion()
        messageEt.addTextChangedListener(this)
        messageEt.setOnEditorActionListener(this)
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
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
    }

    fun sendAnswer() {

        val phrase = benderObj.listenAnswer(messageEt.text.toString()).first
        messageEt.setText("")
        textTxt.text = phrase
        applyColorFilter()
        hideKeyboard()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            sendAnswer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("benderObj.status.name", benderObj.status.name)
        outState.putString("benderObj.question.name", benderObj.question.name)
        outState.putInt("benderObj.tryCnt", benderObj.tryCnt)
        Log.d("qxov_MainActivity", "onSaveInstanceState ${benderObj.status.name} / ${benderObj.question.name}")
    }


    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        val (isValid, message) = benderObj.question.answerTip(messageEt.text.toString())

        if (isValid) {
            messageEt.error = null
        } else {
            messageEt.error = message
        }

    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (v?.id != R.id.et_message)
            return false

        if (actionId == EditorInfo.IME_ACTION_DONE) {
            sendAnswer()
            return true
        }

        if(actionId == EditorInfo.IME_ACTION_UNSPECIFIED && event!=null) {
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                return true
            }

            if (event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                sendAnswer()
                return true
            }

            return false
        }

        return false

    }

}

