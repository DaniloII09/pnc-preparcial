**Danilo Isaac Iraheta Menjivar**
**00377223**

# Reglas de Negocio

## 1. ISBN único
- No se puede registrar un libro con un ISBN ya existente.

## 2. Año de publicación válido
- El año debe ser mayor o igual a `1900`.
- El año no puede ser mayor al año actual.

## 3. Cantidad de páginas válida
- El número de páginas debe ser mayor a `10`.

## 4. Filtrado de libros
- Por autor: `GET /books?author=Stephen King`
- Por idioma: `GET /books?language=English`
- Por rango de páginas: `GET /books?minPages=100&maxPages=300`
- No se permiten parámetros de filtrado desconocidos.
- Si se filtra por rango de páginas, ambos parámetros `minPages` y `maxPages` deben ser proporcionados.

## 5. Modificación de título y/o idioma únicamente
- Solo se permite modificar el `title` y/o el `language` de un libro.
- Al menos uno de los dos campos debe ser proporcionado en la actualización.
- Los demás campos (`author`, `isbn`, `publicationYear`, `pages`) no pueden modificarse.

## 6. Títulos con solo números no permitidos
- No se permiten títulos compuestos únicamente por números.
- Ejemplo: `"123456"` no es válido.