package com.example.basickotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.basickotlin.databinding.ActivityMainBinding
import com.example.basickotlin.ui.theme.BasicKotlinTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CODE_SPEECH_INPUT = 100
    private var isFirstField = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addBtn.setOnClickListener {
            if (binding.EditNum1.text.toString() != "" && binding.EditNum2.text.toString() != "") {
             val no1 =    binding.EditNum1.text.toString().toInt()
             val no2 =    binding.EditNum2.text.toString().toInt()
                val sum = no1 + no2

                Toast.makeText(applicationContext,"The Sum is $sum",Toast.LENGTH_SHORT).show()

                binding.txtResult.text = "The Sum is $sum"

            }
        }


        binding.subBtn.setOnClickListener{
            if (binding.EditNum1.text.toString() != "" && binding.EditNum2.text.toString() != ""){
            val no1 =  binding.EditNum1.text.toString().toInt()
            val no2 =  binding.EditNum2.text.toString().toInt()

                val result = no1 - no2
                Toast.makeText(applicationContext,"The result is $result",Toast.LENGTH_SHORT).show()
                binding.txtResult.text = "The Result is $result"
            }
        }

        binding.mulBtn.setOnClickListener{
            if (binding.EditNum1.text.toString() != "" && binding.EditNum2.text.toString() != ""){
                val no1 = binding.EditNum1.text.toString().toInt()
                val no2 = binding.EditNum2.text.toString().toInt()
                    val result = no1 * no2
                    Toast.makeText(applicationContext, "The Result is $result", Toast.LENGTH_SHORT).show()
                    binding.txtResult.text = "The Result is $result"

            }
        }

        binding.divBtn.setOnClickListener {
            if (binding.EditNum1.text.toString() != "" && binding.EditNum2.text.toString() != "") {
                val no1 =    binding.EditNum1.text.toString().toInt()
                val no2 =    binding.EditNum2.text.toString().toInt()
                if (no2 == 0){
                    Toast.makeText(this, "Can't divide by 0",Toast.LENGTH_LONG).show()
                }else {
                    val result = no1 / no2

                    Toast.makeText(applicationContext, "The result is $result", Toast.LENGTH_SHORT)
                        .show()

                    binding.txtResult.text = "The Result is $result"
                }
            }
        }

        binding.acBtn.setOnClickListener{
            binding.EditNum1.text.clear()
            binding.EditNum2.text.clear()
            binding.txtResult.text = ""
        }

        binding.micBtn.setOnClickListener {
            if (binding.EditNum1.text.isEmpty()) {
                isFirstField = true
                showSpeakPrompt()
                startVoiceInput()
            } else if (binding.EditNum2.text.isEmpty()) {
                isFirstField = false
                showSpeakPrompt()
                startVoiceInput()
            } else {
                Toast.makeText(this, "Both numbers already entered!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showSpeakPrompt() {
        if (isFirstField){
            Toast.makeText(this,"Speak your First number",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Speak your Second number",Toast.LENGTH_SHORT).show()
        }
    }
    private fun startVoiceInput(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, if (isFirstField) "Speak first number..." else "Speak second number...")

        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception){
            Toast.makeText(this,"Voice input not supported",Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()){
                if (isFirstField){
                    binding.EditNum1.setText(result[0])
                    isFirstField = false
                }else{
                    binding.EditNum2.setText(result[0])
                    isFirstField = true

                }
            }
        }
    }
}






