package com.gob.pgutierrezd.e_personas.models;

/**
 * Created by pgutierrezd on 29/10/2016.
 */
public class LoginRegister {

    private String mNombre;
    private String mApellidos;
    private String mTelefono;
    private String mFechaNacimiento;
    private String mCorreo;
    private String mPassword;
    private String mGenero;

    public String getmNombre() {
        return mNombre;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getmApellidos() {
        return mApellidos;
    }

    public void setmApellidos(String mApellidos) {
        this.mApellidos = mApellidos;
    }

    public String getmTelefono() {
        return mTelefono;
    }

    public void setmTelefono(String mTelefono) {
        this.mTelefono = mTelefono;
    }

    public String getmFechaNacimiento() {
        return mFechaNacimiento;
    }

    public void setmFechaNacimiento(String mFechaNacimiento) {
        this.mFechaNacimiento = mFechaNacimiento;
    }

    public String getmCorreo() {
        return mCorreo;
    }

    public void setmCorreo(String mCorreo) {
        this.mCorreo = mCorreo;
    }

    public String getmGenero() {
        return mGenero;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmGenero(String mGenero) {
        if ("male".equals(mGenero)) {
            this.mGenero = "1";
        }else if("female".equals(mGenero)){
            this.mGenero = "2";
        }
    }
}
