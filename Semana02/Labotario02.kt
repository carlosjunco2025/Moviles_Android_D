// 1. ABSTRACCIÓN: Entidad base abstracta
abstract class Producto(
    val id: String,
    open val nombre: String,
    open val precioBase: Double
) {
    // Método abstracto obligatorio
    abstract fun calcularPrecioFinal(): Double

    // Método concreto común
    open fun obtenerDetalle(): String {
        return "[$id] $nombre - Base: S/ $precioBase"
    }
}

// 2. ENCAPSULAMIENTO: Control estricto del estado interno
class CarritoTienda {
    private val productosInternos = mutableListOf<Producto>()

    fun agregar(producto: Producto) {
        productosInternos.add(producto)
        println("✔ Registrado: ${producto.nombre}")
    }

    // Retorna una copia inmutable para no exponer la lista original
    fun obtenerLista(): List<Producto> = productosInternos.toList()

    fun estaVacio(): Boolean = productosInternos.isEmpty()
}
