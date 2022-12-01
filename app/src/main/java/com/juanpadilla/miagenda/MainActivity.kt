package com.juanpadilla.miagenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanpadilla.miagenda.adaptadores.PersonalAdapter
import com.juanpadilla.miagenda.config.Constantes
import com.juanpadilla.miagenda.databinding.ActivityFormularioBinding
import com.juanpadilla.miagenda.databinding.ActivityMainBinding
import com.juanpadilla.miagenda.ui.FormularioActivity
import com.juanpadilla.miagenda.viewmodel.MainViewModel
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()

        binding.lifecycleOwner = this
        binding.modelo = viewModel

        viewModel.iniciar()

        binding.miRecycler.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }

        viewModel.personalList.observe(this, Observer {
            binding.miRecycler.adapter = PersonalAdapter(it)
        })

        binding.btnAbrirFormulario.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra(Constantes.OPERACION_KEY,Constantes.OPERACION_INSERTAR)
            startActivity(intent)
        }

        binding.etBuscar.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.toString().isNotEmpty()) {
                    viewModel.buscarPersonal()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }
}