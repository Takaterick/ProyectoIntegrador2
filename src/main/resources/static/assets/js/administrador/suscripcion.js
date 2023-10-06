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
      width: "20%", targets: 4 },
    { 
      className: "text-center",
      width: "10%", targets: 5 },
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
            value.estado,
            `
                    <button type="button" data-idsus="${value.id}" data-suscripcion="${value.cliente.nom_cli}" id="btn-editar-suscripcion" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                    <button type="button" data-idsus="${value.id}" data-suscripcion="${value.cliente.nom_cli}" id="btn-eliminar-suscripcion" class="btn btn-danger"><i class="fa fa-trash"></i></button>
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
    let id = $(this).data("idsus");
    $.ajax({
      type: "GET",
      url: backendSuscripcion + "/buscar/" + id,
      dataType: "json",
      success: function (response) {
        console.log(response.id);
        formSuscripcion.idSuscripcion.value = response.id;
        formSuscripcion.cliente.value = response.cliente.nom_cli + " " + response.cliente.ape_cli;
        formSuscripcion.idMembresia.value = response.membresia.id_sus;
        formSuscripcion.estado.value = response.estado; 
      },
    });
    $("#modalEditarSuscripcion").modal("show");
  });
};

const actualizarSuscripcion = () => {
  $("#btn-editar-form-sus").on("click", function () {
    let datos = {
      id: formSuscripcion.idSuscripcion.value,
      idMembresia: formSuscripcion.idMembresia.value,
      fechaInicio: moment(),
      fechaFin: moment().add(formSuscripcion.meses.value, "months"),
    };
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

$(document).ready(function () {
  listarSuscripciones();
  editarSuscripcion();
  cbxMembresias();
  actualizarSuscripcion();
  eliminarSuscripcion();
});
