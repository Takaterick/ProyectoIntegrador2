const abrirTaller = () => {
    $(".mostrarTaller").click(function () {
        $(".descripcion").empty();
        let idModal = $(this).data("modal");
        $("#"+idModal).modal("show");
    })
}




$(document).ready(function () {
    abrirTaller();
    $(".dias-restantes").each(function () {
      let fecha = $(this).data("restantes");
      let hora = $(this).data("hora");
      let mes = fecha.split("-")[1];
        fecha = moment(fecha).format("YYYY-MM-DD") + "T" + hora;   
        $(this).text(moment(fecha).endOf(mes).fromNow());
    });

    $(".fecha-taller").each(function () {
      let fecha = $(this).data("fecha");
      //condicional de si la fecha es igual a la de hoy que ponga como texto hoy
      $(this).text(moment(fecha, "YYYY-MM-DD").format("DD MMM YYYY"));
    });
});