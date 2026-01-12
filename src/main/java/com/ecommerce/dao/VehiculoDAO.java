package com.ecommerce.dao;

import com.ecommerce.config.DBConnection;
import com.ecommerce.models.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public VehiculoDAO() {
    }

    public List<Vehiculo> listarVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        // LOGICA PROFESIONAL: Solo mostramos los NO eliminados (eliminado = 0)
        String sql = "SELECT * FROM vehiculos WHERE eliminado = 0 ORDER BY id DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                vehiculos.add(mapResultSetToVehiculo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listing vehicles: " + e.getMessage());
            e.printStackTrace();
        }
        return vehiculos;
    }

    public boolean insertarVehiculo(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculos (marca, modelo, anio, precio, color, tipo, estado, imagen_url, descripcion, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAnio());
            pstmt.setBigDecimal(4, vehiculo.getPrecio());
            pstmt.setString(5, vehiculo.getColor());
            pstmt.setString(6, vehiculo.getTipo());
            pstmt.setString(7, vehiculo.getEstado());
            pstmt.setString(8, vehiculo.getImagenUrl());
            pstmt.setString(9, vehiculo.getDescripcion());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting vehicle: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Vehiculo obtenerPorId(int id) {
        String sql = "SELECT * FROM vehiculos WHERE id = ? AND eliminado = 0";
        Vehiculo vehiculo = null;

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    vehiculo = mapResultSetToVehiculo(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding vehicle by ID: " + e.getMessage());
        }
        return vehiculo;
    }

    public boolean actualizarVehiculo(Vehiculo vehiculo) {
        String sql = "UPDATE vehiculos SET marca=?, modelo=?, anio=?, precio=?, color=?, tipo=?, estado=?, imagen_url=?, descripcion=? WHERE id=?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAnio());
            pstmt.setBigDecimal(4, vehiculo.getPrecio());
            pstmt.setString(5, vehiculo.getColor());
            pstmt.setString(6, vehiculo.getTipo());
            pstmt.setString(7, vehiculo.getEstado());
            pstmt.setString(8, vehiculo.getImagenUrl());
            pstmt.setString(9, vehiculo.getDescripcion());
            pstmt.setInt(10, vehiculo.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    // --- BORRADO LÓGICO (SOFT DELETE) ---
    public boolean eliminarVehiculo(int id) {
        // En lugar de DELETE, hacemos UPDATE para marcarlo como eliminado
        String sql = "UPDATE vehiculos SET eliminado = 1 WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error soft-deleting vehicle: " + e.getMessage());
            return false;
        }
    }

    private Vehiculo mapResultSetToVehiculo(ResultSet rs) throws SQLException {
        // Asegúrate de que tu tabla tenga la columna 'eliminado', si no, esto fallará.
        boolean eliminadoValue = false;
        try {
            eliminadoValue = rs.getBoolean("eliminado");
        } catch (SQLException e) {
            // Si la columna no existe aún, asumimos false
        }

        return new Vehiculo(
            rs.getInt("id"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getInt("anio"),
            rs.getBigDecimal("precio"),
            rs.getString("color"),
            rs.getString("tipo"),
            rs.getString("estado"),
            rs.getString("imagen_url"),
            rs.getString("descripcion"),
            rs.getTimestamp("fecha_registro"),
            eliminadoValue
        );
    }
}