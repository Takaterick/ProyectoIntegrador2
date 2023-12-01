const backendInscripcion = "https://luckygym.azurewebsites.net/api/v1/";
const formInscripcion = $("#formInscripcion")[0];
const tablaAsistencia = $("#tablaAsistencia").DataTable({
  language: {
    url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
  },
});
const selectTalleres = $("#selectTaller");

const guardarInscripcion = () => {
  $("#btnAgregar").on("click", function () {
    limpiarFormulario();
    $("#titulo-form").html("Agregar Inscripcion");
    $("#color-modal").removeClass("bg-success");
    $("#color-modal").addClass("bg-primary");
    $("#btnActualizar").hide();
    $("#btnGuardar").show();
    $("#modalInscripcion").modal("show");
  });
  $("#btnGuardar").on("click", function () {
    let datosForm = new FormData(formInscripcion);
    let fechaTaller = datosForm.get("fechaTaller");
    let horaInicio = datosForm.get("horaInicioTaller");
    let horaFin = datosForm.get("horaFinTaller");
    if (fechaTaller!="" && horaInicio!=""){
      fechaTaller = moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + ":00.000Z";
    } else if (fechaTaller!="" && horaInicio=="") {
      fechaTaller = moment(datosForm.get("fechaTaller")).format();
    }

    if (horaInicio!=""){
      horaInicio = moment().format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + "-05:00";
    }

    if (horaFin!=""){
      horaFin = moment().format('YYYY-MM-DD') + "T"+ datosForm.get("horaFinTaller")+ "-05:00";
    }

    let datosInscripcion = {
      fechaTaller: fechaTaller,
      horaInicio: horaInicio,
      horaFin: horaFin,
      cupos: datosForm.get("cuposTaller"),
      taller: {
        id_taller: datosForm.get("taller"),
      },
      empleado: {
        idEmpl: datosForm.get("idEmpl"),
      },
    };
    //console.log(datosInscripcion.horaInicio);
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
          setTimeout(function () {
               location.reload();
            }, 800);
        } else {
          alertasInsc(response.mensaje, response.tipo);
        }
      },
    });
  });
};

const listarCbxTalleres = () => {
  selectTalleres.empty();
  selectTalleres.append(`<option selected="">-- Elija una opcion --</option>`)
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
            title: "¿Está seguro de eliminar el Taller?",
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
      fechaTaller: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + "-05:00",
      horaInicio: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaInicioTaller") + "-05:00",
      horaFin: moment(datosForm.get("fechaTaller")).format('YYYY-MM-DD') + "T"+ datosForm.get("horaFinTaller")+ "-05:00",
      cupos: datosForm.get("cuposTaller"),
      taller: {
        id_taller: datosForm.get("taller"),
      }
    };
    //console.log(datosInscripcion);
    $.ajax({
      type: "PUT",
      url: backendInscripcion + "inscripciones/actualizar/" + datosInscripcion.idInsTaller,
      contentType: "application/json",
      data: JSON.stringify(datosInscripcion),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          alertasInsc(response.mensaje, response.tipo);
          $("#modalInscripcion").modal("hide");
          formInscripcion.reset();
          setTimeout(function () {
            location.reload();
          }, 800);
        } else {
          alertasInsc(response.mensaje, response.tipo);
        }
        
      }
    });
  })
}

const listarAsistencias = () => {
  $(".btnAsistencia").on("click", function () {
    let fecha = $(this).data("fecha");
    let taller = $(this).data("taller");
    let id = $(this).data("idtaller");
    fecha = moment(fecha, "YYYY-MM-DD").format("DD MMM YYYY");
    //console.log(id);
    $("#titAsistencia").text(taller +" - "+fecha);
    tablaAsistencia.clear();
    $.ajax({
      type: "GET",
      url: backendInscripcion + "asistencias/buscar/taller/"+id,
      dataType: "json",
      success: function (response) {
        $.each(response, function (i, value) {
          tablaAsistencia.row.add([
            i+1,
            value.cliente.nom_cli + " " + value.cliente.ape_cli,
            value.asistencia,
            `<input type="checkbox" ${value.asistencia == 'Asistió' ? 'checked' : ''} class="form-check-input asistencia" data-id="${value.idAsistencia}">`,
          ]).draw();
        })
        
      },
    });
    $("#modalAsistencia").modal("show");
  })
}



//METODO LIMPIAR
const limpiarFormulario = () => {
  $("#id_inscripcion").val("");
  $("#fecha_taller").val("");
  $("#selectTaller").val("");
  $("#cupos_taller").val("");
  $("#horaInicio_taller").val("");
  $("#horaFin_taller").val("");
};

const alertasInsc = (mensaje, icono) => {
    Swal.fire({
      title: mensaje,
      icon: icono,
      showConfirmButton: false,
      timer: 2000,
    });
  };

  const alertasToast = (mensaje, icono) => {
    const Toast = Swal.mixin({
      toast: true,
      //positicon superior derecha
      position: "top-end",
      showConfirmButton: false,
      timer: 2000,
      timerProgressBar: true,
    });
    Toast.fire({
      icon: icono,
      title: mensaje,
    });
  }

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

    $(document).change(".asistencia", function (e) { 
      e.preventDefault();
      let id = e.target.dataset.id;
      let asistencia = e.target.checked;
      $.ajax({
        type: "GET",
        url: backendInscripcion + "asistencias/marcar/"+id+"/"+asistencia,
        success: function (response) {
          if(asistencia){
            alertasToast("Asistencia registrada", "success");
          } else {
            alertasToast("Asistencia eliminada", "success");
          }
        }
      });
    });
});
