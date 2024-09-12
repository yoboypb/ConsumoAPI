package com.example.consumoapi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.consumoapi.api.RetrofitHelper
import com.example.consumoapi.model.Endereco
import com.example.consumoapi.model.EnderecoAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        RetrofitHelper.retrofit
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnEnviar:Button = findViewById(R.id.btnEnviar)

        btnEnviar.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                recuperarEndereco()
            }
        }
    }

    private suspend fun recuperarEndereco(){
        var retorno:Response<Endereco>? = null
        val cepDigitado = "45712368"

        try{
            val enderecoAPI = retrofit.create(EnderecoAPI::class.java)
            retorno = enderecoAPI.recuperarEndereco(cepDigitado)
        }catch (e: Exception){
        e.stackTrace
        }

        if(retorno!=null){
            if(retorno.isSuccessful){
                val endereco = retorno.body()
                val rua = endereco?.logradouro
                Log.i("teste","RUA:${rua}")

            }
        }
    }
}