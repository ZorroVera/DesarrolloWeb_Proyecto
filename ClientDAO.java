package com.turismo.dao;

import com.turismo.dto.Client;
import com.turismo.util.DataAccess;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private final Connection cnn;

    public ClientDAO(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void cerrarConexion() {
        try {
            if (cnn != null && !cnn.isClosed()) {
                cnn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Create
    public void addClient(Client client) throws SQLException {
        String sql = "INSERT INTO Cliente (Nombres, ApellidoPaterno, ApellidoMaterno, CorreoElectronico, Ciudad, Direccion, Telefono1, Telefono2, FechaRegistro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
            stmt.setString(1, client.getNombres());
            stmt.setString(2, client.getApellidoPaterno());
            stmt.setString(3, client.getApellidoMaterno());
            stmt.setString(4, client.getCorreoElectronico());
            stmt.setString(5, client.getCiudad());
            stmt.setString(6, client.getDireccion());
            stmt.setString(7, client.getTelefono1());
            stmt.setString(8, client.getTelefono2());
            stmt.setDate(9, new java.sql.Date(client.getFechaRegistro().getTime()));
            stmt.executeUpdate();
        }
    }

    // Read
    public Client getClientById(int clientId) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE ClienteID = ?";
        try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getInt("ClienteID"),
                            rs.getString("Nombres"),
                            rs.getString("ApellidoPaterno"),
                            rs.getString("ApellidoMaterno"),
                            rs.getString("CorreoElectronico"),
                            rs.getString("Ciudad"),
                            rs.getString("Direccion"),
                            rs.getString("Telefono1"),
                            rs.getString("Telefono2"),
                            rs.getDate("FechaRegistro")
                    );
                }
            }
        }
        return null;
    }

    // Update
    public void updateClient(Client client) throws SQLException {
        String sql = "UPDATE Cliente SET Nombres = ?, ApellidoPaterno = ?, ApellidoMaterno = ?, CorreoElectronico = ?, Ciudad = ?, Direccion = ?, Telefono1 = ?, Telefono2 = ? WHERE ClienteID = ?";
        try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
            stmt.setString(1, client.getNombres());
            stmt.setString(2, client.getApellidoPaterno());
            stmt.setString(3, client.getApellidoMaterno());
            stmt.setString(4, client.getCorreoElectronico());
            stmt.setString(5, client.getCiudad());
            stmt.setString(6, client.getDireccion());
            stmt.setString(7, client.getTelefono1());
            stmt.setString(8, client.getTelefono2());
            stmt.setInt(9, client.getClientId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void deleteClient(int clientId) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE ClienteID = ?";
        try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.executeUpdate();
        }
    }

    // List all clients
    public List<Client> getAllClients() throws SQLException {
        String sql = "SELECT * FROM Cliente";
        List<Client> clients = new ArrayList<>();
        try (Statement stmt = cnn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("ClienteID"),
                        rs.getString("Nombres"),
                        rs.getString("ApellidoPaterno"),
                        rs.getString("ApellidoMaterno"),
                        rs.getString("CorreoElectronico"),
                        rs.getString("Ciudad"),
                        rs.getString("Direccion"),
                        rs.getString("Telefono1"),
                        rs.getString("Telefono2"),
                        rs.getDate("FechaRegistro")
                ));
            }
        }
        return clients;
    }
}
