package com.gob.pgutierrezd.e_personas.interfaces.encuesta;

import android.content.Context;

import com.gob.pgutierrezd.e_personas.models.AnswersInterview;
import com.gob.pgutierrezd.e_personas.models.InformationComplement;

/**
 * Created by pgutierrezd on 18/10/2016.
 */
public interface EncuestaInteractor {

    interface OnInterviewFinishedListener {
        void navigateToHome();
        void errorConnectionServer();
        void errorSendInterview();
    }

    void sendInterview(AnswersInterview answersInterview,InformationComplement informationComplement, boolean bandera, final Context context, final OnInterviewFinishedListener listener);
}
