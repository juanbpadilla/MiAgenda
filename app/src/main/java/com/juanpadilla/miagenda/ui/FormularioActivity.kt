package com.juanpadilla.miagenda.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.juanpadilla.miagenda.MainActivity
import com.juanpadilla.miagenda.R
import com.juanpadilla.miagenda.config.Constantes
import com.juanpadilla.miagenda.databinding.ActivityFormularioBinding
import com.juanpadilla.miagenda.databinding.ItemListBinding
import com.juanpadilla.miagenda.dialogos.BorrarDialogo
import com.juanpadilla.miagenda.viewmodel.FormularioViewModel

class FormularioActivity : AppCompatActivity(),BorrarDialogo.BorrarListener {

    lateinit var binding: ActivityFormularioBinding
    lateinit var viewModel : FormularioViewModel
    lateinit var dialogoBorrar: BorrarDialogo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogoBorrar = BorrarDialogo(this)

        viewModel = ViewModelProvider(this).get()
        viewModel.operacion = intent.getStringExtra(Constantes.OPERACION_KEY)!!
        binding.modelo = viewModel
        binding.lifecycleOwner = this

        viewModel.operacionExistosa.observe(this, Observer {
            if(it){
                mostrarMensaje("Operación Exitosa")
                irAlInicio()
            }else{
                mostrarMensaje("Ocurrió un error")
            }
        })

        if(viewModel.operacion.equals((Constantes.OPERACION_EDITAR))){
            viewModel.id.value = intent.getLongExtra(Constantes.ID_PERSONAL_KEY,0)
            viewModel.cargarDatos()
            binding.linearEditar.visibility = View.VISIBLE
            binding.btnGuardar.visibility = View.GONE
        }else{
            binding.linearEditar.visibility = View.GONE
            binding.btnGuardar.visibility = View.VISIBLE
        }

        binding.btnBorrar.setOnClickListener {
            mostrarDialogo()
        }
    }

    private fun mostrarDialogo() {
        dialogoBorrar.show(supportFragmentManager,"Dialogo Borrar")
    }

    private fun mostrarMensaje(s: String) {
        Toast.makeText(applicationContext,s,Toast.LENGTH_LONG).show()
    }

    private fun irAlInicio() {
        val intent = Intent(applicationContext,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun borrarPersonal() {
        viewModel.eliminarPersonal()
    }
}