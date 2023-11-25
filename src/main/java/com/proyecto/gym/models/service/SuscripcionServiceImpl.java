package com.proyecto.gym.models.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.proyecto.gym.models.entity.Suscripcion;
import com.proyecto.gym.models.repository.SuscripcionRepository;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class SuscripcionServiceImpl implements ISuscripcion {

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Override
    public List<Suscripcion> listarTodos() {
        return (List<Suscripcion>) suscripcionRepository.findAll();
    }

    @Override
    public Suscripcion guardarSuscripcion(Suscripcion suscripcion) {

        suscripcion.setFechaInicio(new Date());
        suscripcion.setFechaFin(new Date());
        suscripcion.setEstado("Pendiente");

        return suscripcionRepository.save(suscripcion);
    }

    @Override
    public Suscripcion buscarPorId(Long Id) {
        return suscripcionRepository.findById(Id).orElse(null);
    }

    @Override
    public void eliminarSuscripcion(Long Id) {
        suscripcionRepository.deleteById(Id);
    }

    @Override
    public Suscripcion actualizarSuscripcion(Suscripcion suscripcion, Long Id) {
        Suscripcion suscripcionActual = suscripcionRepository.findById(Id).orElse(null);

        suscripcionActual.setFechaInicio(suscripcion.getFechaInicio());
        suscripcionActual.setFechaFin(suscripcion.getFechaFin());
        suscripcionActual.setEstado(suscripcion.getEstado());
        suscripcionActual.setMembresia(suscripcion.getMembresia());

        switch (suscripcionActual.getEstado()) {
            case "Cancelado":
                suscripcionActual.getCliente().getUsuario().setBloqueo(1);
                suscripcionActual.getCliente().getUsuario().setDesc_bloq("Cuenta dada de baja");
                break;
            case "Pagado":
                suscripcionActual.getCliente().getUsuario().setBloqueo(0);
                suscripcionActual.getCliente().getUsuario().setDesc_bloq("Realizo el pago respectivo");
                break;
            default:
                break;
        }

        return suscripcionRepository.save(suscripcionActual);
    }

    @Override
    public Suscripcion buscarPorCliente(Long Id) {
        return suscripcionRepository.findByCliente_Id(Id);
    }

    @Override
    public ResponseEntity<byte[]> exportarBoucher(Long Id){
        Suscripcion suscripcion = suscripcionRepository.findById(Id).orElse(null);
        try {

            File file = ResourceUtils.getFile("classpath:templates/report/ticketGym.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            HashMap<String, Object> params = new HashMap<>();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String fechaTicket = formatter.format(new Date());
            String fechaInicio = formatter.format(suscripcion.getFechaInicio());
            String fechaFin = formatter.format(suscripcion.getFechaFin());

            //obtener un numero de ticket con el siguiente formato: 000001
            String numTicket = String.format("%06d", suscripcion.getId());

            //darle formato al subtotal, igv y total para que se muestre S/ 00.00
            String subTotal = String.format("%.2f", suscripcion.getMembresia().getPrecio_sus());
            String igv = String.format("%.2f", suscripcion.getMembresia().getPrecio_sus() * 0.00);
            String total = String.format("%.2f", suscripcion.getMembresia().getPrecio_sus());


            params.put("nombreCliente", suscripcion.getCliente().getNom_cli() + " " + suscripcion.getCliente().getApe_cli());
            params.put("numeroCliente", suscripcion.getCliente().getTel_cli());
            params.put("fechaInicio", fechaInicio);
            params.put("fechaFin", fechaFin);
            params.put("fechaTicket", fechaTicket);
            params.put("numTicket", numTicket);
            params.put("nombreMembresia", suscripcion.getMembresia().getNom_sus());
            params.put("subTotal", "S/ "+subTotal);
            params.put("igv", "S/ "+igv);
            params.put("total", "S/ "+total);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            
            byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            //el nombre del archivo pdf sera el siguiente: "ticketGym-000001.pdf"
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("ticketGym-"+numTicket+".pdf").build());
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Double ventaDiaria() {
        return suscripcionRepository.ventaDiaria();
    }

    @Override
    public Double ventaMensual() {
        return suscripcionRepository.ventaMensual();
    }

    @Override
    public Integer suscripcionesTotales() {
        return suscripcionRepository.suscripcionesTotales();
    }
}
