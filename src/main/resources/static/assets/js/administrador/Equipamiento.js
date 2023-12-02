const backend = "https://luckygym.azurewebsites.net/api/v1/equipamientos";
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
  dom: "Bflrtip",
    buttons: [
      {
        extend: "excelHtml5",
        text: '<i class="fas fa-file-excel"></i> Exportar a CSV',
        titleAttr: "Exportar a Excel",
        className: "btn btn-success",
        filename: "Reporte_Equipamiento",
      }
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
  });