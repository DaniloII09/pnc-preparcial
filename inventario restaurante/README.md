**Danilo Isaac Iraheta Menjivar**
**00377223**

# Reglas de Negocio

## 1. Nombre único
- No se permite registrar dos productos con el mismo nombre, sin importar mayúsculas/minúsculas.

## 2. Precio válido
- El precio debe ser mayor a cero.
- No se permiten productos gratuitos.

## 3. Stock y disponibilidad
- Si `quantity = 0`, `available` cambia automáticamente a `false`.
- Si `quantity > 0`, se puede cambiar `available` a `true` manualmente.

## 4. Protección de ingredientes
- No se puede eliminar un producto de categoría `INGREDIENT` si `available = true`.

## 5. Actualización de stock parcial
- Recibe un valor `amount` que puede ser positivo o negativo.
- No se permite que el stock resultante sea menor que `0`.

## 6. Filtrado por categoría y disponibilidad
- Los productos se pueden filtrar combinando `category` y `available`.
- Ejemplo: `GET /products/filter?category=FOOD&available=true`