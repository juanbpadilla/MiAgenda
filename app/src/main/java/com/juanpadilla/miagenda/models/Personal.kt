package com.juanpadilla.miagenda.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Personal (
    @PrimaryKey(autoGenerate = true)
    var idEmpleado:Long,
    var nombre:String,
    var apellidos:String,
    var email:String,
    var telefono:String,
    var edad:Int
)