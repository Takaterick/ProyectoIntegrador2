const backend = "http://localhost:8080/api/v1/roles";
const tablaRoles = $("#tablaRoles");

/*------------METODOS CRUD------------*/
const listarRoles = () => {
  tablaRoles.DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
    },
    ajax: {
      url: backend + "/lista",
      type: "GET",
      dataType: "json",
      dataSrc: function (response) {
        return response;
      }
    },
    columns: [
      { data: "id_rol" },
      { data: "nom_rol" },
      {
        render: function (data, type, row) {
          return `
                <button type="button" data-id="${row.id_rol}" data-rol="${row.nom_rol}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.id_rol}" data-rol="${row.nom_rol}" id="btn-eliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
                `;
        },
      },
    ],
  });
};

const guardarRol = () => {
    $("#btn-abrirModalRol").on("click", function () {
        $("#modalRol").modal("show");
    });
    $("#btn-guardarRol").on("click", function () {
        let datosForm = new FormData(formRol);
        
        const datosRol = {
            nom_rol: datosForm.get("nom_rol")
        }

        $.ajax({
            type: "POST",
            url: backend + "/guardar",
            contentType: "application/json",
            data: JSON.stringify(datosRol),
            dataType: "json",
            success: function (response) {
                tablaRoles.DataTable().ajax.reload();
                $("#modalRol").modal("hide");
                limpiarFormulario();
            }
        });
    });
}

const eliminarRol = () =>{
    tablaRoles.on("click", "#btn-eliminar", function () {
        let idRol = $(this).data("id");
        $.ajax({
            type: "DELETE",
            url: backend + "/eliminar/" + idRol,
            success: function (response) {
                tablaRoles.DataTable().ajax.reload();
            }
        });
    });
}



const limpiarFormulario = () => {
    $("#id_rol").val("");
    $("#nom_rol").val("");
}

$(document).ready(function () {
  listarRoles();
  guardarRol();
  eliminarRol();
});
