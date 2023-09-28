const backend = "http://localhost:8080/api/v1/";
const tablaEmpleado = $("#tablaEmpleado");
const formEmpleado = $("#formEmpleado")[0];
const selectRoles = $("#selectRoles");

/*------------METODOS CRUD------------*/
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
      { data: "usuario.usuario" },
      { data: "rol.nom_rol" },
      { data: "dniEmpl" },
      { data: "telefonoEmpl" },
      { data: "correoEmpl" },
      {
        render: function (data, type, row) {
          return `
                <button type="button" data-id="${row.idEmpl}" data-nombre="${row.nombreEmpl}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.idEmpl}" data-nombre="${row.nombreEmpl}" id="btn-eliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
                `;
        },
      },
    ],
  });
};

//LISTAR ROLES
const listarRoles = () => {
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

const guardarEmpleado = () => {
  $("#btnAgregar").on("click", function () {
    $("#titulo-form").html("Nuevo Empleado");
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
        alertas("El empleado "+datosEmpleado.usuario.usuario+" se agregó correctamente", "success");
        tablaEmpleado.DataTable().ajax.reload();
        $("#modalEmpleado").modal("hide");
        formEmpleado.reset();
      }
    });
  });
};

const rellenarEmpleado = () => {
  $(document).on("click", "#btn-editar", function () {
    //$("#titulo-form").html("Actualizar Empleado");
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
        alertas("El empleado "+datosEmpleado.usuario.usuario+" se actualizó correctamente", "success");
        tablaEmpleado.DataTable().ajax.reload();
        $("#modalEmpleado").modal("hide");
        formEmpleado.reset();
      }
    });
  });
}

const eliminarEmpleado = () =>{
  $(document).on("click", "#btn-eliminar", function () {
    let id = $(this).data("id");
    let nombreEmpleado = $(this).data("nombre");
    Swal.fire({
      title: "¿Estás seguro de eliminar a " + nombreEmpleado + "?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
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
            alertas(
              "¡El empleado " + nombreEmpleado + " se ha eliminado exitosamente!",
              "success"
            );
          },
        });
      } else {
        alertas("El empleado " + nombreEmpleado + " no se ha eliminado", "error");
      }
    });
  });
}
const alertas = (mensaje, icono) => {
  Swal.fire({
    title: mensaje,
    icon: icono,
    showConfirmButton: false,
    timer: 1000,
  });
};

$(document).ready(function () {
  listarEmpleados();
  listarRoles();
  guardarEmpleado();
  eliminarEmpleado();
  rellenarEmpleado();
  actualizarEmpleado();
});
