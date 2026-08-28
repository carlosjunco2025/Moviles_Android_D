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
