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
                <button type="button" data-id="${row.id_empleado}" data-nombre="${row.nom_empleado}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.id_empleado}" data-nombre="${row.nom_empleado}" id="btn-eliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
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
        },
        error: function (error) {
            console.log(error);
        },
    });
};

const guardarEmpleado = () => {
    $("#btnAgregar").on("click", function () {
        $("#titulo-form").html("Nuevo Empleado");
        $("#modalEmpleado").modal("show");
    })
    $("#btn-btnGuardar").on("click", function () {
        let datosForm = new FormData(formEmpleado);
        let datosEmpleado = {
            nombreEmpl: datosForm.get("nombreEmpl"),
            apellidoEmpl: datosForm.get("apellidoEmpl"),
            dniEmpl: datosForm.get("dniEmpl"),
            telefonoEmpl: datosForm.get("telefonoEmpl"),
            correoEmpl: datosForm.get("correoEmpl"),
            usuario: {
                usuario: datosForm.get("usuario"),
                password: datosForm.get("password"),
                rol: {
                    id_rol: datosForm.get("rol")
                }
            }
        }
    })
    $.ajax({
      type: "POST",
      url: backend + "roles/guardar",
      contentType: "application/json",
      data: "data",
      dataType: "json",
      success: function (response) {
        
      }
    });
    $.ajax({
      type: "POST",
      url: backend + "empleados/guardar",
      contentType: "application/json",
      data: "data",
      dataType: "json",
      success: function (response) {
        
      }
    });
}

$(document).ready(function () {
  listarEmpleados();
  listarRoles();
  guardarEmpleado();
});
