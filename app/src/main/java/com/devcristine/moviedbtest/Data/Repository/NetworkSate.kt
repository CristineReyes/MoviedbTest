package com.devcristine.moviedbtest.Data.Repository


/**
 * Created by Cristine R.M. on 26/09/2021.
 * DevCristineAguirre
 */


enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}


class NetworkSate  (val status: Status, val msg: String) {


    companion object{
        val LOADED : NetworkSate
        val LOADING : NetworkSate
        val ERROR : NetworkSate

        val LIST_END: NetworkSate

        init {
            LOADED = NetworkSate(Status.SUCCESS, "Conectado")
            LOADING = NetworkSate(Status.RUNNING, "Ejecutando/ Running")
            ERROR = NetworkSate(Status.FAILED, "Error en la conexion")
            LIST_END = NetworkSate(Status.FAILED, "Final de la lista ")

        }

    }
}