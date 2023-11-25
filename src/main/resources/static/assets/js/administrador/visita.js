const backend = "http://localhost:8080/api/v1/";
const formVisita = $("#formVisita")[0];
const buscadorMiembro = $("#bucador_miembro");
const selectMembresiaMiembro = $("#selectMembresiaMiembro");
const tablaVisitas = $("#tablaVisitas").DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
    },
})

const agregarVisita = () => {
    /* si el switch esta inactivo deshabilitar el input de busqueda */
    $("#switchCliente").on("change", function(){
        if($(this).is(":checked")){
            formVisita.buscadorMiembro.disabled = false;
            formVisita.nombreMiembro.disabled = true;
            formVisita.apellidoMiembro.disabled = true;
            formVisita.selectMembresiaMiembro.disabled = true;
            formVisita.selectVisitaMiembro.value = "Visita con Membresia";
            formVisita.selectVisitaMiembro.disabled = true;
        }else{
            /* limpiar el nombremiembro */
            formVisita.nombreMiembro.value = "";
            formVisita.apellidoMiembro.value = "";
            formVisita.nombreMiembro.disabled = false;
            formVisita.apellidoMiembro.disabled = false;
            formVisita.selectMembresiaMiembro.disabled = true;
            formVisita.selectVisitaMiembro.value = "Visita Regular";
            formVisita.selectMembresiaMiembro.value = "Sin membresia";
            formVisita.selectVisitaMiembro.disabled = true;
            formVisita.buscadorMiembro.disabled = true;
        }
    })
    $("#btn-abrirModalVisita").on("click", function(){
        formVisita.reset();
        formVisita.buscadorMiembro.disabled = true;
        formVisita.selectVisitaMiembro.value = "Visita Regular";
        formVisita.selectVisitaMiembro.disabled = true;
        formVisita.selectMembresiaMiembro.value = "Sin membresia"
        formVisita.selectMembresiaMiembro.disabled = true;
        $("#modalVisita").modal("show");
    })
    buscadorMiembro.on("change", function(){
        $.ajax({
            type: "GET",
            url: backend + "suscripciones/lista",
            dataType: "json",
            success: function (response) {
                response.forEach((element) => {
                    if(element.id == buscadorMiembro.val()){
                        formVisita.idSuscripcion.value = element.id;
                        formVisita.nombreMiembro.value = element.cliente.nom_cli;
                        formVisita.apellidoMiembro.value = element.cliente.ape_cli;
                        formVisita.selectMembresiaMiembro.value = element.membresia.nom_sus;
                    }
                });
            }
        });
    })

    $("#btnGuardarVisita").on("click", function(){
        /* si el valor del selectMembresiaMiembro es Sin membresia entonces la suscripcion es nu */

        let datos = {
            miembroVisita: formVisita.nombreMiembro.value + " " + formVisita.apellidoMiembro.value,
            membresiaVisita: formVisita.selectMembresiaMiembro.value,
            fechaVisita: moment().format("YYYY-MM-DD"),
            tipoVisita: formVisita.selectVisitaMiembro.value,
            pagoVisita: 30.00,
            /* si el valor del idSuscripcion esta vacio entonces la suscripcion es null sino agregar el id */
            /* suscripcion: formVisita.idSuscripcion.value == "" ? null : {
                id: formVisita.idSuscripcion.value
            } */
            suscripcion: formVisita.selectMembresiaMiembro.value == "Sin membresia" ? null : {id: formVisita.idSuscripcion.value}
        }
        $.ajax({
            type: "POST",
            url: backend + "visitas/guardar",
            contentType: "application/json",
            data: JSON.stringify(datos),
            dataType: "json",
            success: function (response) {
                alertas("Visita registrada correctamente", "success");
                $("#modalVisita").modal("hide");
                listarVisitas();
            }
        });
    })
}

const cargarMiembros = () => {
    $.ajax({
        type: "GET",
        url: backend + "suscripciones/lista",
        dataType: "json",
        success: function (response) {
            response.forEach((element) => {
                buscadorMiembro.append(
                    `<option value="${element.id}">${element.cliente.nom_cli} ${element.cliente.ape_cli}</option>`
                )
            });
        }
    });
}

const cargarMembresias = () => {
    $.ajax({
        type: "GET",
        url: backend + "membresias/lista",
        dataType: "json",
        success: function (response) {
            response.forEach((element) => {
                selectMembresiaMiembro.append(
                    `<option value="${element.nom_sus}">${element.nom_sus}</option>`
                )
            });
        }
    });
}

const listarVisitas = () => {
    tablaVisitas.clear();
    $.ajax({
        type: "GET",
        url: backend + "visitas/lista",
        dataType: "json",
        success: function (response) {
            $.each(response, function (i, value) { 
                tablaVisitas.row
                .add([
                    value.idVisita,
                    value.miembroVisita,
                    value.membresiaVisita,
                    value.fechaVisita,
                    value.tipoVisita,
                    /*pago con formato de moneda*/
                    "S/ " + parseFloat(value.pagoVisita).toFixed(1),
                    /* si la membresiaVisita es Sin membresia entonces agregar el boton de imprimir voucher */
                    value.membresiaVisita == "Sin membresia" ? `<a href="${backend}visitas/boucher?idVis=${value.idVisita}" class="btn btn-success"><i class="fa fa-print"></i></a>` : `<a href="/administrador/suscripcion" class="btn btn-warning"><i class="fa-solid fa-arrow-right-to-bracket"></i></a>`,
                ])
                .draw();
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
    listarVisitas();
    agregarVisita();
    cargarMiembros();
    cargarMembresias();
})