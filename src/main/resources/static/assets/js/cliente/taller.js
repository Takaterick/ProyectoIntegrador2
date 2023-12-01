const abrirTaller = () => {
    $(".mostrarTaller").click(function () {
        $(".descripcion").empty();
        let idModal = $(this).data("modal");
        $("#"+idModal).modal("show");
    })
}

const inscribirTaller = () => {
  $(".formInscripcionCliente").submit(function (e) {
    e.preventDefault();
    let datosForm = new FormData(e.target);
    let datosInscTaller = {
      cliente: {
        id_cli: datosForm.get("idCliente"),
      },
      inscripcionTaller: {
        idInsTaller: datosForm.get("idInsTaller")
      }
    }
    Swal.fire({
      title: '¿Esta seguro de inscribirse a este taller ? ',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, inscribirme!'
    }).then((result) => {
      if(result.isConfirmed){
        $.ajax({
          type: "POST",
          url: "https://luckygym.azurewebsites.net/api/v1/asistencias/guardar",
          contentType: "application/json",
          data: JSON.stringify(datosInscTaller),
          dataType: "json",
          success: function (response) {
            //ocultar el modal
            $("#modalTaller"+datosInscTaller.inscripcionTaller.idInsTaller).modal("hide");
            alertasInscCliente("Inscripcion exitosa", "success");
            setTimeout(function () {
              location.reload();
           }, 800);
          }
        });
      }

    })
})
}

const eliminarInscripcionTaller = () => {
  $(".btnEliminar").on("click", function () {
    let id = $(this).data("id");
    Swal.fire({
      title: "¿Está seguro de eliminar la inscripción?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
    }).then((result) => {
      if(result.isConfirmed){
        $.ajax({
          type: "DELETE",
          url: "https://luckygym.azurewebsites.net/api/v1/asistencias/eliminar/"+id,
          success: function (response) {
            alertasInscCliente("Inscripción eliminada", "success");
            setTimeout(function () {
              location.reload();
           }, 800);
          }
        });
      }
    })
    console.log(id);
  })

}

const alertasInscCliente = (mensaje, icono) => {
  Swal.fire({
    title: mensaje,
    icon: icono,
    showConfirmButton: false,
    timer: 1000,
  });
};


$(document).ready(function () {
    abrirTaller();
    inscribirTaller();
    eliminarInscripcionTaller();
    $(".dias-restantes").each(function () {
      let fecha = $(this).data("restantes");
      let hora = $(this).data("hora");
      let mes = fecha.split("-")[1];
        fecha = moment(fecha).format("YYYY-MM-DD") + "T" + hora;   
        $(this).text(moment(fecha).endOf(mes).fromNow());
    });

    $(".fecha-taller").each(function () {
      let fecha = $(this).data("fecha");
      //condicional de si la fecha es igual a la de hoy que ponga como texto hoy
      $(this).text(moment(fecha, "YYYY-MM-DD").format("DD MMM YYYY"));
    });
});