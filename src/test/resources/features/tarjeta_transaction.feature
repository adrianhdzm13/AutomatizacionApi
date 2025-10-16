#author: Adrian Hernández
#language: es

Característica: Transacción con tarjeta en Wompi Sandbox

  Yo como Adrian
  Quiero realizar consumos de servicios api rest
  Para validar los diferentes estados de una transacción con tarjeta

  Antecedentes:
    Dado "Adrian" obtiene la baseurl de la api
    Dado que el usuario obtiene los tokens de aceptación de Wompi

  Escenario: Crear una transacción aprobada con tarjeta
    Cuando el usuario crea una transacción con tarjeta
    Entonces la transacción debe ser aprobada

  Escenario: Crear una transacción declinada con tarjeta
    Cuando el usuario crea una transacción declinada con tarjeta
    Entonces la transacción debe ser declinada