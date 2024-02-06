import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class acceso extends JFrame{
    private JPanel panelAcceso;
    private JButton verificarButton;
    private JTextField usuarioText;
    private JPasswordField contrasenaText;

    public acceso() {
        super("Acceso");
        setContentPane(panelAcceso);
        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexion();
            }
        });
    }

    void conexion(){
        String url = "jdbc:mysql://localhost:3306/capacitacion";
        String usuarioDB = "root";
        String contreasenaDB = "";

        try(Connection conexion = DriverManager.getConnection(url, usuarioDB, contreasenaDB)){
            JOptionPane.showMessageDialog(acceso.this, "Conexión exitosa");
            String usuario = usuarioText.getText();
            String contrasena = contrasenaText.getText();
            insertarDatos(conexion, usuario, null, null, contrasena, null);


        } catch (SQLException e){
            JOptionPane.showMessageDialog(acceso.this, "Conexión no exitosa \n" + e);
        }
    }
    void insertarDatos(Connection conexion, String nombre, String codigo, String cedula, String contrasena, String rol){
        String query = "INSERT INTO usuarios (nombre, codigo, cedula, contraseña, rol) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, codigo);
            preparedStatement.setString(3, cedula);
            preparedStatement.setString(4, contrasena);
            preparedStatement.setString(5, rol);
            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas>0){
                JOptionPane.showMessageDialog(acceso.this, "Datos insertados correctamentes");
            } else {
                JOptionPane.showMessageDialog(acceso.this, "No se han insertado datos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(acceso.this, "Error al ejecutar la consulta SQL \n "+ e);
        }
    }

    void abrirAcceso(){
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
