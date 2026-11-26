function doGet() {
  return HtmlService.createHtmlOutputFromFile('Index').setTitle('Productos');
}

function listarProductos() {
  return llamarApi_('get', '');
}

function guardarProducto(id, producto) {
  return id
    ? llamarApi_('put', '/' + encodeURIComponent(id), producto)
    : llamarApi_('post', '', producto);
}

function eliminarProducto(id) {
  return llamarApi_('delete', '/' + encodeURIComponent(id));
}

function llamarApi_(metodo, ruta, datos) {
  const base = (PropertiesService.getScriptProperties().getProperty('PRODUCTOS_API_URL') || '').trim();
  if (!/^https:\/\//i.test(base)) {
    throw new Error('Configura PRODUCTOS_API_URL con la dirección HTTPS de Spring Boot.');
  }

  const opciones = { method: metodo, muteHttpExceptions: true, followRedirects: false };
  if (datos) {
    opciones.contentType = 'application/json';
    opciones.payload = JSON.stringify(datos);
  }

  const respuesta = UrlFetchApp.fetch(base.replace(/\/+$/, '') + '/api/productos' + ruta, opciones);
  const estado = respuesta.getResponseCode();
  if (estado === 204) return null;
  if (estado === 400) throw new Error('Revisa el nombre y el precio.');
  if (estado < 200 || estado >= 300) throw new Error('La API respondió HTTP ' + estado + '.');
  return JSON.parse(respuesta.getContentText());
}
