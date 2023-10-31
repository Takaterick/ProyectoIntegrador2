const backendInscripcion = "http://localhost:8080/api/v1/";
const formInscripcion = $("#formInscripcion")[0];

const selectTalleres = $("#selectTaller");

const guardarInscripcion = () => {
  $("#btnAgregar").on("click", function () {
    formInscripcion.reset();
    $("#titulo-form").html("Agregar Inscripcion");
    $("#color-modal").removeClass("bg-success");
    $("#btnActualizar").hide();
    $("#btnGuardar").show();
    $("#modalInscripcion").modal("show");
  });
  $("#btnGuardar").on("click", function () {
    let datosForm = new FormData(formInscripcion);
    let datosInscripcion = {
      fechaTaller: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + ":00.000Z",
      horaInicio: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + "-03:00",
      horaFin: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaFinTaller")+ "-03:00",
      cupos: datosForm.get("cuposTaller"),
      taller: {
        id_taller: datosForm.get("taller"),
      },
      empleado: {
        idEmpl: datosForm.get("idEmpl"),
      },
    };
    console.log(datosInscripcion.horaInicio);
    $.ajax({
      type: "POST",
      url: backendInscripcion + "inscripciones/guardar",
      contentType: "application/json",
      data: JSON.stringify(datosInscripcion),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          alertasInsc(response.mensaje, response.tipo);
          $("#modalInscripcion").modal("hide");
          formInscripcion.reset();
        } else {
          alertasInsc(response.mensaje, response.tipo);
        }
        $("#modalInscripcion").modal("hide");
        alertasInsc("Inscripción guardada", "success");
        setTimeout(function () {
          location.reload();
        }, 800);
      },
    });
  });
};

const listarCbxTalleres = () => {
    $.ajax({
        url: backendInscripcion + "talleres/lista",
        type: "GET",
        dataType: "json",
        success: function (response) {
            response.forEach((taller) => {
                selectTalleres.append(
                `<option value="${taller.id_taller}">${taller.nomTaller}</option>`
                );
            });
        },
    });
};

const eliminarInscripciones = () => {
    $(".btnEliminar").on("click", function () {
        let id = $(this).data("id");
        Swal.fire({
            title: "¿Está seguro de eliminar el Taller?" + id,
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: "DELETE",
                    url: backendInscripcion + "inscripciones/eliminar/" + id,
                    dataType: "text",
                    success: function (response) {
                      alertasInsc("Taller eliminado correctamente", "success");
                        setTimeout(function () {
                            location.reload();
                        }, 800);
                    },
                });
            }
        });
    });
}

const editarInscripciones = () => {
  $(".btnEditar").on("click", function () {
    $("#titulo-form").html("Actualizar Inscripción");
    $("#color-modal").addClass("bg-success");
    $("#btnGuardar").hide();
    $("#btnActualizar").show();
    let id = $(this).data("id");
    $.ajax({
      type: "GET",
      url: backendInscripcion + "inscripciones/buscar/" + id,
      dataType: "json",
      success: function (response) {
        formInscripcion.idInscripcion.value = response.idInsTaller;
        formInscripcion.fechaTaller.value = moment(response.fechaTaller).format('YYYY-MM-DD');
        formInscripcion.horaInicioTaller.value = response.horaInicio;
        formInscripcion.horaFinTaller.value = response.horaFin;
        formInscripcion.taller.value = response.taller.id_taller;
        formInscripcion.cuposTaller.value = response.cupos;
        console.log(response.taller.id_taller);
      },
    });
    $("#modalInscripcion").modal("show");
  })
}

const actualizarInscripciones = () => {
  $("#btnActualizar").on("click", function(){
    const datosForm = new FormData(formInscripcion);
    let datosInscripcion = {
      idInsTaller: datosForm.get("idInscripcion"),
      fechaTaller: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + "-03:00",
      horaInicio: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + "-03:00",
      horaFin: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaFinTaller")+ "-03:00",
      cupos: datosForm.get("cuposTaller"),
      taller: {
        id_taller: datosForm.get("taller"),
      }
    };
    console.log(datosInscripcion);
    $.ajax({
      type: "PUT",
      url: backendInscripcion + "inscripciones/actualizar/" + datosInscripcion.idInsTaller,
      data: JSON.stringify(datosInscripcion),
      dataType: "json",
      contentType: "application/json",
      success: function (response) {
        $("#modalInscripcion").modal("hide");
        alertasInsc("Inscripción actualizada", "success");
        setTimeout(function () {
          location.reload();
        }, 800);
      }
    });
  })
}

const listarAsistencias = () => {
  $(".asistencia").on("click", function () {
    $("#modalInscripcion").modal("show");
  })
}

const alertasInsc = (mensaje, icono) => {
    Swal.fire({
      title: mensaje,
      icon: icono,
      showConfirmButton: false,
      timer: 2000,
    });
  };

$(document).ready(function () {
    listarCbxTalleres();
    guardarInscripcion();
    eliminarInscripciones();
    editarInscripciones();
    actualizarInscripciones();
    listarAsistencias();

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
