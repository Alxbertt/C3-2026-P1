package biblioteca.vista;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * DocumentListener que ejecuta una acción ante cualquier cambio del texto
 * (sirve para filtrar mientras el usuario escribe).
 */
final class CambioTexto implements DocumentListener {
    private final Runnable accion;

    CambioTexto(Runnable accion) {
        this.accion = accion;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        accion.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        accion.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        accion.run();
    }
}
