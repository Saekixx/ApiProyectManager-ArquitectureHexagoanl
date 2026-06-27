package com.api.proyectmanager.shared.adapters.http;

public class Response<T> {
    private Boolean success;
    private String mensaje;
    private T data;

    public Response(Boolean success, String mensaje, T data) {
        this.success = success;
        this.mensaje = mensaje;
        this.data = data;
    }

    public Response(Boolean success, String mensaje) {
        this.success = success;
        this.mensaje = mensaje;
        this.data = null;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
