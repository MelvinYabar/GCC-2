# CRUD de Pokémon

La página de Apps Script usa GET y POST en `/api/pokemon`, y PUT y DELETE en `/api/pokemon/{id}`. Spring Boot guarda `id`, `nombre` y `tipo` en la tabla `pokemon` de H2. La base se guarda en `data/pokemon.mv.db`.

1. Desde esta carpeta, inicia Spring Boot con `mvn spring-boot:run` (Java 17).
2. Usa la URL HTTPS externa del servidor. Por ejemplo, en Codespaces, reenvía el puerto HTTP 8080 y copia su dirección HTTPS pública para la demostración.
3. En Apps Script, agrega `PRODUCTOS_API_URL` en **Configuración del proyecto → Propiedades de la secuencia de comandos**. El valor es la URL base, sin `/api/pokemon`.
4. En **Implementar**, selecciona **App web**. Para presentar en tu cuenta puedes usar **Solo yo**. Si ya existe una implementación de este proyecto, actualízala a una versión nueva.
5. Abre la página y agrega un Pokémon de prueba, cambia su tipo y elimínalo.

`Index.html` muestra el formulario. `Code.gs` envía las peticiones REST. `PokemonController.java` recibe las peticiones y `PokemonRepository.java` consulta y guarda los Pokémon. Usa datos de prueba y cierra el reenvío público después de exponer.

## Publicar en Render

1. Usa el repositorio GCC-2, con `render.yaml` en la raíz.
2. En Render, crea un **Blueprint**, selecciona ese repositorio y aplica el plan gratuito de la API y PostgreSQL. El perfil `render` conecta la API a la base por la red interna de Render. La tabla se crea al arrancar.
3. Cuando la API esté activa, abre `/api/pokemon` en el dominio HTTPS que te asignó Render. Debe verse `[]` al principio. Usa datos de demostración, ya que la API es accesible desde Internet.
4. Pega el dominio base de Render en `PRODUCTOS_API_URL` de Apps Script y recarga la app web.

La tabla `pokemon` empieza vacía. Para trabajar en tu computadora se sigue usando H2. En el plan gratuito el servicio puede tardar en arrancar después de un período de inactividad y la base PostgreSQL gratuita expira a los 30 días.
