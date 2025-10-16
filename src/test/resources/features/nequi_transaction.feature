#author: Adrian Hernández
#language: es

Característica: Transacción con Nequi en Wompi Sandbox

  Yo como Adrian
  Quiero realizar consumos de servicios api rest
  Para validar los diferentes estados de una transacción con Nequi

  Antecedentes:
    Dado "Adrian" obtiene la baseurl de la api
    Dado que el usuario obtiene los tokens de aceptación de Wompi

  Escenario: Crear una transacción aprobada con Nequi
    Cuando el usuario crea una transacción con Nequi
    Entonces la transacción debe ser aprobada

  Escenario: Crear una transacción declinada con Nequi
    Cuando el usuario crea una transacción declinada con Nequi
    Entonces la transacción debe ser declinada

  Escenario: Consultar por id inexistente
    Cuando el usuario crea una transacción con Nequi
    Y consulta por id inexistente
    Entonces la transacción debe devolver error

  Escenario: validar errores de validación
    Cuando el usuario crea una transacción con Nequi con datos invalidos
    Entonces la transacción debe fallar con el mensaje "El monto mínimo de una transacción es $1,500 exceptuando impuestos"



