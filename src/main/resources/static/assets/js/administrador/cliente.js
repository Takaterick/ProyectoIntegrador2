const backend = "http://localhost:8080/api/v1/clientes";
const tablaClientes = $("#tablaClientes");
const formCliente = $("#formCliente")[0];

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
      {
        data: "nom_cli",
        render: function (data, type, row, meta) {
          return row.nom_cli + " " + row.ape_cli;
        },
      },
      { data: "usuario.usuario" },
      { data: "dni_cli" },
      { data: "tel_cli" },
      { data: "dir_cli" },
      { data: "createdBy",
        render: function (data, type, row) {
            if (data == null) {
                return "";
            } else {
            return data;
            }
        }
      },
      {
          data: "createdDate",
          render: function (data, type, row) {
              if (data == null) {
                  return "";
              } else {
              return moment(data).format('DD/MM/YYYY HH:mm:ss');
              }
          }
      },
      { data: "lastModifiedBy" },
      {
          data: "lastModifiedDate",
          render: function (data, type, row) {
              if (data == null) {
                  return "";
              } else {
                  return moment(data).format('DD/MM/YYYY HH:mm:ss');
              }
          }
      },
      {
        render: function (data, type, row) {
          return `
                <button type="button" data-id="${row.id_cli}" data-nombre="${row.nom_cli}" data-apellido="${row.ape_cli}" id="btnEditar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.id_cli}" data-nombre="${row.nom_cli}" data-apellido="${row.ape_cli}" id="btnEliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
          `;
        },
      },
    ],
    //redimensionar columnas
    columnDefs: [
      {
        targets: [0],
        width: "5%",
      },
      {
        targets: [1],
        width: "15%",
      },
      {
        targets: [5],
        width: "20%",
      }
    ],
    dom: "Bflrtip",
    buttons: [
      {
        extend: "excelHtml5",
        text: '<i class="fas fa-file-excel"></i> Exportar a CSV',
        titleAttr: "Exportar a Excel",
        className: "btn btn-success",
        filename: "Reporte_clientes",
      }
    ],
  });
}

//METODO GUARDAR CLIENTES
const guardarCliente = () => {
  $("#btn-abrirModalCliente").on("click", function () {
    limpiarFormulario();
    $("#titulo-form").html("Nuevo cliente");
    $("#color-modal").removeClass("bg-success");
    $("#color-modal").addClass("bg-primary");
    $("#btn-actualizarCliente").hide();
    $("#btn-guardarCliente").show();
    $("#modalCliente").modal("show");
  });
  $("#btn-guardarCliente").on("click", function () {
    let datosForm = new FormData(formCliente);
    let datosCliente = {
      nom_cli: datosForm.get("nom_cli"),
      ape_cli: datosForm.get("ape_cli"),
      dni_cli: datosForm.get("dni_cli"),
      tel_cli: datosForm.get("tel_cli"),
      correo_cli: datosForm.get("correo_cli"),
      dir_cli: datosForm.get("dir_cli"),
      usuario: {
        usuario: datosForm.get("usuario"),
        contrasenia: datosForm.get("contrasenia"),
      },
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
          formCliente.reset();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
};

//EDITAR CLIENTE
const rellenarCliente = () => {
  $(document).on("click", "#btnEditar", function () {
    $("#titulo-form").html("Editar cliente");
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
        console.log(response);
        formCliente.id_cli.value = response.id_cli;
        formCliente.nom_cli.value = response.nom_cli;
        formCliente.ape_cli.value = response.ape_cli;
        formCliente.dni_cli.value = response.dni_cli;
        formCliente.tel_cli.value = response.tel_cli;
        formCliente.correo_cli.value = response.correo_cli;
        formCliente.dir_cli.value = response.dir_cli;
        formCliente.usuario.value = response.usuario.usuario;
        formCliente.contrasenia.value = response.usuario.contrasenia;
        formCliente.idUsuario.value = response.usuario.id;
      },
    });
  });
};

//METODO ACTUALIZAR
const actualizarCliente = () => {
  $("#btn-actualizarCliente").on("click", function () {
    let datosForm = new FormData(formCliente);
    let datosCliente = {
      id_cli: datosForm.get("id_cli"),
      nom_cli: datosForm.get("nom_cli"),
      ape_cli: datosForm.get("ape_cli"),
      dni_cli: datosForm.get("dni_cli"),
      tel_cli: datosForm.get("tel_cli"),
      correo_cli: datosForm.get("correo_cli"),
      dir_cli: datosForm.get("dir_cli"),
      usuario: {
        id: datosForm.get("idUsuario"),
        usuario: datosForm.get("usuario"),
        contrasenia: datosForm.get("contrasenia"),
      },
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
          formCliente.reset();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      }
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
      cancelButtonText: "Cancelar",
      confirmButtonText: "Si, eliminar!",
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          type: "DELETE",
          url: backend + "/eliminar/" + idCliente,
          dataType: "text",
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

//METODO LIMPIAR
const limpiarFormulario = () => {
  $("#id_cliente").val("");
  $("#nombre_cliente").val("");
  $("#apellido_cliente").val("");
  $("#usuario_cliente").val("");
  $("#contrasenia_cliente").val("");
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
    showConfirmButton: false,
    timer: 2000,
  });
};

$(document).ready(function () {
  listarClientes();
  guardarCliente();
  eliminarCliente();
  rellenarCliente();
  actualizarCliente();
});