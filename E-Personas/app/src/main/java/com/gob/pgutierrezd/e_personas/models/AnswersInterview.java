package com.gob.pgutierrezd.e_personas.models;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by pgutierrezd on 03/11/2016.
 */
public class AnswersInterview {

    private String sIdUsuario;
    private String sOpinionPolicia;
    private String sPatrullaje;
    private String sHorarioPatrullaje;
    private String sAccionesMunicipio;
    private String sTramitePendiente;
    private String sTramitePendienteComentario;
    private String sGobiernoMarcosAguilar;
    private String sEsperaMarcosAguilar;
    private String sPracticaDeportes;
    private String sDeporte;
    private String sNecesidadColonia;
    private String oOpinionRealizarObraNombre;
    private String oOpinionRealizaObra;
    private String oHorarioMayorRiesgo;
    private String oRiesgosFraccionamiento;
    private String bHayLotesBaldios;
    private String bConsideraRiesgos;
    private String bDebenAtenderse;
    private String bRealizadoLimpieza;
    private String bDesmalezadoLotes;
    private String aAparienciaAlameda;
    private String aSeguroTransitar;
    private String aAlamedaRecreo;
    private String aProblemaAlameda;
    private String aMejorarImagen;
    private String aLugarEncuesta;
    private String fecha;
    private String status;
    private String latitud;
    private String longitud;
    private String comentarios;
    private String referenciaMovil;

    public String getsIdUsuario() {
        return sIdUsuario;
    }

    public void setsIdUsuario(String sIdUsuario) {
        this.sIdUsuario = sIdUsuario;
    }

    public String getsOpinionPolicia() {
        return sOpinionPolicia;
    }

    public void setsOpinionPolicia(String sOpinionPolicia) {
        this.sOpinionPolicia = sOpinionPolicia;
    }

    public String getsPatrullaje() {
        return sPatrullaje;
    }

    public void setsPatrullaje(String sPatrullaje) {
        this.sPatrullaje = sPatrullaje;
    }

    public String getsHorarioPatrullaje() {
        return sHorarioPatrullaje;
    }

    public void setsHorarioPatrullaje(String sHorarioPatrullaje) {
        this.sHorarioPatrullaje = sHorarioPatrullaje;
    }

    public String getsAccionesMunicipio() {
        return sAccionesMunicipio;
    }

    public void setsAccionesMunicipio(String sAccionesMunicipio) {
        this.sAccionesMunicipio = sAccionesMunicipio;
    }

    public String getsTramitePendiente() {
        return sTramitePendiente;
    }

    public void setsTramitePendiente(String sTramitePendiente) {
        this.sTramitePendiente = sTramitePendiente;
    }

    public String getsTramitePendienteComentario() {
        return sTramitePendienteComentario;
    }

    public void setsTramitePendienteComentario(String sTramitePendienteComentario) {
        this.sTramitePendienteComentario = sTramitePendienteComentario;
    }

    public String getsGobiernoMarcosAguilar() {
        return sGobiernoMarcosAguilar;
    }

    public void setsGobiernoMarcosAguilar(String sGobiernoMarcosAguilar) {
        this.sGobiernoMarcosAguilar = sGobiernoMarcosAguilar;
    }

    public String getsEsperaMarcosAguilar() {
        return sEsperaMarcosAguilar;
    }

    public void setsEsperaMarcosAguilar(String sEsperaMarcosAguilar) {
        this.sEsperaMarcosAguilar = sEsperaMarcosAguilar;
    }

    public String getsPracticaDeportes() {
        return sPracticaDeportes;
    }

    public void setsPracticaDeportes(String sPracticaDeportes) {
        this.sPracticaDeportes = sPracticaDeportes;
    }

    public String getsDeporte() {
        return sDeporte;
    }

    public void setsDeporte(String sDeporte) {
        this.sDeporte = sDeporte;
    }

    public String getsNecesidadColonia() {
        return sNecesidadColonia;
    }

    public void setsNecesidadColonia(String sNecesidadColonia) {
        this.sNecesidadColonia = sNecesidadColonia;
    }

    public String getoOpinionRealizarObraNombre() {
        return oOpinionRealizarObraNombre;
    }

    public void setoOpinionRealizarObraNombre(String oOpinionRealizarObraNombre) {
        this.oOpinionRealizarObraNombre = oOpinionRealizarObraNombre;
    }

    public String getoOpinionRealizaObra() {
        return oOpinionRealizaObra;
    }

    public void setoOpinionRealizaObra(String oOpinionRealizaObra) {
        this.oOpinionRealizaObra = oOpinionRealizaObra;
    }

    public String getoHorarioMayorRiesgo() {
        return oHorarioMayorRiesgo;
    }

    public void setoHorarioMayorRiesgo(String oHorarioMayorRiesgo) {
        this.oHorarioMayorRiesgo = oHorarioMayorRiesgo;
    }

    public String getoRiesgosFraccionamiento() {
        return oRiesgosFraccionamiento;
    }

    public void setoRiesgosFraccionamiento(String oRiesgosFraccionamiento) {
        this.oRiesgosFraccionamiento = oRiesgosFraccionamiento;
    }

    public String getbHayLotesBaldios() {
        return bHayLotesBaldios;
    }

    public void setbHayLotesBaldios(String bHayLotesBaldios) {
        this.bHayLotesBaldios = bHayLotesBaldios;
    }

    public String getbConsideraRiesgos() {
        return bConsideraRiesgos;
    }

    public void setbConsideraRiesgos(String bConsideraRiesgos) {
        this.bConsideraRiesgos = bConsideraRiesgos;
    }

    public String getbDebenAtenderse() {
        return bDebenAtenderse;
    }

    public void setbDebenAtenderse(String bDebenAtenderse) {
        this.bDebenAtenderse = bDebenAtenderse;
    }

    public String getbRealizadoLimpieza() {
        return bRealizadoLimpieza;
    }

    public void setbRealizadoLimpieza(String bRealizadoLimpieza) {
        this.bRealizadoLimpieza = bRealizadoLimpieza;
    }

    public String getbDesmalezadoLotes() {
        return bDesmalezadoLotes;
    }

    public void setbDesmalezadoLotes(String bDesmalezadoLotes) {
        this.bDesmalezadoLotes = bDesmalezadoLotes;
    }

    public String getaAparienciaAlameda() {
        return aAparienciaAlameda;
    }

    public void setaAparienciaAlameda(String aAparienciaAlameda) {
        this.aAparienciaAlameda = aAparienciaAlameda;
    }

    public String getaSeguroTransitar() {
        return aSeguroTransitar;
    }

    public void setaSeguroTransitar(String aSeguroTransitar) {
        this.aSeguroTransitar = aSeguroTransitar;
    }

    public String getaAlamedaRecreo() {
        return aAlamedaRecreo;
    }

    public void setaAlamedaRecreo(String aAlamedaRecreo) {
        this.aAlamedaRecreo = aAlamedaRecreo;
    }

    public String getaProblemaAlameda() {
        return aProblemaAlameda;
    }

    public void setaProblemaAlameda(String aProblemaAlameda) {
        this.aProblemaAlameda = aProblemaAlameda;
    }

    public String getaMejorarImagen() {
        return aMejorarImagen;
    }

    public void setaMejorarImagen(String aMejorarImagen) {
        this.aMejorarImagen = aMejorarImagen;
    }

    public String getaLugarEncuesta() {
        return aLugarEncuesta;
    }

    public void setaLugarEncuesta(String aLugarEncuesta) {
        this.aLugarEncuesta = aLugarEncuesta;
    }

    public String getFecha() {
        Calendar c = new GregorianCalendar();
        return c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getReferenciaMovil() {
        Calendar c = new GregorianCalendar();
        Random rnd = new Random();
        int random = (int) (rnd.nextDouble() * 9000 + 1000);
        return random+""+c.get(Calendar.DAY_OF_MONTH)+""+c.get(Calendar.MONTH)+""+c.get(Calendar.YEAR);
    }
}
