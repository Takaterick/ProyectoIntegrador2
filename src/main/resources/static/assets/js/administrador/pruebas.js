let backend = "http://localhost:8080/api/v1/membresias";
const tablaMembresias = $("#tablaMembresias");
const arregloMembresias = [];

//METODO LISTAR MEMBRESIAS
const listarMembresias = () => {
  //Inicializamos el dataTable
  tablaMembresias.DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
    },
    ajax: {
      url: backend + "/lista",
      type: "GET",
      dataType: "json",
      dataSrc: function(response){
        return response;
      }
    },
    columns: [
      {data: "id_sus"},
      {data: "nom_sus"},
      {
        //creame un checkbox
        data: "estado_sus",
        render: function(data, type, row, meta){
          return ``;
        }
      },
      {
        data: "precio_sus",
        render: function(data, type, row, meta){
          return "S/ " + row.precio_sus.toFixed(2);
        }
      },
      {
        render: function(data, type, row){
          return `
          <button type="button" data-id="${row.id_sus}" data-membresia="${row.nom_sus}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
          <input class="form-check-input" type="checkbox" value="${row.id_sus}" id="cbo-datos" />
        `;
        }
      }
    ]
  });
}

const initArray = () => {
  //obtener los datos del checkbox
  $(document).on("click", "#cbo-datos", function(){
    //obtener el valor del checkbox
    let id = $(this).val();
    let estado = $(this).prop("checked");
    //console.log(id, estado);
    //buscar el id en el arreglo
    let indice = arregloMembresias.findIndex((obj) => obj.id_sus == id);
    //console.log(indice);
    //si no existe el id en el arreglo
    if(indice == -1){
      //agregar el id al arreglo
      arregloMembresias.push({id_sus: id, estado_sus: estado});
    }else{
      //si existe el id en el arreglo
      //actualizar el estado del id en el arreglo
      arregloMembresias[indice].estado_sus = estado;
    }
    //ahora falta si es que lo desmarco
    //si es que desmarco el checkbox
    if(!estado){
      //eliminar el id del arreglo
      arregloMembresias.splice(indice, 1);
    }
    console.log(arregloMembresias);
  })
}

const eliminarSeleccionados = () => {
  $(document).on("click", "#btn-eliminar-membresia", function(){
    //validar si es que hay elementos seleccionados
    if(arregloMembresias.length > 0){
      Swal.fire({
        title: '¿Esta seguro de eliminar los registros seleccionados?',
        text: 'Una vez eliminado no se puede recuperar!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText:'Si, eliminar!'
      }).then((result) => {
        if(result.isConfirmed){
          //iterar el arreglo
          arregloMembresias.forEach((obj) => {
            //eliminar el registro
            $.ajax({
              url: backend + '/eliminar/'+obj.id_sus,
              type: 'DELETE',
              datatype: 'json',
              success: function(res){
                alertas("Los registros seleccionados fueron eliminados!","success");
                tablaMembresias.DataTable().ajax.reload();
                //limpiar el arreglo
                arregloMembresias.splice(0, arregloMembresias.length);
              }
            })
          })
        }else{
          alertas("Los registros seleccionados no fueron eliminados!","error");
        }
      })
    }else{
      alertas("Debe seleccionar al menos un registro!","error");
    }
  })
}

const guardarMembresia = () =>{
  //Abrir el modal
  $("#btnAgregarMembresia").on("click", function(){     //??¡??¡
    $("#modalGuardarMembresia").modal("show");
  })

  //Presiona el boton guardar
  $("#guardarMembresia").on("click", function(){
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
        alertas("La membresia "+datosMembresia.nom_sus+" fue guardada!","success");
        limpiarFormulario();
        tablaMembresias.DataTable().ajax.reload();
      }
    })
  })
}

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
            tablaMembresias.DataTable().ajax.reload();
          }
        })
      }else{
        alertas("La membresia "+membresia+" no fue eliminada","error");
      }
    })
  })
}

//alertas
const alertas = (mensaje, icono, foco) =>{
  if(foco!==""){
    $('#'+foco).trigger('focus');
  }
  Swal.fire({
    title:mensaje,
    icon:icono,
    customClass: {confirmButton: 'btn btn-primary', popup: 'animated xoomIn'},
    buttonsStyling: false
  })
}


const limpiarFormulario = () =>{
  $('#id').val('');
  $('#nom_sus').val('');
  $('#precio_sus').val('');
}
  
  /* $(document).on("click", "#btnAgregarMembresia", function(){     //??¡??¡
    
  })   */ 

/* var app = {
  backend: "http://localhost:8080/api/v1/membresias",
  table : null,
  init: function () {
    app.initDatatable("#tablaMembresias");
  },
  initDatatable: function (id) {
    app.table = $(id).DataTable({
      language: {
        url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
      },
      ajax: {
        url: app.backend + "/lista",
        type: "GET",
        dataType: "json",
        dataSrc: function (json) {
          return json;
        },
      },
      columns: [
        { data: "id_sus" },
        { data: "nom_sus" },
        {
          data: "precio_sus",
          render: function (data, type, row, meta) {
            return "S/ " + row.precio_sus.toFixed(2);
          },
        },
        {
          render: function (data, type, row) {
            return `
              <button type="button" data-id="${row.id_sus}" data-membresia="${row.nom_sus}" id="btn-editar" class="btn btn-warning"><i class="fa fa-edit"></i></button>
              <button type="button" data-id="${row.id_sus}" data-membresia="${row.nom_sus}" id="btn-eliminar" class="btn btn-danger"><i class="fa fa-trash"></i></button>
            `;
          },
        },
      ],
    });

    //Abrir el modal
    $(document).on("click", "#btnAgregarMembresia", function () {
      app.limpiar();
      $("#modalGuardarMembresia").modal("show");
    });
  },

  limpiar: function (){
    $('#id').val('');
    $('#nom_sus').val('');
    $('#precio_sus').val('');
  },

  //Metodo Guardar
  save: function () {
    $('#guardarMembresia').on('click', function(){
      const datosMembresia = {
        id_sus: $('#id').val(),
        nom_sus: $('#nom_sus').val(),
        precio_sus: $('#precio_sus').val()
      }

      $.ajax({
        url: app.backend + '/guardar',
        contentType: 'application/json',
        type: 'POST',
        data:JSON.stringify(datosMembresia),
        dataType: 'json',
        success: function(data){
          limpiarFormulario();
        }
      })
    })
  },

  //Metodo Eliminar
  eliminarMembresia: function() {
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
            url: app.backend + '/eliminar/'+id,
            type: 'DELETE',
            datatype: 'json',
            success: function(res){
              app.alertas("La membresia "+membresia+" fue eliminada!","success");
              app.table.ajax.reload();
            }
          })
        }else{
          app.alertas("La membresia "+membresia+" no fue eliminada","error");
        }
      })
    })
  },

  //Metodo editar
  editar: function(){
    $(document).on('click', '#btn-editar', function(){
      $("#modalGuardarMembresia").modal("show");
      let id = $(this).attr("data-id");
      
      $.ajax({
        url: app.backend + '/'+id,
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            $("#id").val(response.id_sus);
            $("#nom_sus").val(response.nom_sus);
            $("#precio_sus").val(response.precio_sus);
        }
      });
    })
  },

  //Alertas
  alertas: function(mensaje, icono, foco){
    if(foco!==""){
      $('#'+foco).trigger('focus');
    }
    Swal.fire({
      title:mensaje,
      icon:icono,
      customClass: {confirmButton: 'btn btn-primary', popup: 'animated xoomIn'},
      buttonsStyling: false
    })
  }
}; */




$(document).ready(function () {
  listarMembresias();
  guardarMembresia();
  limpiarFormulario();
  eliminarMembresia();
  initArray();
  eliminarSeleccionados();
});