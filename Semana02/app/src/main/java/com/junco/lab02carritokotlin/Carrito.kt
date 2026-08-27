package com.junco.lab02carritokotlin

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d  S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Carlos Junco"
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente")
    println()

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Parlantes soni ", 120.0, 1))
    carrito.add(Producto("Audifonos RedDragon", 650.0, 1))

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }

    println()
    println("Cantidad de productos: ${carrito.size}")
    println()

    mostrarDetalle(carrito)

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("%-25s S/ %8.2f", "Subtotal :", subtotal))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
    println(String.format("%-25s S/ %8.2f", "TOTAL :", total))
    println()

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " + String.format("(S/ %.2f)", masCaro.precio))
    }

    val descuento = calcularDescuento(total)
    val totalConDescuento = total - descuento

    if (descuento > 0.0) {
        println(String.format("Descuento aplicado: S/ %.2f", descuento))
    } else {
        println("Descuento aplicado: S/ 0.00 (No aplica)")
    }

    println(String.format("TOTAL CON DESCUENTO: S/ %.2f", totalConDescuento))


  // PARTE VI: RETO ADICIONAL

    println()
    println("=========================================")
    println(" RETO ADICIONAL ")
    println("=========================================")

    // 1. Probar la función buscarProducto
    println("\n--> Buscando producto 'Laptop HP'...")
    val encontrado = buscarProducto(carrito, "Laptop HP")
    if (encontrado != null) {
        println("Resultado: Encontrado -> ${encontrado.nombre} (S/ ${encontrado.precio})")
    } else {
        println("Resultado: No se encontro el producto.")
    }

    // 2. Eliminar un producto usando removeIf
    println("\n--> Eliminando 'Mouse Logitech' del carrito con removeIf...")
    carrito.removeIf { it.nombre.equals("Mouse Logitech", ignoreCase = true) }

    // 3. Mostrar detalle y totales actualizados
    println("\n--> DETALLE Y TOTALES ACTUALIZADOS:")
    mostrarDetalle(carrito)

    val subtotalActualizado = calcularSubtotal(carrito)
    val igvActualizado = calcularIGV(subtotalActualizado)
    val totalActualizado = calcularTotal(subtotalActualizado, igvActualizado)
    val descuentoActualizado = calcularDescuento(totalActualizado)
    val totalFinalActualizado = totalActualizado - descuentoActualizado

    println(String.format("%-25s S/ %8.2f", "Subtotal :", subtotalActualizado))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igvActualizado))
    println(String.format("%-25s S/ %8.2f", "TOTAL :", totalActualizado))
    println(String.format("Descuento aplicado: S/ %.2f", descuentoActualizado))
    println(String.format("TOTAL CON DESCUENTO: S/ %.2f", totalFinalActualizado))
}