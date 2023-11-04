const backend = "http://localhost:8080/api/v1/";
const tablaEmpleado = $("#tablaEmpleado");
const tablaUsuario = $("#tablaUsuario");
const tablaRolEmpleado = $("#tablaRolEmpleado");
const formEmpleado = $("#formEmpleado")[0];
const selectRoles = $("#selectRoles");

/*------------METODOS CRUD------------*/
//METODO LISTAR EMPLEADOS
const listarEmpleados = () => {
  tablaEmpleado.DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
    },
    ajax: {
      url: backend + "empleados/lista",
      type: "GET",
      dataType: "json",
      dataSrc: function (response) {
        return response;
      },
    },
    columns: [
      { data: "idEmpl" },
      {
        data: "nombreEmpl",
        render: function (data, type, row, meta) {
          return row.nombreEmpl + " " + row.apellidoEmpl;
        },
      },
      { data: "telefonoEmpl" },
      { data: "correoEmpl" },
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
                <button type="button" data-id="${row.idEmpl}" data-nombre="${row.nombreEmpl}" data-apellido="${row.apellidoEmpl}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.idEmpl}" data-nombre="${row.nombreEmpl}" data-apellido="${row.apellidoEmpl}" id="btn-eliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
              `;
        },
      },
    ],
    //centrar columnas
    columnDefs: [
      { className: "dt-center", targets: "_all" },
      { orderable: false, targets: [8] },
    ],
  });
};

const listarUsuarios = () => {
  $("#usuario-tab").on("click", function () {
    tablaUsuario.DataTable().destroy();
    tablaUsuario.DataTable({
      language: {
        url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
      },
      ajax: {
        url: backend + "empleados/lista",
        type: "GET",
        dataType: "json",
        dataSrc: function (response) {
          return response;
        },
      },
      columns: [
        { data: "idEmpl" },
        {
          data: "nombreEmpl",
          render: function (data, type, row, meta) {
            return row.nombreEmpl + " " + row.apellidoEmpl;
          },
        },
        { data: "usuario.usuario" },
        {
          //poner bloqueado y no bloquedao segun el estado
          data: "usuario.bloqueo",
          render: function (data, type, row, meta) {
            if (row.usuario.bloqueo == 0) {
              return `<span class="badge bg-success">Activo</span>`;
            } else {
              return `<span class="badge bg-danger">Bloqueado</span>`;
            }
          },
        },
        { data: "usuario.desc_bloq" },
        {
          render: function (data, type, row) {
            return `
                  <button type="button" data-id="${row.idEmpl}" data-nombre="${row.nombreEmpl}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                  `;
          },
        },
      ],
    });
  })
};

const listarRolEmpleado = () => {
  $("#rol-tab").on("click", function () {
    tablaRolEmpleado.DataTable().destroy();
    tablaRolEmpleado.DataTable({
      language: {
        url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
      },
      ajax: {
        url: backend + "empleados/lista",
        type: "GET",
        dataType: "json",
        dataSrc: function (response) {
          return response;
        },
      },
      columns: [
        { data: "idEmpl" },
        {
          data: "nombreEmpl",
          render: function (data, type, row, meta) {
            return row.nombreEmpl + " " + row.apellidoEmpl;
          },
        },
        { data: "rol.nom_rol" },
        {
          render: function (data, type, row) {
            return `
                  <button type="button" data-id="${row.idEmpl}" data-nombre="${row.nombreEmpl}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                  `;
          },
        },
      ],
    });
  })
};

//LISTAR ROLES
const listarRoles = () => {
  selectRoles.append(`<option value="">-- Seleccionar --</option>`);
  $.ajax({
    url: backend + "roles/lista",
    type: "GET",
    dataType: "json",
    success: function (response) {
      response.forEach((rol) => {
        selectRoles.append(
          `<option value="${rol.id_rol}">${rol.nom_rol}</option>`
        );
      });
    }
  });
};

//METODO GUARDAR EMPLEADOS
const guardarEmpleado = () => {
  $("#btnAgregar").on("click", function () {
    limpiarFormulario();
    $("#titulo-form").html("Nuevo empleado");
    $("#color-modal").removeClass("bg-success");
    $("#color-modal").addClass("bg-primary");
    $("#btnActualizar").hide();
    $("#btnGuardar").show();
    $("#modalEmpleado").modal("show");
  });
  $("#btnGuardar").on("click", function () {
    let datosForm = new FormData(formEmpleado);
    let datosEmpleado = {
      nombreEmpl: datosForm.get("nombreEmpl"),
      apellidoEmpl: datosForm.get("apellidoEmpl"),
      dniEmpl: datosForm.get("dniEmpl"),
      telefonoEmpl: datosForm.get("telefonoEmpl"),
      correoEmpl: datosForm.get("correoEmpl"),
      direccionEmpl: datosForm.get("direccionEmpl"),
      usuario: {
        usuario: datosForm.get("usuario"),
        contrasenia: datosForm.get("contrasenia"),
      },
      rol: {
        id_rol: datosForm.get("idRol"),
      },
    };
    $.ajax({
      type: "POST",
      url: backend + "empleados/guardar",
      contentType: "application/json",
      data: JSON.stringify(datosEmpleado),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          alertas(response.mensaje, response.tipo);
          tablaEmpleado.DataTable().ajax.reload();
          $("#modalEmpleado").modal("hide");
          formEmpleado.reset();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
};

//EDITAR EMPLEADO
const rellenarEmpleado = () => {
  $(document).on("click", "#btn-editar", function () {
    $("#titulo-form").html("Editar empleado");
    $("#color-modal").removeClass("bg-primary");
    $("#color-modal").addClass("bg-success");
    $("#btnActualizar").show();
    $("#btnGuardar").hide();
    $("#modalEmpleado").modal("show");
    let id = $(this).data("id");
    $.ajax({
      type: "GET",
      url: backend + "empleados/buscar/" + id,
      dataType: "json",
      success: function (response) {
        console.log(response);
        formEmpleado.idEmpl.value = response.idEmpl;
        formEmpleado.nombreEmpl.value = response.nombreEmpl;
        formEmpleado.apellidoEmpl.value = response.apellidoEmpl;
        formEmpleado.dniEmpl.value = response.dniEmpl;
        formEmpleado.telefonoEmpl.value = response.telefonoEmpl;
        formEmpleado.correoEmpl.value = response.correoEmpl;
        formEmpleado.direccionEmpl.value = response.direccionEmpl;
        formEmpleado.usuario.value = response.usuario.usuario;
        formEmpleado.contrasenia.value = response.usuario.contrasenia;
        formEmpleado.idRol.value = response.rol.id_rol;
        formEmpleado.idUsuario.value = response.usuario.id;
      },
    });
  });
}

//METODO ACTUALIZAR
const actualizarEmpleado = () => {
  $("#btnActualizar").on("click", function () {
    let datosForm = new FormData(formEmpleado);
    let datosEmpleado = {
      idEmpl: datosForm.get("idEmpl"),
      nombreEmpl: datosForm.get("nombreEmpl"),
      apellidoEmpl: datosForm.get("apellidoEmpl"),
      dniEmpl: datosForm.get("dniEmpl"),
      telefonoEmpl: datosForm.get("telefonoEmpl"),
      correoEmpl: datosForm.get("correoEmpl"),
      direccionEmpl: datosForm.get("direccionEmpl"),
      usuario: {
        id: datosForm.get("idUsuario"),
        usuario: datosForm.get("usuario"),
        contrasenia: datosForm.get("contrasenia"),
      },
      rol: {
        id_rol: datosForm.get("idRol"),
      },
    };
    $.ajax({
      type: "PUT",
      url: backend + "empleados/actualizar/" + datosEmpleado.idEmpl,
      contentType: "application/json",
      data: JSON.stringify(datosEmpleado),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          tablaEmpleado.DataTable().ajax.reload();
          alertas(response.mensaje, response.tipo);
          $("#modalEmpleado").modal("hide");
          formEmpleado.reset();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      }
    });
  });
};

//ELIMINAR CLIENTE
const eliminarEmpleado = () =>{
  $(document).on("click", "#btn-eliminar", function () {
    let id = $(this).data("id");
    let nombreEmpleado = $(this).data("nombre");
    let apellidoEmpleado = $(this).data("apellido");

    Swal.fire({
      title: "¿Esta seguro de eliminar a " + nombreEmpleado + " " + apellidoEmpleado + "?",
      text: "¡Una vez eliminado no se puede recuperar!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      cancelButtonText: "Cancelar",
      confirmButtonText: "Si, eliminar",
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          type: "DELETE",
          url: backend + "empleados/eliminar/" + id,
          dataType: "text",
          success: function (response) {
            tablaEmpleado.DataTable().ajax.reload();
            alertas("¡Se ha eliminado exitosamente a " + nombreEmpleado + " " + apellidoEmpleado + "!", "success");
          },
        });
      } else {
        alertas("No se ha eliminado a " + nombreEmpleado + " " + apellidoEmpleado, "error");
      }
    });
  });
}

//METODO LIMPIAR
const limpiarFormulario = () => {
  $("#id_empleado").val("");
  $("#nombre_empleado").val("");
  $("#apellido_empleado").val("");
  $("#usuario_empleado").val("");
  $("#contrasenia_empleado").val("");
  $("#dni_empleado").val("");
  $("#telefono_empleado").val("");
  $("#direccion_empleado").val("");
  $("#correo_empleado").val("");
  $("#selectRoles").val("");
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
  listarEmpleados();
  listarUsuarios();
  listarRolEmpleado();
  listarRoles();
  guardarEmpleado();
  eliminarEmpleado();
  rellenarEmpleado();
  actualizarEmpleado();
});
