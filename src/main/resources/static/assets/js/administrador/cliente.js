const backend = "http://localhost:8080/api/v1/clientes";
const tablaClientes = $("#tablaClientes");

/*------------METODOS CRUD------------*/
//METODO LISTAR CLIENTES
const listarClientes = () => {
  tablaClientes.DataTable({
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
      { data: "id_cli" },
      { data: "nom_cli" },
      { data: "ape_cli" },
      { data: "dni_cli" },
      { data: "tel_cli" },
      { data: "correo_cli" },
      { data: "dir_cli" },
      {
        render: function (data, type, row) {
          return `
                <button type="button" data-id="${row.id_cli}" data-nombre="${row.nom_cli}" data-apellido="${row.ape_cli}" id="btnEditar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.id_cli}" data-nombre="${row.nom_cli}" data-apellido="${row.ape_cli}"id="btnEliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
          `;
        },
      },
    ],
  });
}

//METODO GUARDAR CLIENTES
const guardarCliente = () => {
  $("#btn-abrirModalCliente").on("click", function () {
    limpiarFormulario();
    $("#titulo-form").html("Guardar Nuevo cliente");
    $("#color-modal").removeClass("bg-success");
    $("#color-modal").addClass("bg-primary");
    $("#btn-actualizarCliente").hide();
    $("#btn-guardarCliente").show();
    $("#modalCliente").modal("show");
  });
  $("#btn-guardarCliente").on("click", function () {
    let datosForm = new FormData(formCliente);
    const datosCliente = {
      nom_cli: datosForm.get("nom_cli"),
      ape_cli: datosForm.get("ape_cli"),
      dni_cli: datosForm.get("dni_cli"),
      tel_cli: datosForm.get("tel_cli"),
      correo_cli: datosForm.get("correo_cli"),
      dir_cli: datosForm.get("dir_cli"),
    };
    $.ajax({
      type: "POST",
      url: backend + "/guardar",
      contentType: "application/json",
      data: JSON.stringify(datosCliente),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          alertas(response.mensaje, response.tipo);
          tablaClientes.DataTable().ajax.reload();
          $("#modalCliente").modal("hide");
          limpiarFormulario();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
};

//ELIMINAR CLIENTE
const eliminarCliente = () => {
  $(document).on("click", "#btnEliminar", function () {
    let idCliente = $(this).data("id");
    let nombreCliente = $(this).data("nombre");
    let apellidoCliente = $(this).data("apellido");

    Swal.fire({
      title: "¿Esta seguro de eliminar a " + nombreCliente + " " + apellidoCliente + "?",
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
          url: backend + "/eliminar/" + idCliente,
          success: function (response) {
            tablaClientes.DataTable().ajax.reload();
            alertas("¡Se ha eliminado exitosamente a " + nombreCliente + " " + apellidoCliente + "!", "success");
          },
        });
      } else {
        alertas("No se ha eliminado a " + nombreCliente + " " + apellidoCliente, "error");
      }
    });
  });
};

//EDITAR CLIENTE
const rellenarCliente = () => {
  $(document).on("click", "#btnEditar", function () {
    $("#titulo-form").html("Editar Cliente");
    $("#color-modal").removeClass("bg-primary");
    $("#color-modal").addClass("bg-success");
    $("#btn-actualizarCliente").show();
    $("#btn-guardarCliente").hide();
    $("#modalCliente").modal("show")
    let id = $(this).data("id");
    $.ajax({
      type: "GET",
      url: backend + "/buscar/" + id,
      dataType: "json",
      success: function (response) {
        $("#id_cliente").val(response.id_cli);
        $("#nombre_cliente").val(response.nom_cli);
        $("#apellido_cliente").val(response.ape_cli);
        $("#dni_cliente").val(response.dni_cli);
        $("#telefono_cliente").val(response.tel_cli);
        $("#correo_cliente").val(response.correo_cli);
        $("#direccion_cliente").val(response.dir_cli);
      },
    });
  });
};

//METODO ACTUALIZAR
const actualizarCliente = () => {
  $("#btn-actualizarCliente").on("click", function () {
    let datosForm = new FormData(formCliente);
    const datosCliente = {
      id_cli: datosForm.get("id_cli"),
      nom_cli: datosForm.get("nom_cli"),
      ape_cli: datosForm.get("ape_cli"),
      dni_cli: datosForm.get("dni_cli"),
      tel_cli: datosForm.get("tel_cli"),
      correo_cli: datosForm.get("correo_cli"),
      dir_cli: datosForm.get("dir_cli"),
    };
    $.ajax({
      type: "PUT",
      url: backend + "/actualizar/" + datosCliente.id_cli,
      contentType: "application/json",
      data: JSON.stringify(datosCliente),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          tablaClientes.DataTable().ajax.reload();
          alertas(response.mensaje, response.tipo);
          $("#modalCliente").modal("hide");
          limpiarFormulario();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      }
    });
  });
};

//METODO LIMPIAR
const limpiarFormulario = () => {
  $("#id_cliente").val("");
  $("#nombre_cliente").val("");
  $("#apellido_cliente").val("");
  $("#dni_cliente").val("");
  $("#telefono_cliente").val("");
  $("#correo_cliente").val("");
  $("#direccion_cliente").val("");
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
  listarClientes();
  guardarCliente();
  eliminarCliente();
  rellenarCliente();
  actualizarCliente();
});