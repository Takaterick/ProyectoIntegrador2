const backend = "http://localhost:8080/api/v1/talleres";
const tablaTalleres = $("#tablaTalleres");

/*------------METODOS CRUD------------*/
//METODO LISTAR TALLERES
const listarTalleres = () => {
  tablaTalleres.DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
    },
    ajax: {
      url: backend + "/lista",
      type: "GET",
      dataType: "json",
      dataSrc: function (response) {
        return response;
      },
    },
    columns: [
      { data: "id_taller" },
      { data: "nom_taller" },
      {
        render: function (data, type, row) {
          return `
                <button type="button" data-id="${row.id_taller}" data-nombre="${row.nom_taller}" id="btnEditar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.id_taller}" data-nombre="${row.nom_taller}" id="btnEliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
          `;
        },
      },
    ],
  });
};

//METODO GUARDAR TALLER
const guardarTaller = () => {
  $("#btn-abrirModalTaller").on("click", function () {
    limpiarFormulario();
    $("#titulo-form").html("Guardar Taller");
    $("#color-modal").removeClass("bg-success");
    $("#color-modal").addClass("bg-primary");
    $("#btn-actualizarTaller").hide();
    $("#btn-guardarTaller").show();
    $("#modalTaller").modal("show");
  });
  $("#btn-guardarTaller").on("click", function () {
    let datosForm = new FormData(formTaller);
    const datosTaller = {
      nom_taller: datosForm.get("nom_taller"),
    };
    $.ajax({
      type: "POST",
      url: backend + "/guardar",
      contentType: "application/json",
      data: JSON.stringify(datosTaller),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          alertas(response.mensaje, response.tipo);
          tablaTalleres.DataTable().ajax.reload();
          $("#modalTaller").modal("hide");
          limpiarFormulario();
        }else{
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
};

//ELIMINAR TALLER
const eliminarTaller = () => {
  $(document).on("click", "#btnEliminar", function () {
    let idTaller = $(this).data("id");
    let nombreTaller = $(this).data("nombre");

    Swal.fire({
      title: "¿Esta seguro de eliminar el taller  " + nombreTaller + "?",
      text: "¡Una vez eliminado no se puede recuperar!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Si, eliminar!",
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          type: "DELETE",
          url: backend + "/eliminar/" + idTaller,
          success: function (response) {
            tablaTalleres.DataTable().ajax.reload();
            alertas(
              "¡El taller " + nombreTaller + " se ha eliminado exitosamente!",
              "success"
            );
          },
        });
      } else {
        alertas("El taller " + nombreTaller + " no se ha eliminado", "error");
      }
    });
  });
};

//EDITAR TALLER
const rellenarTaller = () => {
  $(document).on("click", "#btnEditar", function () {
    $("#titulo-form").html("Editar Taller");
    $("#color-modal").removeClass("bg-primary");
    $("#color-modal").addClass("bg-success");
    $("#btn-actualizarTaller").show();
    $("#btn-guardarTaller").hide();
    $("#modalTaller").modal("show");
    let id = $(this).data("id");
    $.ajax({
      type: "GET",
      url: backend + "/buscar/" + id,
      dataType: "json",
      success: function (response) {
        $("#id_taller").val(response.id_taller);
        $("#nombre_taller").val(response.nom_taller);
      },
    });
  });
};

//METODO ACTUALIZAR
const actualizarTaller = () => {
  $("#btn-actualizarTaller").on("click", function () {
    let datosForm = new FormData(formTaller);
    const datosTaller = {
      id_taller: datosForm.get("id_taller"),
      nom_taller: datosForm.get("nom_taller"),
    };
    $.ajax({
      type: "PUT",
      url: backend + "/actualizar/" + datosTaller.id_taller,
      contentType: "application/json",
      data: JSON.stringify(datosTaller),
      dataType: "json",
      success: function (response) {
        if(response.tipo == "success"){
          tablaTalleres.DataTable().ajax.reload();
          alertas(response.mensaje, response.tipo);
          $("#modalTaller").modal("hide");
          limpiarFormulario();
        }else{
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
};

//METODO LIMPIAR
const limpiarFormulario = () => {
  $("#id_taller").val("");
  $("#nombre_taller").val("");
};

//METODO ALERTAS
const alertas = (mensaje, icono) => {
  Swal.fire({
    title: mensaje,
    icon: icono,
    customClass: { confirmButton: "btn btn-primary", popup: "animated xoomIn" },
    buttonsStyling: false,
  });
};

$(document).ready(function () {
  listarTalleres();
  guardarTaller();
  eliminarTaller();
  rellenarTaller();
  actualizarTaller();
});
