const backend = "http://localhost:8080/api/v1/roles";
const tablaRoles = $("#tablaRoles");
const formRol = $("#formRol")[0];

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
      },
    },
    columns: [
      { data: "id_rol" },
      { data: "nom_rol" },
      /* agregar los campos de auditoria */
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
                <button type="button" data-id="${row.id_rol}" data-rol="${row.nom_rol}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                <button type="button" data-id="${row.id_rol}" data-rol="${row.nom_rol}" id="btn-eliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
                `;
        },
      },
    ],
    //cambiar tamaño de las columnas column defs
    columnDefs: [
      {
        width: "5%",
        targets: [0],
        className: "dt-center",
      },
      {
        width: "15%",
        targets: [1],
        className: "dt-center",
      },
      {
        width: "15%",
        targets: [2],
        className: "dt-center",
      },
      {
        width: "20%",
        targets: [3],
        className: "dt-center",
      },
      {
        width: "15%",
        targets: [4],
        className: "dt-center",
      },
      {
        width: "20%",
        targets: [5],
        className: "dt-center",
      },
      {
        width: "10%",
        targets: [6],
        className: "dt-center",
      },
    ],
  });
};

const guardarRol = () => {
  $("#btn-abrirModalRol").on("click", function () {
    formRol.reset();
    $("#titulo-form").html("Nuevo Rol");
    $("#color-modal").removeClass("bg-success");
    $("#btn-guardarRol").show();
    $("#btn-actualizarRol").hide();
    $("#modalRol").modal("show");
  });
  $("#btn-guardarRol").on("click", function () {
    const datosForm = new FormData(formRol);
    const datosRol = {
      nom_rol: datosForm.get("nom_rol"),
    };

    $.ajax({
      type: "POST",
      url: backend + "/guardar",
      contentType: "application/json",
      data: JSON.stringify(datosRol),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          alertas(response.mensaje, response.tipo);
          tablaRoles.DataTable().ajax.reload();
          $("#modalRol").modal("hide");
          formRol.reset();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
};

const rellenarRol = () => {
  $(document).on("click", "#btn-editar", function () {
    $("#titulo-form").html("Actualizar Rol");
    $("#color-modal").addClass("bg-success");
    $("#btn-guardarRol").hide();
    $("#btn-actualizarRol").show();
    $("#modalRol").modal("show");
    let idRol = $(this).data("id");
    $.ajax({
      type: "GET",
      url: backend + "/buscar/" + idRol,
      dataType: "json",
      success: function (response) {
        formRol.id_rol.value = response.id_rol;
        formRol.nom_rol.value = response.nom_rol;
      },
    });
  });
};

const actualizarRol = () => {
  $("#btn-actualizarRol").on("click", function () {
    const datosForm = new FormData(formRol);
    const datosRol = {
      id_rol: datosForm.get("id_rol"),
      nom_rol: datosForm.get("nom_rol"),
    };
    $.ajax({
      type: "PUT",
      url: backend + "/actualizar/" + datosRol.id_rol,
      contentType: "application/json",
      data: JSON.stringify(datosRol),
      dataType: "json",
      success: function (response) {
        if (response.tipo == "success") {
          alertas(response.mensaje, response.tipo);
          tablaRoles.DataTable().ajax.reload();
          $("#modalRol").modal("hide");
          formRol.reset();
        } else {
          alertas(response.mensaje, response.tipo);
        }
      },
    });
  });
};

const eliminarRol = () => {
  tablaRoles.on("click", "#btn-eliminar", function () {
    let idRol = $(this).data("id");
    let nombreRol = $(this).data("rol");
    Swal.fire({
      title: "¿Estas seguro de eliminar el rol " + nombreRol + "?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Si, eliminar!",
      cancelButtonText: "No, cancelar!",
      customClass: {
        confirmButton: "btn btn-primary",
        cancelButton: "btn btn-danger",
      },
      buttonsStyling: true,
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          type: "DELETE",
          url: backend + "/eliminar/" + idRol,
          success: function (response) {
            alertas(
              "El rol " + nombreRol + " se ha eliminado correctamente",
              "success"
            );
            tablaRoles.DataTable().ajax.reload();
          },
        });
      } else {
        alertas("El rol " + nombreRol + " no se ha eliminado", "error");
      }
    });
  });
};

const alertas = (mensaje, icono) => {
  Swal.fire({
    title: mensaje,
    icon: icono,
    showConfirmButton: false,
    timer: 1000,
  });
};

$(document).ready(function () {
  listarRoles();
  guardarRol();
  eliminarRol();
  rellenarRol();
  actualizarRol();
});
