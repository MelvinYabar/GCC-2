# CRUD de Pokémon

La página de Google Apps Script llama a la API de Spring Boot. Spring Boot guarda los Pokémon en la tabla `pokemon` de PostgreSQL en Supabase. La tabla tiene `id`, `nombre` y `tipo`.

## 1. Conectar Supabase

1. Abre tu proyecto en Supabase y entra a **Connect → Session pooler**. Copia el host y el usuario que aparecen ahí. Esta conexión usa el puerto `5432` y funciona por IPv4. [Conexiones de Supabase](https://supabase.com/docs/guides/database/connecting-to-postgres)
2. En [Codespaces secrets de GitHub](https://github.com/settings/codespaces), crea estos tres secretos y habilítalos para `MelvinYabar/GCC-2`.

| Nombre | Valor |
| --- | --- |
| `SUPABASE_DB_URL` | `jdbc:postgresql://HOST_DEL_SESSION_POOLER:5432/postgres?sslmode=require` |
| `SUPABASE_DB_USER` | El usuario de **Session pooler**, por ejemplo `postgres.gpbmbgjhuahdwdttybid` |
| `SUPABASE_DB_PASSWORD` | La contraseña de la base de datos de Supabase |

En la URL, reemplaza `HOST_DEL_SESSION_POOLER` por el host exacto que muestra Supabase. Si usas una red con acceso al host directo, puedes poner `jdbc:postgresql://db.gpbmbgjhuahdwdttybid.supabase.co:5432/postgres?sslmode=require` y el usuario `postgres`. La contraseña va únicamente en su secreto.

## 2. Ejecutar Spring Boot en Codespaces

1. En [GCC-2](https://github.com/MelvinYabar/GCC-2), entra a **Code → Codespaces → Create codespace on main**. El entorno instala Java 17 y Maven y reenvía el puerto `8080`.
2. Si ya tenías un Codespace, trae los cambios con `git pull` y usa **Codespaces: Rebuild Container**. Si agregaste secretos con el Codespace en ejecución, detenlo y vuelve a abrirlo para que los reciba. [Secretos de Codespaces](https://docs.github.com/en/codespaces/managing-your-codespaces/managing-your-account-specific-secrets-for-github-codespaces)
3. Ejecuta en la terminal:

   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=supabase
   ```

4. Espera a que salga `Started PokemonApplication`. Spring Boot crea la tabla al iniciar si hace falta. En otra terminal puedes probar la API:

   ```bash
   curl -i http://localhost:8080/api/pokemon
   ```

   Debe responder `200` con un arreglo JSON. Si la tabla está vacía, verás `[]`.

## 3. Conectar la página de Apps Script

1. En **Ports**, cambia la visibilidad del `8080` a **Public** y copia la dirección HTTPS que aparece. Apps Script necesita poder llegar al puerto desde los servidores de Google. [Reenvío de puertos en Codespaces](https://docs.github.com/en/codespaces/developing-in-a-codespace/forwarding-ports-in-your-codespace)
2. En Apps Script abre **Configuración del proyecto → Propiedades de la secuencia de comandos**. En `PRODUCTOS_API_URL`, pega la dirección base del puerto, sin `/api/pokemon`. Si ya tienes la versión de Pokémon publicada, guarda el valor y recarga la página. Si vas a crear una app nueva, copia `apps-script/Code.gs` y `apps-script/Index.html`, y publícala como **Aplicación web**.
3. Para la exposición, crea `Pikachu` de tipo `Eléctrico`, verifica que aparezca, edítalo y luego elimínalo. Mantén el Codespace y Spring Boot activos mientras uses la página. Al terminar, vuelve a poner el puerto en **Private**.

## Cambiar código en vivo

Edita y guarda el archivo Java en Codespaces. Detén Spring Boot con `Ctrl+C` y ejecuta otra vez el mismo comando. Si usas el mismo Codespace y puerto, la URL que configuraste en Apps Script sirve para volver a probar.

| Archivo | Qué hace |
| --- | --- |
| `Pokemon.java` | Define las tres columnas y valida los datos. |
| `PokemonController.java` | Recibe `GET` y `POST` en `/api/pokemon` y `PUT` y `DELETE` en `/api/pokemon/{id}`. |
| `PokemonRepository.java` | Lee y guarda en PostgreSQL usando JPA. |
| `application-supabase.properties` | Lee la conexión a Supabase desde los secretos. |
| `apps-script/Code.gs` | Envía las peticiones REST a Spring Boot. |
| `apps-script/Index.html` | Muestra el formulario y la lista. |

Para una prueba local con H2, ejecuta `mvn spring-boot:run` sin activar el perfil `supabase`.
