const backend = "http://localhost:8080/api/v1/suscripciones";
const formEmpleado = $("#formEmpleado")[0];

const registrarSuscripcion = () => {
  $("#btnRegistrar").on("click", function () {
    let datosForm = new FormData(formRegistro);
    let suscripcion = {
      cliente: {
        nom_cli: datosForm.get("firstname"),
        ape_cli: datosForm.get("lastname"),
        dni_cli: datosForm.get("dni"),
        tel_cli: datosForm.get("phone"),
        correo_cli: datosForm.get("email"),
        dir_cli: datosForm.get("address"),
        usuario: {
          usuario: datosForm.get("usuario"),
          contrasenia: datosForm.get("contrasenia"),
        },
      },
      membresia: {
        id_sus: datosForm.get("suscripcion"),
      },
    };

    console.log(suscripcion);

    $.ajax({
      url: backend + "/guardar",
      type: "POST",
      data: JSON.stringify(suscripcion),
      contentType: "application/json",
      success: function (data) {
        Swal.fire({
            icon: 'success',
            title: 'Se registro exitosamente, realice el pago respectivo para activar su cuenta',
            text: 'Por favor, revise ese link de whatsapp para solicitar',
            showDenyButton: false,
            showCancelButton: false,
            confirmButtonText: 'Cancelado',
            footer: `<a href="https://api.whatsapp.com/send?phone=51964835210&text=Me%20gustaria%
            20pagar%20mi%20suscripcion%3A%0AMi%20usuario%20es%3A%20${suscripcion.cliente.usuario.usuario}" 
            target="_BLANK">Contactar Pago</a>`
        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                document.location.href = "";
            }
        });
        formRegistro.reset();
      },
      error: function (error) {
        console.log(error);
      },
    });
  });
};

const alertas = (mensaje, icono) => {
    Swal.fire({
      title: mensaje,
      icon: icono,
      showConfirmButton: false,
      timer: 1000,
    });
  };

$(document).ready(function () {
    registrarSuscripcion();
})
