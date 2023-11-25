const backend = "http://localhost:8080/api/v1/equipamientos";
const formEquipamiento = $("#formEquipamiento")[0];
const tablaEquipamiento = $("#tablaEquipamiento").DataTable({
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

const listarEquipamiento = () => {
    tablaEquipamiento.clear();
    $.ajax({
        type: "GET",
        url: backend + "/lista",
        dataType: "json",
        success: function (response) {
            $.each(response, function (i, value) { 
                tablaEquipamiento.row
                .add([
                    value.idEquipamiento,
                    value.nombreEquipamiento,
                    value.stockEquipamiento,
                    `
                    <button type="button" data-id="${value.idEquipamiento}" class="btn btn-warning" id="btnEditar"><i class="fas fa-edit"></i></button>
                    <button type="button" data-id="${value.idEquipamiento}" class="btn btn-danger" id="btnEliminar"><i class="fas fa-trash-alt"></i></button>
                    `
                ]).draw(false);
            });
        }
    });
}

/*------------METODOS CRUD------------*/
const guardarEquipamiento = () =>{
    //Abrir el modal
    $("#btnAgregarEquipo").on("click", function(){
      //limpiarFormulario();
      formEquipamiento.reset();
      $("#color-modal").removeClass("bg-success");
      $("#color-modal").addClass("bg-primary");
      $("#titulo-form").html("Guardar Equipamiento");
      $("#btn-editar-form").hide();
      $("#btn-guardar").show();
      $("#modalGuardarEquipo").modal("show");
    })
  
    //Presiona el boton guardar
    $("#btn-guardar").on("click", function(){
      const datosEquipamiento = {
        nombreEquipamiento : formEquipamiento.nom_equi.value,
        stockEquipamiento : formEquipamiento.stock_equi.value,
      }
      $.ajax({
        url: backend + '/guardar',
        contentType: 'application/json',
        type: 'POST',
        data:JSON.stringify(datosEquipamiento),
        dataType: 'json',
        success: function(data){
            listarEquipamiento();
            alertas("El equipo se guardo correctamente", "success");
            $("#modalGuardarEquipo").modal("hide");
        }
      })
    })
  }

  const rellenarEquipamiento = () => {
    $(document).on("click", "#btnEditar", function () {
      $("#titulo-form").html("Editar Equipamiento");
      $("#color-modal").removeClass("bg-primary");
      $("#color-modal").addClass("bg-success");
      $("#btn-editar-form").show();
      $("#btn-guardar").hide();
      $("#modalGuardarEquipo").modal("show");
      let idEquipamiento = $(this).data("id");
      $.ajax({
        type: "GET",
        url: backend + "/buscar/" + idEquipamiento,
        dataType: "json",
        success: function (response) {
            formEquipamiento.id_equi.value = response.idEquipamiento;
            formEquipamiento.nom_equi.value = response.nombreEquipamiento;
            formEquipamiento.stock_equi.value = response.stockEquipamiento;
        }
      });
    });
  }

  const actualizarEquipamiento = () => {
    $("#btn-editar-form").on("click", function () {
      const datosEquipamiento = {
        idEquipamiento: formEquipamiento.id_equi.value,
        nombreEquipamiento: formEquipamiento.nom_equi.value,
        stockEquipamiento: formEquipamiento.stock_equi.value,
      };
      $.ajax({
        type: "PUT",
        url: backend + "/actualizar/" + datosEquipamiento.idEquipamiento,
        contentType: "application/json",
        data: JSON.stringify(datosEquipamiento),
        dataType: "json",
        success: function (response) {
            listarEquipamiento();
            alertas("El equipo se actualizo correctamente", "success");
            $("#modalGuardarEquipo").modal("hide");
        },
      });
    });
  }

  const eliminarEquipamiento = () => {
    $(document).on("click", "#btnEliminar", function () {
      let idEquipamiento = $(this).data("id");
      Swal.fire({
        title: "¿Estás seguro que deseas eliminar el equipo?",
        text: "No puedes recuperar la información luego de eliminarla",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Si, eliminar",
        cancelButtonText: "Cancelar",
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
            type: "DELETE",
            url: backend + "/eliminar/" + idEquipamiento,
            dataType: "text",
            success: function (response) {
                listarEquipamiento();
                alertas("El equipo se elimino correctamente", "success");
            },
          });
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
    listarEquipamiento();
    guardarEquipamiento();
    rellenarEquipamiento();
    actualizarEquipamiento();
    eliminarEquipamiento();
  });