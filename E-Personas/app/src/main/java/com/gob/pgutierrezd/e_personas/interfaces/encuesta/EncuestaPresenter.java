package com.gob.pgutierrezd.e_personas.interfaces.encuesta;

import com.gob.pgutierrezd.e_personas.models.AnswersInterview;
import com.gob.pgutierrezd.e_personas.models.CoordsInterview;

/**
 * Created by pgutierrezd on 18/10/2016.
 */
public interface EncuestaPresenter {

    void validateCoords(CoordsInterview coordsInterview);
    void validateInterview(AnswersInterview answersInterview);

}
