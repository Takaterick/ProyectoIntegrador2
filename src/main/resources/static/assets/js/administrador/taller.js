const backend = "http://localhost:8080/api/v1/talleres";
const tablaTalleres = $("#tablaTalleres").DataTable({
  language: {
    url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
  },
  //centrar columnas
  columnDefs: [
    {
      className: "dt-center",
      targets: "_all",
    },
  ],
});

///*** otra forma de capturar los datos de los inputs */
const formulario = $("#formTaller")[0];
/* const valorTaller = {
  id_taller: "",
  nom_taller: "",
}
const actualizarValor = (e) => {
  valorTaller[e.name] = e.value;
}

const limpiarValores = () => {
  valorTaller.id_taller = "";
  valorTaller.nom_taller = "";
}

const guardar = () => {
  $("#btn-guardarTaller").click(function (e) { 
    $.ajax({
      type: "method",
      url: "url",
      data: "data",
      dataType: "dataType",
      success: function (response) {
        
      }
    });
  });
} */

/*------------METODOS CRUD------------*/
//METODO LISTAR TALLERES
const listarTalleres = () => {
  tablaTalleres.clear();
  $.ajax({
    type: "GET",
    url: backend + "/lista",
    dataType: "json",
    success: function (response) {
      $.each(response, function (i, value) {
        //limpiar tabla
        tablaTalleres.row
          .add([
            i + 1,
            value.nomTaller,
            /* agregar los campos de auditoria */
            value.createdBy,
            //validar si el campo es nulo
            value.createdDate == null
              ? ""
              : moment(value.createdDate).format("DD/MM/YYYY HH:mm:ss"),
            value.lastModifiedBy,
            //validar si el campo es nulo
            value.lastModifiedDate == null
              ? ""
              : moment(value.lastModifiedDate).format("DD/MM/YYYY HH:mm:ss"),
            `
          <button type="button" data-id="${value.id_taller}" data-nombre="${value.nomTaller}" id="btnEditar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
          <button type="button" data-id="${value.id_taller}" data-nombre="${value.nomTaller}" id="btnEliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
          `,
          ])
          .draw(false);
      });
    },
  });
};

//METODO GUARDAR TALLER
const guardarTaller = () => {
  $("#btn-abrirModalTaller").on("click", function () {
    formulario.reset();
    $("#titulo2-form").html("Guardar Taller");
    $("#color2-modal").removeClass("bg-success");
    $("#color2-modal").addClass("bg-primary");
    $("#btn-actualizarTaller").hide();
    $("#btn-guardarTaller").show();
    $("#modalTaller").modal("show");
  });

  $("#btn-guardarTaller").on("click", function () {
    const datosForm = new FormData(formulario);
    const datosTaller = {
      nomTaller: datosForm.get("nom_taller"),
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
          listarTalleres();
          listarCbxTalleres();
          $("#modalTaller").modal("hide");
          formulario.reset();
        } else {
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
            listarTalleres();
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
    $("#titulo2-form").html("Editar Taller");
    $("#color2-modal").removeClass("bg-primary");
    $("#color2-modal").addClass("bg-success");
    $("#btn-actualizarTaller").show();
    $("#btn-guardarTaller").hide();
    $("#modalTaller").modal("show");
    let id = $(this).data("id");
    $.ajax({
      type: "GET",
      url: backend + "/buscar/" + id,
      dataType: "json",
      success: function (response) {
        formulario.id_taller.value = response.id_taller;
        formulario.nom_taller.value = response.nomTaller;
      },
    });
  });
};

//METODO ACTUALIZAR
const actualizarTaller = () => {
  $("#btn-actualizarTaller").on("click", function () {
    const datosForm = new FormData(formulario);
    const datosTaller = {
      id_taller: datosForm.get("id_taller"),
      nomTaller: datosForm.get("nom_taller"),
    };
    $.ajax({
      type: "PUT",
      url: backend + "/actualizar/" + datosTaller.id_taller,
      contentType: "application/json",
      data: JSON.stringify(datosTaller),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          listarTalleres();
          alertas(response.mensaje, response.tipo);
          $("#modalTaller").modal("hide");
        } else {
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
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
