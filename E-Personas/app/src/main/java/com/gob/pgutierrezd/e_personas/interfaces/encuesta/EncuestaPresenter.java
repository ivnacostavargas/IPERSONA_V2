package com.gob.pgutierrezd.e_personas.interfaces.encuesta;

import com.gob.pgutierrezd.e_personas.models.AnswersInterview;
import com.gob.pgutierrezd.e_personas.models.CoordsInterview;
import com.gob.pgutierrezd.e_personas.models.InformationComplement;

/**
 * Created by pgutierrezd on 18/10/2016.
 */
public interface EncuestaPresenter {

    void validateInterview(AnswersInterview answersInterview,InformationComplement informationComplement,boolean bandera);
}
