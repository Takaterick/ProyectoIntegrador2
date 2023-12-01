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

import com.proyecto.gym.models.entity.Visita;
import com.proyecto.gym.models.repository.VisitaRepository;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class VisitaServiceImpl implements IVisitaService{

    @Autowired
    private VisitaRepository visitaRepository;

    @Override
    public List<Visita> listarTodos() {
        return (List<Visita>) visitaRepository.findAll();
    }

    @Override
    public Visita guardarVisita(Visita visita) {
        return visitaRepository.save(visita);
    }

    @Override
    public Visita buscarPorId(Long Id) {
        return visitaRepository.findById(Id).orElse(null);
    }

    @Override
    public void eliminarVisita(Long Id) {
        visitaRepository.deleteById(Id);
    }

    @Override
    public Visita actualizarVisita(Visita visita, Long Id) {
        Visita visitaActual = visitaRepository.findById(Id).orElse(null);
        visitaActual.setMembresiaVisita(visita.getMembresiaVisita());
        visitaActual.setMiembroVisita(visita.getMiembroVisita());
        visitaActual.setPagoVisita(visita.getPagoVisita());
        visitaActual.setTipoVisita(visita.getTipoVisita());
        visitaActual.setFechaVisita(visita.getFechaVisita());

        return visitaRepository.save(visitaActual);
    }

    @Override
    public ResponseEntity<byte[]> exportarBoucher(Long Id) {
        Visita visita = visitaRepository.findById(Id).orElse(null);
        try {
            File file = ResourceUtils.getFile("classpath:templates/report/boucherVisita.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            HashMap<String, Object> params = new HashMap<>();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String fechaTicket = formatter.format(new Date());
            String fechaVisita = formatter.format(visita.getFechaVisita());

            //obtener un numero de ticket con el siguiente formato: 000001
            String numTicket = String.format("%06d", visita.getIdVisita());

            //darle formato al subtotal, igv y total para que se muestre S/ 00.00
            String subTotal = String.format("%.2f", visita.getPagoVisita());
            String igv = String.format("%.2f", visita.getPagoVisita() * 0.00);
            String total = String.format("%.2f", visita.getPagoVisita());


            params.put("nombreCliente", visita.getMiembroVisita());
            params.put("fechaVisita", fechaVisita);
            params.put("fechaTicket", fechaTicket);
            params.put("numTicket", numTicket);
            params.put("nombreVisita", visita.getTipoVisita());
            params.put("subTotal", "S/ "+subTotal);
            params.put("igv", "S/ "+igv);
            params.put("total", "S/ "+total);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            
            byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            //el nombre del archivo pdf sera el siguiente: "ticketGym-000001.pdf"
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("boucherVisita-"+numTicket+".pdf").build());
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public Integer visitasTotales() {
        return visitaRepository.visitasTotales();
    }
    
}
