package com.ecommerce.controllers;

import com.ecommerce.dao.VehiculoDAO;
import com.ecommerce.models.Vehiculo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/vehiculos")
public class VehiculoController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private VehiculoDAO vehiculoDAO;

    public VehiculoController() {
        super();
        this.vehiculoDAO = new VehiculoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action != null) {
            if (action.equals("eliminar")) {
                eliminarVehiculo(request, response);
                return; // Importante: salir para no volver a listar aquí
            } else if (action.equals("editar")) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Vehiculo vehiculo = vehiculoDAO.obtenerPorId(id);
                    request.setAttribute("vehiculoEditar", vehiculo);
                } catch (Exception e) {
                    System.err.println("Error al obtener vehículo para editar: " + e.getMessage());
                }
            }
        }
        
        listarVehiculos(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMarca(request.getParameter("marca"));
            vehiculo.setModelo(request.getParameter("modelo"));
            vehiculo.setAnio(Integer.parseInt(request.getParameter("anio")));
            vehiculo.setPrecio(new BigDecimal(request.getParameter("precio")));
            vehiculo.setColor(request.getParameter("color"));
            vehiculo.setTipo(request.getParameter("tipo"));
            vehiculo.setEstado(request.getParameter("estado"));
            vehiculo.setImagenUrl(request.getParameter("imagenUrl"));
            vehiculo.setDescripcion(request.getParameter("descripcion"));

            if (idStr != null && !idStr.isEmpty()) {
                vehiculo.setId(Integer.parseInt(idStr));
                vehiculoDAO.actualizarVehiculo(vehiculo);
            } else {
                vehiculoDAO.insertarVehiculo(vehiculo);
            }
            
            response.sendRedirect("vehiculos");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando vehículo");
        }
    }

    private void listarVehiculos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Vehiculo> lista = vehiculoDAO.listarVehiculos();
        request.setAttribute("vehiculos", lista);
        request.getRequestDispatcher("views/lista-vehiculos.jsp").forward(request, response);
    }

    private void eliminarVehiculo(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        vehiculoDAO.eliminarVehiculo(id);
        response.sendRedirect("vehiculos");
    }
}