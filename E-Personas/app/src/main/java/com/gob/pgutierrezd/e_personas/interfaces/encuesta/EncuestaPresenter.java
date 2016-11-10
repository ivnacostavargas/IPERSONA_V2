package com.gob.pgutierrezd.e_personas.interfaces.encuesta;

import com.gob.pgutierrezd.e_personas.models.AnswersInterview;
import com.gob.pgutierrezd.e_personas.models.CoordsInterview;
import com.gob.pgutierrezd.e_personas.models.InformationComplement;

import java.util.List;

/**
 * Created by pgutierrezd on 18/10/2016.
 */
public interface EncuestaPresenter {

    void validateInterview(AnswersInterview answersInterview, InformationComplement informationComplement, List<CoordsInterview> coordsInterviews, boolean bandera);
}
