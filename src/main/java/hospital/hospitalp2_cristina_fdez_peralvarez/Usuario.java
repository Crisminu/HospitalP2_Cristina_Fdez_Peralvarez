package hospital.hospitalp2_cristina_fdez_peralvarez;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
    private String nombre, numeroPersonal, cargo, fechaAlta, fechaBaja;



    public Usuario(String nombre, String numeroPersonal, String cargo, String fechaAlta, String fechaBaja) {
        this.nombre = nombre;
        this.numeroPersonal = numeroPersonal;
        this.cargo = cargo;
        fechaAlta = fechaActual();
        this.fechaAlta = fechaAlta;
        fechaBaja = "31/12/2400";
        this.fechaBaja = fechaBaja;
    }

    public String getNombre() {
        return nombre;
    }


    public String getNumeroPersonal() {
        return numeroPersonal;
    }


    public String getCargo() {
        return cargo;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public String fechaActual(){
        String fecha = null;
        LocalDate date = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        fecha = dtf.format(date);
        return fecha;
    }
}


