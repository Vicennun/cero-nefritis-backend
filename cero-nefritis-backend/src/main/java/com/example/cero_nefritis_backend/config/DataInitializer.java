package com.example.cero_nefritis_backend.config;

import com.example.cero_nefritis_backend.model.*;
import com.example.cero_nefritis_backend.repository.*;
import com.example.cero_nefritis_backend.service.RiesgoService;
import com.example.cero_nefritis_backend.service.SeguimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final EvaluacionRiesgoRepository evaluacionRepo;
    private final SeguimientoActivoRepository seguimientoRepo;
    private final AtencionRepository atencionRepo;
    private final ExamenLaboratorioRepository examenRepo;
    private final PrevencionGuiaRepository prevencionRepo;
    private final MensajeRepository mensajeRepo;
    private final RecordatorioRepository recordatorioRepo;

    private final RiesgoService riesgoService;
    private final SeguimientoService seguimientoService;

    @Override
    public void run(String... args) {
        // Evita duplicar datos si la base ya fue inicializada
        if (usuarioRepo.count() > 0) {
            return;
        }

        // ==========================================
        // 1. USUARIOS (PROFESIONALES Y PACIENTES)
        // ==========================================
        Usuario drFreddy = new Usuario();
        drFreddy.setNombreCompleto("Dr. Freddy Turbina");
        drFreddy.setRut("11.222.333-4");
        drFreddy.setEmail("freddy.turbina@ceronefritis.cl");
        drFreddy.setPassword("12345678");
        drFreddy.setEdad(42);
        drFreddy.setSexo("Masculino");
        drFreddy.setTelefono("+56 9 7766 5544");
        drFreddy.setRol(Rol.PROFESIONAL);
        drFreddy.setEspecialidad("Médico Cirujano");
        drFreddy.setConsentimientoInformado(true);
        drFreddy = usuarioRepo.save(drFreddy);

        Usuario draValentina = new Usuario();
        draValentina.setNombreCompleto("Dra. Valentina Rojas");
        draValentina.setRut("14.555.666-7");
        draValentina.setEmail("valentina.rojas@ceronefritis.cl");
        draValentina.setPassword("12345678");
        draValentina.setEdad(35);
        draValentina.setSexo("Femenino");
        draValentina.setTelefono("+56 9 6655 4433");
        draValentina.setRol(Rol.PROFESIONAL);
        draValentina.setEspecialidad("Medicina General");
        draValentina.setConsentimientoInformado(true);
        draValentina = usuarioRepo.save(draValentina);

        Usuario camila = new Usuario();
        camila.setNombreCompleto("Camila Soto");
        camila.setRut("20.123.456-7");
        camila.setEmail("camila.soto@correo.cl");
        camila.setPassword("12345678");
        camila.setEdad(24);
        camila.setSexo("Femenino");
        camila.setTelefono("+56 9 8877 6655");
        camila.setRol(Rol.PACIENTE);
        camila.setConsentimientoInformado(true);
        camila = usuarioRepo.save(camila);

        Usuario mateo = new Usuario();
        mateo.setNombreCompleto("Mateo Fuentes");
        mateo.setRut("12.345.678-9");
        mateo.setEmail("mateo.fuentes@correo.cl");
        mateo.setPassword("12345678");
        mateo.setEdad(67);
        mateo.setSexo("Masculino");
        mateo.setTelefono("+56 9 1234 5678");
        mateo.setRol(Rol.PACIENTE);
        mateo.setConsentimientoInformado(true);
        mateo = usuarioRepo.save(mateo);

        Usuario sofia = new Usuario();
        sofia.setNombreCompleto("Sofía Herrera");
        sofia.setRut("15.432.198-k");
        sofia.setEmail("sofia.herrera@correo.cl");
        sofia.setPassword("12345678");
        sofia.setEdad(32);
        sofia.setSexo("Femenino");
        sofia.setTelefono("+56 9 5544 3322");
        sofia.setRol(Rol.PACIENTE);
        sofia.setConsentimientoInformado(true);
        sofia = usuarioRepo.save(sofia);

        Usuario daniela = new Usuario();
        daniela.setNombreCompleto("Daniela Vera");
        daniela.setRut("17.654.321-0");
        daniela.setEmail("daniela.vera@correo.cl");
        daniela.setPassword("12345678");
        daniela.setEdad(36);
        daniela.setSexo("Femenino");
        daniela.setTelefono("+56 9 9988 7766");
        daniela.setRol(Rol.PACIENTE);
        daniela.setConsentimientoInformado(true);
        daniela = usuarioRepo.save(daniela);

        // ==========================================
        // 2. EVALUACIONES DE RIESGO (PANEL MÉDICO)
        // ==========================================
        EvaluacionRiesgo evMateo = new EvaluacionRiesgo();
        evMateo.setPaciente(mateo);
        evMateo.setEdadMayor65(true);
        evMateo.setHombre(true);
        evMateo.setDiabetes(true);
        evMateo.setItuRecurrente(true);
        evMateo.setLitiasis(true);
        evMateo.setFiebreMayor38(true);
        evMateo.setDolorLumbarOFlanco(true);
        evMateo.setEstadoAtencion("EN_PROCESO");
        evaluacionRepo.save(riesgoService.calcularYClasificar(evMateo));

        EvaluacionRiesgo evSofia = new EvaluacionRiesgo();
        evSofia.setPaciente(sofia);
        evSofia.setEmbarazo(true);
        evSofia.setItuRecurrente(true);
        evSofia.setEstadoAtencion("EN_PROCESO");
        evaluacionRepo.save(riesgoService.calcularYClasificar(evSofia));

        EvaluacionRiesgo evCamila = new EvaluacionRiesgo();
        evCamila.setPaciente(camila);
        evCamila.setItuRecurrente(true);
        evCamila.setAntibioticoUltimos30Dias(true);
        evCamila.setEstadoAtencion("COMPLETADO");
        evaluacionRepo.save(riesgoService.calcularYClasificar(evCamila));

        EvaluacionRiesgo evDaniela = new EvaluacionRiesgo();
        evDaniela.setPaciente(daniela);
        evDaniela.setLitiasis(true);
        evDaniela.setEstadoAtencion("COMPLETADO");
        evaluacionRepo.save(riesgoService.calcularYClasificar(evDaniela));

        // ==========================================
        // 3. SEGUIMIENTOS ACTIVOS (SEMÁFORO CLÍNICO)
        // ==========================================
        SeguimientoActivo segMateo = new SeguimientoActivo();
        segMateo.setPaciente(mateo);
        segMateo.setDiagnostico("Pielonefritis Aguda");
        segMateo.setFechaDiagnostico(LocalDate.now().minusDays(3));
        segMateo.setDiaActual(3);
        segMateo.setTotalDias(7);
        segMateo.setAntibioticoActual("Ciprofloxacino 500mg");
        segMateo.setTemperaturaActual(38.6);
        segMateo.setDolorLumbarActual(7);
        segMateo.setGlucosaCapilarActual(265);
        segMateo.setVasosHidratacion(4);
        seguimientoService.evaluarSemaforo(segMateo, false);

        SeguimientoActivo segSofia = new SeguimientoActivo();
        segSofia.setPaciente(sofia);
        segSofia.setDiagnostico("ITU Complicada en Gestación");
        segSofia.setFechaDiagnostico(LocalDate.now().minusDays(2));
        segSofia.setDiaActual(2);
        segSofia.setTotalDias(7);
        segSofia.setAntibioticoActual("Cefadroxilo 500mg");
        segSofia.setTemperaturaActual(37.6);
        segSofia.setDolorLumbarActual(5);
        segSofia.setGlucosaCapilarActual(98);
        segSofia.setVasosHidratacion(4);
        seguimientoService.evaluarSemaforo(segSofia, true);

        SeguimientoActivo segDaniela = new SeguimientoActivo();
        segDaniela.setPaciente(daniela);
        segDaniela.setDiagnostico("ITU Baja en Resolución");
        segDaniela.setFechaDiagnostico(LocalDate.now().minusDays(7));
        segDaniela.setDiaActual(7);
        segDaniela.setTotalDias(7);
        segDaniela.setAntibioticoActual("Nitrofurantoína 100mg");
        segDaniela.setTemperaturaActual(36.5);
        segDaniela.setDolorLumbarActual(0);
        segDaniela.setGlucosaCapilarActual(92);
        segDaniela.setVasosHidratacion(8);
        seguimientoService.evaluarSemaforo(segDaniela, false);

        // ==========================================
        // 4. ATENCIONES CLÍNICAS
        // ==========================================
        Atencion atMateo = new Atencion();
        atMateo.setCodigoAtencion("0000360");
        atMateo.setPaciente(mateo);
        atMateo.setProfesional(drFreddy);
        atMateo.setTipo("consulta");
        atMateo.setTitulo("Evaluación por sospecha de pielonefritis");
        atMateo.setModalidad("Presencial");
        atMateo.setGravedad("Extremo");
        atMateo.setEstado("En proceso");
        atMateo.setFechaHora(LocalDateTime.now().minusHours(4));
        atMateo.setTemperatura("38,6 °C");
        atMateo.setPresionArterial("128/82 mmHg");
        atMateo.setFrecuenciaCardiaca("96 lpm");
        atMateo.setSaturacion("97 %");
        atMateo.setMotivoConsulta("Dolor en la espalda baja, fiebre y ardor al orinar desde hace 3 días.");
        atMateo.setObservacionesClinicas("Paciente con diabetes tipo 2 descontrolada y antecedentes de ITU recurrentes. Puño percusión lumbar derecha positiva.");
        atMateo.setPlanIndicaciones("Solicitar examen de orina y urocultivo. Reforzar hidratación y control estricto de temperatura.");
        atMateo.setSintomasReportados(List.of("Fiebre", "Escalofríos", "Ardor al orinar", "Dolor en la espalda baja", "Náuseas"));
        atencionRepo.save(atMateo);

        Atencion atCamila = new Atencion();
        atCamila.setCodigoAtencion("0000361");
        atCamila.setPaciente(camila);
        atCamila.setProfesional(draValentina);
        atCamila.setTipo("control");
        atCamila.setTitulo("Control preventivo de vías urinarias");
        atCamila.setModalidad("Videoconsulta");
        atCamila.setGravedad("Moderado");
        atCamila.setEstado("En proceso");
        atCamila.setFechaHora(LocalDateTime.now().plusDays(3));
        atCamila.setTemperatura("36,7 °C");
        atCamila.setPresionArterial("115/75 mmHg");
        atCamila.setFrecuenciaCardiaca("74 lpm");
        atCamila.setSaturacion("99 %");
        atCamila.setMotivoConsulta("Seguimiento de síntomas urinarios leves y pautas de hidratación.");
        atCamila.setObservacionesClinicas("Evolución favorable sin fiebre ni compromiso lumbar.");
        atCamila.setPlanIndicaciones("Mantener ingesta hídrica de 2L diarios y completar registro semanal.");
        atCamila.setSintomasReportados(List.of("Urgencia miccional leve"));
        atencionRepo.save(atCamila);

        // ==========================================
        // 5. EXÁMENES DE LABORATORIO
        // ==========================================
        ExamenLaboratorio exMateo = new ExamenLaboratorio();
        exMateo.setPaciente(mateo);
        exMateo.setNombreArchivo("Urocultivo_Mateo_Octubre.pdf");
        exMateo.setUrlArchivo("/uploads/Urocultivo_Mateo_Octubre.pdf");
        exMateo.setEstado("Enviado");
        examenRepo.save(exMateo);

        ExamenLaboratorio exCamila = new ExamenLaboratorio();
        exCamila.setPaciente(camila);
        exCamila.setNombreArchivo("Examen_Orina_Completa.pdf");
        exCamila.setUrlArchivo("/uploads/Examen_Orina_Completa.pdf");
        exCamila.setEstado("Revisado");
        exCamila.setResultadoCultivo("negativo");
        exCamila.setMicroorganismo("Sin desarrollo bacteriano");
        exCamila.setRecuentoColonias("< 10.000 UFC/mL");
        exCamila.setResumenAntibiograma("No aplica (cultivo negativo).");
        examenRepo.save(exCamila);

        // ==========================================
        // 6. GUÍAS DE PREVENCIÓN
        // ==========================================
        PrevencionGuia g1 = new PrevencionGuia();
        g1.setTitulo("Hidratación constante");
        g1.setCategoria("Hábitos diarios");
        g1.setDescripcion("Bebe agua durante todo el día (aprox. 1,5 a 2 litros, salvo indicación médica) para ayudar a eliminar bacterias del tracto urinario.");
        g1.setAutor("Dra. Valentina Rojas");
        g1.setEstado("Activa");

        PrevencionGuia g2 = new PrevencionGuia();
        g2.setTitulo("No retener la orina");
        g2.setCategoria("Hábitos diarios");
        g2.setDescripcion("Ve al baño cuando sientas ganas y vacía la vejiga por completo cada vez para evitar la proliferación bacteriana.");
        g2.setAutor("Dr. Freddy Turbina");
        g2.setEstado("Activa");

        PrevencionGuia g3 = new PrevencionGuia();
        g3.setTitulo("Higiene post-coital");
        g3.setCategoria("Salud sexual");
        g3.setDescripcion("Orinar inmediatamente después de las relaciones sexuales ayuda a expulsar bacterias que puedan haber entrado a la uretra.");
        g3.setAutor("Matrona Silvia Pino");
        g3.setEstado("En revisión");

        PrevencionGuia g4 = new PrevencionGuia();
        g4.setTitulo("Fiebre o escalofríos");
        g4.setCategoria("Señales de alarma");
        g4.setDescripcion("Temperatura sobre 38 °C o escalofríos junto con síntomas urinarios requiere consulta de urgencia el mismo día.");
        g4.setAutor("Dra. Valentina Rojas");
        g4.setEstado("Activa");

        PrevencionGuia g5 = new PrevencionGuia();
        g5.setTitulo("Uso profiláctico de arándanos");
        g5.setCategoria("Nutrición / Suplementos");
        g5.setDescripcion("El uso de cápsulas de cranberry puede reducir la adherencia de E. coli, pero requiere validación de dosis según historial gástrico.");
        g5.setAutor("Nut. Camila Ibáñez");
        g5.setEstado("Borrador");

        prevencionRepo.saveAll(List.of(g1, g2, g3, g4, g5));

        // ==========================================
        // 7. MENSAJES Y RECORDATORIOS INICIALES
        // ==========================================
        Mensaje m1 = new Mensaje();
        m1.setRemitente(draValentina);
        m1.setDestinatario(camila);
        m1.setContenido("Hola Camila, revisé tus últimos controles. ¿Cómo te has sentido esta semana?");
        m1.setFechaEnvio(LocalDateTime.now().minusHours(2));
        m1.setLeido(true);

        Mensaje m2 = new Mensaje();
        m2.setRemitente(camila);
        m2.setDestinatario(draValentina);
        m2.setContenido("Hola doctora, mucho mejor con el aumento de hidratación y sin molestias al orinar.");
        m2.setFechaEnvio(LocalDateTime.now().minusHours(1));
        m2.setLeido(false);

        mensajeRepo.saveAll(List.of(m1, m2));

        Recordatorio r1 = new Recordatorio();
        r1.setPaciente(camila);
        r1.setTitulo("Hidratación diaria");
        r1.setDescripcion("Beber entre 1,5 y 2 litros de agua distribuidos durante el día.");
        r1.setFrecuencia("Diario");

        Recordatorio r2 = new Recordatorio();
        r2.setPaciente(camila);
        r2.setTitulo("Control de temperatura");
        r2.setDescripcion("Registrar temperatura corporal si aparecen escalofríos o malestar lumbar.");
        r2.setFrecuencia("Alarma");

        recordatorioRepo.saveAll(List.of(r1, r2));

        System.out.println(">>> Base de datos CERO NEFRITIS inicializada con datos clínicos de prueba.");
    }
}