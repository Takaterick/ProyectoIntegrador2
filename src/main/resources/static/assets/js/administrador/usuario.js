const backendUsuario = "http://localhost:8080/api/v1/usuarios";
const tablaUsuario = $("#tablaUsuario");
const formUsuario = $("#formUsuario")[0];

/*------------METODOS CRUD------------*/
const listarUsuarios = () => {
  tablaUsuario.DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
    },
    ajax: {
      url: backendUsuario + "/lista",
      type: "GET",
      dataType: "json",
      dataSrc: function (response) {
        return response;
      },
    },
    columns: [
      { data: "id" },
      { data: "usuario" },
      { data: "rol" },
      {
        render: function (data, type, row) {
          return `
                <button type="button" data-id="${row.id}" data-nombre="${row.usuario}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                `;
        },
      },
    ],
  });
};

$(document).ready(function () {
    listarUsuarios();
})