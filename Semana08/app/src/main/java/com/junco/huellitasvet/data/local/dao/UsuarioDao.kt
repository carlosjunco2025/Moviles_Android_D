package com.junco.huellitasvet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.junco.huellitasvet.data.local.entity.Usuario

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun buscarPorCredenciales(correo: String, clave: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Usuario?

    @Query("SELECT COUNT(*) FROM usuarios WHERE correo = :correo")
    suspend fun cuantosConCorreo(correo: String): Int

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun cantidad(): Int

    @Insert
    suspend fun insertar(usuario: Usuario): Long

    @Insert
    suspend fun insertarVarios(usuarios: List<Usuario>)
}
