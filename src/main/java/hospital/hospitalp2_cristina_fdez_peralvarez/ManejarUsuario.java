package hospital.hospitalp2_cristina_fdez_peralvarez;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class ManejarUsuario {
    //lo que maneja el crud
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    //cuando lo instancie crearé la ArrayList
    static List<Usuario> usuarios;
    static List<Usuario> usuariosLoggeados;


    public ManejarUsuario() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/hospital/hospitalp2_cristina_fdez_peralvarez/hospital.db");
            usuarios = rellenarListUsuarios();
            usuariosLoggeados = new ArrayList<>();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Usuario> getUsuariosLoggeados() {
        return usuariosLoggeados;
    }
    public void crearUsuario(Usuario usuario) throws SQLException {
        stmt = conn.createStatement();
        String sql = "INSERT INTO USUARIO VALUES(";
        sql = sql + "'" + usuario.getNombre() + "'," + usuario.getNumeroPersonal() + ",'" + usuario.getCargo() + "'," + usuario.getFechaAlta() + "," + usuario.getFechaBaja() +
     ");";
        stmt.executeUpdate(sql);
        usuarios.add(usuario);
    }
    public ArrayList<Usuario> rellenarListUsuarios() throws SQLException {
        String sql = "SELECT * FROM USUARIO";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        ArrayList <Usuario> listaRellenar = new ArrayList<>();

        while(rs.next()){
            String Nombre = rs.getString("NOMBRE");
            String NumPers = valueOf(rs.getInt("NUMPERS"));
            String Cargo = rs.getString("TIPO");
            String fechaAlta = valueOf(rs.getDate("FECHAALTA"));
            String fechaBaja = valueOf(rs.getDate("FECHABAJA"));
            listaRellenar.add(new Usuario(Nombre,NumPers,Cargo,fechaAlta,fechaBaja));
        }
        return listaRellenar;
    }
    public static Usuario login (int num) throws SQLException {
        String sql = "SELECT * FROM USUARIO WHERE NUMPERS = '"+num+"';";
        stmt = conn.createStatement();
        stmt.executeQuery(sql);
        ResultSet rs = stmt.executeQuery(sql);
        Usuario usuarioLog = null;


        if(rs.next()){
            //Coge cada uno de los valore del resultset
            String usuarioLogNombre = rs.getString("NOMBRE");
            String usuarioLogNum = valueOf(rs.getInt("NUMPERS"));
            String usuarioLogCargo = rs.getString("TIPO");
            String usuarioLogfechaAlta = valueOf(rs.getDate("FECHAALTA"));
            String usuarioLogfechaBaja = valueOf(rs.getDate("FECHABAJA"));
            usuarioLog = new Usuario(usuarioLogNombre,usuarioLogNum,usuarioLogCargo, usuarioLogfechaAlta, usuarioLogfechaBaja);
            usuariosLoggeados.add(usuarioLog);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("El usuario no existe");
            alert.show();
        }
        return usuarioLog;
    }
    public int estaLogeado(Usuario usuario){
        int n = -1;

        for(int i=0;i<usuariosLoggeados.size();i++) {
            String comprobar = usuariosLoggeados.get(i).getNumeroPersonal();
            if(comprobar.equals(usuario.getNumeroPersonal())){
                n=i;
            }
        }
        return n;
    }
    void modificarUsuario(Usuario usuario) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/hospital/hospitalp2_cristina_fdez_peralvarez/hospital.db");
        stmt = conn.createStatement();
        String nombre = usuario.getNombre();
        String cargo = usuario.getCargo();
        String sql = "UPDATE USUARIO SET NOMBRE = '" + nombre + "',TIPO = '" + cargo + "' WHERE NUMPERS="+ usuario.getNumeroPersonal() + ";";
        stmt.executeQuery(sql);
    }
    public void recargarListaUsuarios() throws SQLException {
        usuarios = rellenarListUsuarios();
    }
   /* void darDeBaja(Usuario usuario) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/hospital/hospitalp2_cristina_fdez_peralvarez/hospital.db");
        stmt = conn.createStatement();
        String fechaBaja = usuario.getFechaBaja();
        String sql = "UPDATE USUARIO SET FECHABAJA = '" + fechaBaja + "' WHERE NUMPERS="+ usuario.getNumeroPersonal() + ";";
        stmt.executeQuery(sql);
    }*/

}
