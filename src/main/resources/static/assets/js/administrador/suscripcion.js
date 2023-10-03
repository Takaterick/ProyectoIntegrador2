const backendSuscripcion = "http://localhost:8080/api/v1/suscripciones";
const formSuscripcion = $("#formSuscripcion")[0];
const selectMembresias = $("#selectMembresias");
const selectEstados = $("#selectEstado");
const tablaSuscripciones = $("#tablaSuscripciones").DataTable({
  language: {
    url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
  },
});

/*------------METODOS CRUD------------*/
const listarSuscripciones = () => {
  tablaSuscripciones.clear();
  $.ajax({
    type: "GET",
    url: backendSuscripcion + "/lista",
    dataType: "json",
    success: function (response) {
      $.each(response, function (i, value) {
        tablaSuscripciones.row
          .add([
            value.id,
            value.cliente.nom_cli + " " + value.cliente.ape_cli,
            value.membresia.nom_sus,
            //new Date(value.fechaInicio).toLocaleDateString("es-ES", { year: 'numeric', month: '2-digit', day: '2-digit'}) + " - " + new Date(value.fechaFin).toLocaleDateString(),
            //formato con moment
            moment(value.fechaInicio).format("DD/MM/YYYY") +
              " - " +
              moment(value.fechaFin).format("DD/MM/YYYY"),
            value.estado,
            `
                    <button type="button" data-idsus="${value.id}" data-suscripcion="${value.cliente.usuario.usuario}" id="btn-editar-suscripcion" class="btn btn-warning"><i class="fa fa-edit"></i></button>
                    <button type="button" data-idsus="${value.id}" data-suscripcion="${value.cliente.usuario.usuario}" id="btn-eliminar-suscripcion" class="btn btn-danger"><i class="fa fa-trash"></i></button>
                    `,
          ])
          .draw();
      });
    },
  });
};

//LISTAR ROLES
const cbxMembresias = () => {
  $.ajax({
    url: backend + "/lista",
    type: "GET",
    dataType: "json",
    success: function (response) {
      response.forEach((membresia) => {
        selectMembresias.append(
          `<option value="${membresia.id_sus}">${membresia.nom_sus}</option>`
        );
      });
    },
  });
};

const editarSuscripcion = () => {
  $(document).on("click", "#btn-editar-suscripcion", function () {
    let id = $(this).data("idsus");
    $.ajax({
      type: "GET",
      url: backendSuscripcion + "/buscar/" + id,
      dataType: "json",
      success: function (response) {
        formSuscripcion.cliente.value = response.cliente.nom_cli + " " + response.cliente.ape_cli;
        formSuscripcion.idMembresia.value = response.membresia.id_sus;
        //para el estado
        
      },
    });
    /* const id = $(this).data("id");
        const suscripcion = $(this).data("suscripcion");
        $("#titulo-form").html("Editar Suscripcion");
        $("#btn-guardar").hide();
        $("#btn-editar-form").show(); */
    $("#modalEditarSuscripcion").modal("show");
    /* $("#id").val(id);
        $("#suscripcion").val(suscripcion); */
  });
};

$(document).ready(function () {
  listarSuscripciones();
  editarSuscripcion();
  cbxMembresias();
});
