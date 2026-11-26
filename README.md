# CRUD de productos

La página de Apps Script usa GET y POST en `/api/productos`, y PUT y DELETE en `/api/productos/{id}`. Spring Boot guarda `id`, `nombre` y `precio` en la tabla `productos` de H2. La base se guarda en `data/productos.mv.db`.

1. Desde esta carpeta, inicia Spring Boot con `mvn spring-boot:run` (Java 17).
2. Usa la URL HTTPS externa del servidor. Por ejemplo, en Codespaces, reenvía el puerto HTTP 8080 y copia su dirección HTTPS pública para la demostración.
3. En Apps Script, agrega `PRODUCTOS_API_URL` en **Configuración del proyecto → Propiedades de la secuencia de comandos**. El valor es la URL base, sin `/api/productos`.
4. En **Implementar**, selecciona **App web**. Para presentar en tu cuenta puedes usar **Solo yo**. Si ya existe una implementación de este proyecto, actualízala a una versión nueva.
5. Abre la página y agrega un cuaderno de prueba, cambia su precio y elimínalo.

`Index.html` muestra el formulario. `Code.gs` envía las peticiones REST. `ProductoController.java` recibe las peticiones y `ProductoRepository.java` consulta y guarda en H2. Usa datos de prueba y cierra el reenvío público después de exponer.

## Publicar en Render

1. Sube esta carpeta a un repositorio propio del CRUD de productos, con `render.yaml` en la raíz.
2. En Render, crea un **Blueprint**, selecciona ese repositorio y aplica el plan gratuito de la API y PostgreSQL. El perfil `render` conecta la API a la base por la red interna de Render. La tabla se crea al arrancar.
3. Cuando la API esté activa, abre `/api/productos` en el dominio HTTPS que te asignó Render. Debe verse `[]` al principio. Usa datos de demostración, ya que la API es accesible desde Internet.
4. Pega el dominio base de Render en `PRODUCTOS_API_URL` de Apps Script y recarga la app web.

La base de Render empieza vacía. Para trabajar en tu computadora se sigue usando H2. En el plan gratuito el servicio puede tardar en arrancar después de un período de inactividad y la base PostgreSQL gratuita expira a los 30 días.
