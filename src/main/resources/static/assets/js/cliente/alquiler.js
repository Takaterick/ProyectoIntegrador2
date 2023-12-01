const backend = "http://localhost:8080/api/v1/";
const formAlquiler = $("#formAlquiler")[0];
const buscadorCliente = $("#buscador_cliente");
const selectEquipo = $("#selectEquipamiento");
const selectStock = $("#select_Stock");
const tablaAlquileres = $("#tablaAlquiler").DataTable({
  language: {
    url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
  },
  columnDefs: [
    { width: "5%", targets: 0 },
    { className: "text-center", width: "15%", targets: 1 },
    { className: "text-center", width: "20%", targets: 2 },
    { className: "text-center", width: "20%", targets: 3 },
    { className: "text-center", width: "10%", targets: 4 },
    { className: "text-center", width: "5%", targets: 5 },
    { className: "text-center", width: "5%", targets: 6 },
    { className: "text-center", width: "20%", targets: 7 },
  ],
});

const listarAlquileres = () => {
  tablaAlquileres.clear();
  $.ajax({
    type: "GET",
    url: backend + "alquileres/buscar/cliente/" + clienteId,
    dataType: "json",
    success: function (response) {
      response.forEach((element) => {
        tablaAlquileres.row
          .add([
            element.idAlquiler,
            element.equipamiento.nombreEquipamiento,
            moment(element.fechaAlquiler).format("DD/MM/YYYY"),
            moment(element.fechaEntrega).format("DD/MM/YYYY"),
            /* poner el estado con un badge */
            `<span class="badge badge-pill bg-${
              element.estadoAlquiler == "Alquilado"
                ? "success"
                : element.estadoAlquiler == "Pendiente"
                ? "warning"
                : "danger"
            }">${element.estadoAlquiler}</span>`,
            element.diasAlquiler,
            element.cantidadAlquiler,
            /* si el estado es devuelto */
            element.estadoAlquiler == "Devuelto"
              ? `
                        <button class="btn btn-primary" disabled><i class="fas fa-check"></i></button>
                        `
              : `
                        <button type="button" data-id="${element.idAlquiler}" class="btn btn-warning" id="btnEditarAlquiler"><i class="fas fa-edit"></i></button>
                        `,
          ])
          .draw();
      });
    },
  });
};

const cargarEquipamiento = () => {
  $.ajax({
    type: "GET",
    url: backend + "equipamientos/lista",
    dataType: "json",
    success: function (response) {
      response.forEach((element) => {
        selectEquipo.append(
          `<option value="${element.idEquipamiento}">${element.nombreEquipamiento}</option>`
        );
      });
    },
  });
};

const cargarCantidadCupos = () => {
  selectEquipo.on("change", function () {
    /* mostrar el numero de cupos segun el equipo elegido */
    $.ajax({
      type: "GET",
      url: backend + "equipamientos/lista",
      dataType: "json",
      success: function (response) {
        response.forEach((element) => {
          if (element.idEquipamiento == selectEquipo.val()) {
            /* agregar la cantidad de opciones el numero de cupos */
            selectStock.empty();
            for (let i = 1; i <= element.stockEquipamiento; i++) {
              selectStock.append(`<option value="${i}">${i}</option>`);
            }
          }
        });
      },
    });
  });
};

const calcularDias = () => {
  $("#fecha_entrega").on("change", function () {
    let fechaInicio = moment($("#fecha_alquiler").val());
    let fechaFin = moment($("#fecha_entrega").val());
    let dias = fechaFin.diff(fechaInicio, "days");
    $("#dias_alquiler").val(dias);
  });
};

const guardarAlquiler = () => {
  $("#btnAgregarAlquiler").on("click", function () {
    formAlquiler.reset();
    $("#color-modal").removeClass("bg-primary");
    $("#color-modal").addClass("bg-success");
    $("#titulo-form").html("Guardar Alquiler");
    $("#btnGuardarAlquiler").show();
    $("#btnActualizarAlquiler").hide();
    /* poner la fecha actual en el input de fechaAlquiler */
    $("#fecha_alquiler").val(moment().format("YYYY-MM-DD"));
    $("#modalGuardarAlquiler").modal("show");
  });

  $("#btnGuardarAlquiler").on("click", function () {
    const datosAlquiler = {
      fechaAlquiler:
        moment(formAlquiler.fecha_alquiler.value).format("YYYY-MM-DD") +
        "T00:00:00.000-05:00",
      fechaEntrega:
        moment(formAlquiler.fecha_entrega.value).format("YYYY-MM-DD") +
        "T00:00:00.000-05:00",
      estadoAlquiler: "Alquilado",
      diasAlquiler: formAlquiler.dias.value,
      cantidadAlquiler: formAlquiler.selectStock.value,
      cliente: {
        id_cli: formAlquiler.cliente.value,
      },
      equipamiento: {
        idEquipamiento: formAlquiler.selectEquipamiento.value,
      },
    };
    console.log(datosAlquiler.fechaAlquiler);
    console.log(datosAlquiler.fechaEntrega);
    $.ajax({
      type: "POST",
      url: backend + "alquileres/guardar",
      contentType: "application/json",
      data: JSON.stringify(datosAlquiler),
      dataType: "json",
      success: function (response) {
        listarAlquileres();
        alertas("Alquiler guardado correctamente", "success");
        $("#modalGuardarAlquiler").modal("hide");
      },
    });
  });
};

const editarAlquiler = () => {
    $(document).on("click" , "#btnEditarAlquiler", function(){
        formAlquiler.reset();
        $("#color-modal").removeClass("bg-success");
        $("#color-modal").addClass("bg-primary");
        $("#titulo-form").html("Actualizar Alquiler");
        $("#btnGuardarAlquiler").hide();
        $("#btnActualizarAlquiler").show();
        $("#modalGuardarAlquiler").modal("show");
        let idAlquiler = $(this).attr("data-id");
        $.ajax({
            type: "GET",
            url: backend + "alquileres/buscar/" + idAlquiler,
            dataType: "json",
            success: function (response) {
                $("#fecha_alquiler").val(moment().format("YYYY-MM-DD"));
                $("#fecha_entrega").val(moment(response.fechaEntrega).format("YYYY-MM-DD"));
                $("#dias_alquiler").val(response.diasAlquiler);
                $("#selectEquipamiento").val(response.equipamiento.idEquipamiento);
                $("#buscador_cliente").val(response.cliente.id_cli);
                $("#btnActualizarAlquiler").attr("data-id", response.idAlquiler);
                $("#select_Stock").empty();
                /* mostrar la opcion como seleccionada la cantidad */
                for (let i = 1; i <= response.equipamiento.stockEquipamiento; i++) {
                    /* evaluar si la cantidad es igual a i entonces seleccionar */
                    if (response.cantidadAlquiler == i) {
                        $("#select_Stock").append(`<option value="${i}" selected>${i}</option>`);
                    }else{
                        $("#select_Stock").append(`<option value="${i}">${i}</option>`);
                    }
                }
            }
        });
    })
}

const actualizarAlquiler = () => {
    $("#btnActualizarAlquiler").on("click", function(){
        const datosAlquiler = {
            fechaAlquiler: moment(formAlquiler.fecha_alquiler.value).format("YYYY-MM-DD") + "T00:00:00.000-05:00",
            fechaEntrega: moment(formAlquiler.fecha_entrega.value).format("YYYY-MM-DD") + "T00:00:00.000-05:00",
            estadoAlquiler: "Alquilado",
            diasAlquiler: formAlquiler.dias.value,
            cantidadAlquiler: formAlquiler.selectStock.value,
            cliente: {
                id_cli: formAlquiler.cliente.value,
            },
            equipamiento: {
                idEquipamiento: formAlquiler.selectEquipamiento.value
            }
        }
        let idAlquiler = $(this).attr("data-id");
        $.ajax({
            type: "PUT",
            url: backend  + "alquileres/actualizar/" + idAlquiler,
            contentType: "application/json",
            data: JSON.stringify(datosAlquiler),
            dataType: "json",
            success: function (response) {
                listarAlquileres();
                alertas("Alquiler actualizado correctamente", "success");
                $("#modalGuardarAlquiler").modal("hide");
            }
        });
    })
}

const mostrarId = () => {
  console.log(clienteId);
};

const alertas = (mensaje, icono) => {
  Swal.fire({
    title: mensaje,
    icon: icono,
    showConfirmButton: false,
    timer: 1000,
  });
};

const alertasToast = (mensaje, icono) => {
  const Toast = Swal.mixin({
    toast: true,
    //positicon superior derecha
    position: "top-end",
    showConfirmButton: false,
    timer: 2000,
    timerProgressBar: true,
  });
  Toast.fire({
    icon: icono,
    title: mensaje,
  });
};

$(document).ready(function () {
  listarAlquileres();
    cargarEquipamiento();
    cargarCantidadCupos();
    calcularDias();
    guardarAlquiler();
    editarAlquiler();
    actualizarAlquiler();
});
