//VARIABLES PARA EL END POINT Y LA TABLA
const backend = "http://localhost:8080/api/v1/membresias";
const tablaMembresias = $("#tablaMembresias").DataTable({
  language: {
    url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
  },
});

/*------------METODOS CRUD------------*/

//METODO LISTAR MEMBRESIAS
const listarMembresias = () => {
  tablaMembresias.clear();
  $.ajax({
    type: "GET",
    url: backend + "/lista",
    dataType: "json",
    success: function (response) {
      $.each(response, function (i, value) { 
        //limpiar tabla
        tablaMembresias.row.add([
          value.id_sus,
          value.nom_sus,
          "S/ " + value.precio_sus,
          `
          <button type="button" data-id="${value.id_sus}" data-membresia="${value.nom_sus}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
          <button type="button" data-id="${value.id_sus}" data-membresia="${value.nom_sus}" id="btn-eliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
          `
        ]).draw(false);
      });
    }
  });
}

//METODO GUARDAR MEMBRESIA
const guardarMembresia = () =>{
  //Abrir el modal
  $("#btnAgregarMembresia").on("click", function(){
    limpiarFormulario();
    $("#titulo-form").html("Guardar Membresia");
    $("#btn-editar-form").hide();
    $("#btn-guardar").show();
    $("#modalGuardarMembresia").modal("show");
  })

  //Presiona el boton guardar
  $("#btn-guardar").on("click", function(){
    const datosMembresia = {
      id_sus: $('#id').val(),
      nom_sus: $('#nom_sus').val(),
      precio_sus: $('#precio_sus').val()
    }
    $.ajax({
      url: backend + '/guardar',
      contentType: 'application/json',
      type: 'POST',
      data:JSON.stringify(datosMembresia),
      dataType: 'json',
      success: function(data){
        if(data.tipo === "success"){
          alertas(data.mensaje,data.tipo);
          limpiarFormulario();
          listarMembresias();
          $("#modalGuardarMembresia").modal("hide");
          return;
        }else{
          alertas(data.mensaje,data.tipo);
        }
      }
    })
  })
}

//METODO EDITAR MEMBRESIA  sale undefined
const editarMembresia = () =>{
  $(document).on('click', '#btn-editar', function(){
    $("#modalGuardarMembresia").modal("show");
    $("#btn-guardar").hide();
    $("#btn-editar-form").show();
    $("#titulo-form").html("Editar Membresia");
    let id = $(this).attr("data-id");
    $.ajax({
      url: backend + '/buscar/'+id,
      type: 'GET',
      dataType: 'json',
      success: function (response) {
          $("#id").val(response.id_sus);
          $("#nom_sus").val(response.nom_sus);
          $("#precio_sus").val(response.precio_sus);
      }
    });
  })
}

const actualizarMembresia = () =>{
  $("#btn-editar-form").on("click", function(){
    const datosMembresia = {
      id_sus: $('#id').val(),
      nom_sus: $('#nom_sus').val(),
      precio_sus: $('#precio_sus').val()
    }
    $.ajax({
      url: backend + '/actualizar/'+datosMembresia.id_sus,
      contentType: 'application/json',
      type: 'PUT',
      data:JSON.stringify(datosMembresia),
      dataType: 'json',
      success: function(data){
        if(data.tipo === "success"){
          alertas(data.mensaje,data.tipo);
          limpiarFormulario();
          listarMembresias();
          $("#modalGuardarMembresia").modal("hide");
          return;
        }else{
          alertas(data.mensaje,data.tipo);
        }
      }
    })
  })
}

//METODO ELIMINAR MEMBRESIA
const eliminarMembresia = () =>{
  $(document).on('click', '#btn-eliminar', function(){
    let id = $(this).attr("data-id");
    let membresia = $(this).attr("data-membresia");

    Swal.fire({
      title: '¿Esta seguro de eliminar la membresia  '+membresia+'?',
      text: 'Una vez eliminado no se puede recuperar!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if(result.isConfirmed){
        $.ajax({
          url: backend + '/eliminar/'+id,
          type: 'DELETE',
          datatype: 'json',
          success: function(res){
            alertas("La membresia "+membresia+" fue eliminada!","success");
            listarMembresias();
          }
        })
      }else{
        alertas("La membresia "+membresia+" no fue eliminada","error");
      }
    })
  })
}

/*------------METODOS CRUD------------*/


/*------------METODOS SECUNDARIOS------------*/

//Metodo alertas
const alertas = (mensaje, icono) => {
  Swal.fire({
    title: mensaje,
    icon: icono,
    showConfirmButton: false,
    timer: 1000,
  });
};

//Metodo para limpiar
const limpiarFormulario = () =>{
  $('#id').val('');
  $('#nom_sus').val('');
  $('#precio_sus').val('');
}


//probar otra forma de hacer de api


/*------------METODOS SECUNDARIOS------------*/

$(document).ready(function () {
  listarMembresias();
  guardarMembresia();
  limpiarFormulario();
  eliminarMembresia();
  editarMembresia();
  actualizarMembresia();
});

//Referencias
//Java Guides
//Soul Dev