package com.example.domain.exception;

//@SuppressWarnings("serial") //para evitar el warning de serialización como el de abajo
public class TaskNotFoundException extends RuntimeException {
    //private static final long serialVersionUID = 1L;     en caso de serialización

    // TaskNotFoundException es una regla de negocio y como RuntimeException no necesita nada
    //del framework Spring se puede utilizar en estye contexto (en el dominio).
    public TaskNotFoundException(long id) {
        super("No se encontró la tarea con id: " + id);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

}
