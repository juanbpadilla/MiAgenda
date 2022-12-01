package com.juanpadilla.miagenda.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanpadilla.miagenda.config.Constantes
import com.juanpadilla.miagenda.config.PersonalApp.Companion.db
import com.juanpadilla.miagenda.models.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormularioViewModel:ViewModel() {
    var id = MutableLiveData<Long>()
    var nombre = MutableLiveData<String>()
    var apellidos = MutableLiveData<String>()
    var telefono = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var edad = MutableLiveData<Int>()
    var operacion = Constantes.OPERACION_INSERTAR
    var operacionExistosa = MutableLiveData<Boolean>()

    init {
        edad.value = 18
    }

    fun guardarUsuario(){
        if(validarInformacion()) {
            var mPersonal = Personal(0, nombre.value!!, apellidos.value!!, email.value!!, telefono.value!!, edad.value!!)
            when(operacion){
                Constantes.OPERACION_INSERTAR->{
//                Log.d("mensaje", "nombre ${nombre.value}")
//                Log.d("mensaje", "email ${email.value}")

                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO){
                            db.personalDao().insert(
                                arrayListOf<Personal>(
                                    mPersonal
                                )
                            )
                        }
                        operacionExistosa.value = result.isNotEmpty()
                    }
                }
                Constantes.OPERACION_EDITAR->{
                    mPersonal.idEmpleado = id.value!!
                    viewModelScope.launch {
                        val result = withContext(Dispatchers.IO){
                            db.personalDao().update(mPersonal)
                        }

                        operacionExistosa.value = (result>0)
                    }
                }
            }
        }else{
            operacionExistosa.value = false
        }
    }

    fun cargarDatos() {
        viewModelScope.launch {
            var persona = withContext(Dispatchers.IO) {
                db.personalDao().getById(id.value!!)
            }

            nombre.value = persona.nombre
            apellidos.value = persona.apellidos
            telefono.value = persona.telefono
            email.value = persona.email
            edad.value = persona.edad
        }
    }

    private fun validarInformacion():Boolean{
//        devuelve true si la informacion no es nula ni vacia
        return !(nombre.value.isNullOrEmpty()) ||
                apellidos.value.isNullOrEmpty() ||
                email.value.isNullOrEmpty() ||
                telefono.value.isNullOrEmpty() ||
                edad.value!! <= 0 || edad.value!! >= 100
    }

    fun eliminarPersonal() {
        var mPersonal = Personal(id.value!!,"","","","",0)
        viewModelScope.launch {
            var result = withContext(Dispatchers.IO){
                db.personalDao().delete(mPersonal)
            }

            operacionExistosa.value = (result>0)
        }
    }
}