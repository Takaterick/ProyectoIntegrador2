const backendSuscripcion = "http://localhost:8080/api/v1/suscripciones";
const formSuscripcion = $("#formSuscripcion")[0];
const selectMembresias = $("#selectMembresias");
const selectEstados = $("#selectEstado");
const tablaSuscripciones = $("#tablaSuscripciones").DataTable({
  language: {
    url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
  },
  //tamaño de columna
  columnDefs: [
    { width: "5%", targets: 0 },
    { 
      className: "text-center",
      width: "25%", targets: 1 },
    { 
      className: "text-center",
      width: "10  %", targets: 2 },
    { 
      className: "text-center",
      width: "20%", targets: 3 },
    { 
      className: "text-center",
      width: "10%", targets: 4 },
    { 
      className: "text-center",
      width: "20%", targets: 5 },
  ],
});

/*------------METODOS CRUD------------*/
const listarSuscripciones = () => {
  tablaSuscripciones.clear();
  $.ajax({
    type: "GET",
    url: backendSuscripcion + "/lista",
    dataType: "json",
    success: function (response) {
      $.each(response, function (i, value) {
        tablaSuscripciones.row
          .add([
            value.id,
            value.cliente.nom_cli + " " + value.cliente.ape_cli,
            value.membresia.nom_sus,
            //new Date(value.fechaInicio).toLocaleDateString("es-ES", { year: 'numeric', month: '2-digit', day: '2-digit'}) + " - " + new Date(value.fechaFin).toLocaleDateString(),
            //formato con moment
            moment(value.fechaInicio).format("DD/MM/YYYY") +
              " - " +
              moment(value.fechaFin).format("DD/MM/YYYY"),
            /* poner el estado con badges */
            `<span class="badge badge-pill bg-${value.estado == "Pagado" ? "success" : value.estado == "Pendiente" ? "warning" : "danger"}">${value.estado}</span>`,
            `
            <button type="button" data-idsus="${value.id}" data-suscripcion="${value.cliente.nom_cli}" id="btn-editar-suscripcion" class="btn btn-warning"><i class="fa fa-edit"></i></button>
            <button type="button" data-idsus="${value.id}" data-suscripcion="${value.cliente.nom_cli}" id="btn-eliminar-suscripcion" class="btn btn-danger"><i class="fa fa-trash"></i></button>
            <a href="${backendSuscripcion}/boucher?idSus=${value.id}" id="btn-imprimir" class="btn btn-success"><i class="fa fa-print"></i></a>
            `,
          ])
          .draw();
      });
    },
  });
};

//LISTAR ROLES
const cbxMembresias = () => {
  $("#selectMembresias").empty();
  $.ajax({
    url: backend + "/lista",
    type: "GET",
    dataType: "json",
    success: function (response) {
      response.forEach((membresia) => {
        selectMembresias.append(
          `<option value="${membresia.id_sus}">${membresia.nom_sus}</option>`
        );
      });
    },
  });
};

//si es vencido fecha actua, si es pendiente fecha fin

const editarSuscripcion = () => {
  $(document).on("click", "#btn-editar-suscripcion", function () {
    formSuscripcion.reset();
    //habilitar los campos
    formSuscripcion.meses.disabled = false;
    formSuscripcion.idMembresia.disabled = false;
    formSuscripcion.estado.disabled = false;
    let id = $(this).data("idsus");
    $.ajax({
      type: "GET",
      url: backendSuscripcion + "/buscar/" + id,
      dataType: "json",
      success: function (response) {
        formSuscripcion.idSuscripcion.value = response.id;
        formSuscripcion.cliente.value = response.cliente.nom_cli + " " + response.cliente.ape_cli;
        formSuscripcion.idMembresia.value = response.membresia.id_sus;
        formSuscripcion.estado.value = response.estado;
        formSuscripcion.fechaInicio.value = response.fechaInicio;
        formSuscripcion.fechaFin.value = response.fechaFin;
        /* si el estado es cancelado bloquear el estado */
        if (formSuscripcion.estado.value == "Cancelado" || formSuscripcion.estado.value == "Pendiente") {
          formSuscripcion.estado.disabled = true;
        }
      },
    });
    $("#modalEditarSuscripcion").modal("show");
  });
};

const actualizarSuscripcion = () => {
  //cuando el combobox de estado cambie a vencido, deshabilitar todos los campos
  $("#selectEstado").on("change", function () {
    if (formSuscripcion.estado.value == "Cancelado") {
      formSuscripcion.fechaInicio.disabled = true;
      formSuscripcion.fechaFin.disabled = true;
      formSuscripcion.meses.disabled = true;
      formSuscripcion.idMembresia.disabled = true;
    } else {
      formSuscripcion.fechaInicio.disabled = false;
      formSuscripcion.fechaFin.disabled = false;
      formSuscripcion.meses.disabled = false;
      formSuscripcion.idMembresia.disabled = false;
    }
  });
  //si el campo de estado es cancelado hacer que cuando haya meses en el campo meses, cambiar el estado a pagado
  $("#meses-icon").on("input", function () {
    if (formSuscripcion.estado.value != "Cancelado") {
      if (formSuscripcion.meses.value != "") {
        formSuscripcion.estado.value = "Pagado";
      }else{
        formSuscripcion.estado.value = "Pendiente";
      }
    }
  });
  
  $("#btn-editar-form-sus").on("click", function () {
    /* si el estado es cancelado tomar la fecha de inicio actual */
    if (formSuscripcion.estado.value == "Cancelado") {
      formSuscripcion.fechaInicio.value = moment().format("YYYY-MM-DD");
      formSuscripcion.fechaFin.value = moment().format("YYYY-MM-DD");
    }
    let datos = {
      id: formSuscripcion.idSuscripcion.value,
      fechaInicio: formSuscripcion.fechaInicio.value,
      fechaFin: formSuscripcion.fechaFin.value,
      //si el campo de meses esta vacio enviar la fechafin sino sumar la cantidad de meses a la fecha de inicio
      fechaFin: formSuscripcion.meses.value == "" ? formSuscripcion.fechaFin.value :moment(formSuscripcion.fechaFin.value).add(formSuscripcion.meses.value, "months"),
      //si el campo de meses esta vacio enviar el estado como cancelado sino enviar el estado del combobox
      estado: formSuscripcion.meses.value == "" ? formSuscripcion.estado.value : "Pagado",
      membresia: {
        id_sus: formSuscripcion.idMembresia.value,
      },
    };
    console.log(datos.estado);
    $.ajax({
      type: "PUT",
      url: backendSuscripcion + "/actualizar/"+datos.id,
      data: JSON.stringify(datos),
      dataType: "json",
      contentType: "application/json",
      success: function (response) {
        $("#modalEditarSuscripcion").modal("hide");
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Suscripcion actualizada correctamente",
          showConfirmButton: false,
          timer: 1500,
        });
        listarSuscripciones();
      }
    });
  });
}

const refreshMembresia = () => {
  $("#control-tab").on("click", function () {
    $("#selectMembresias").empty();
    cbxMembresias();
  })
}

const eliminarSuscripcion = () => {
  $(document).on("click", "#btn-eliminar-suscripcion", function () {
    let id = $(this).data("idsus");
    let suscripcion = $(this).data("suscripcion");
    Swal.fire({
      title: "¿Estas seguro de eliminar la suscripcion de " + suscripcion + "?",
      text: "No puedes recuperar una suscripcion eliminada",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Si, eliminar",
      cancelButtonText: "Cancelar",
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          type: "DELETE",
          url: backendSuscripcion + "/eliminar/" + id,
          dataType: "text",
          success: function (response) {
            Swal.fire({
              position: "center",
              icon: "success",
              title: "Suscripcion eliminada correctamente",
              showConfirmButton: false,
              timer: 1500,
            });
            listarSuscripciones();
          },
          error: function (error) {
            console.log(error);
          },
        });
      }
    });
  });
}

/* const imprimirComprobante = () => {
  $(document).on("click", "#btn-imprimir", function () {
    let id = $(this).data("idsus");
    //llamar a la api de mostrar el pdf
    $.ajax({
      type: "GET",
      url: backendSuscripcion + "/boucher?idSus=" + id,
      dataType: "json",
      success: function (response) {
        //mostrar el pdf
        window.open(response.url);
      }
    });
  })
} */

$(document).ready(function () {
  listarSuscripciones();
  editarSuscripcion();
  cbxMembresias();
  actualizarSuscripcion();
  eliminarSuscripcion();
});
