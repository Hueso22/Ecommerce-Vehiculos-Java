<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Concesionario Elite | Inventario</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,400,1,0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<div class="container">
    <header>
        <div class="logo">
            <h1>AutoManager</h1>
        </div>
        <div class="user-info"><span>Admin</span></div>
    </header>

    <div class="card">
        <h2 style="margin-bottom: 20px; color: var(--md-sys-color-primary); font-size: 1.3rem;">
            ${vehiculoEditar != null ? 'Editar Vehículo' : 'Nuevo Ingreso'}
        </h2>
        
        <form action="${pageContext.request.contextPath}/vehiculos" method="post">
            <input type="hidden" name="id" value="${vehiculoEditar != null ? vehiculoEditar.id : ''}">

            <div class="form-grid">
                <input type="text" name="marca" placeholder="Marca (ej. Toyota)" value="${vehiculoEditar != null ? vehiculoEditar.marca : ''}" required>
                <input type="text" name="modelo" placeholder="Modelo" value="${vehiculoEditar != null ? vehiculoEditar.modelo : ''}" required>
                <input type="number" name="anio" placeholder="Año" value="${vehiculoEditar != null ? vehiculoEditar.anio : ''}" required>
                <input type="number" name="precio" placeholder="Precio ($)" step="0.01" value="${vehiculoEditar != null ? vehiculoEditar.precio : ''}" required>
                <input type="text" name="color" placeholder="Color" value="${vehiculoEditar != null ? vehiculoEditar.color : ''}">
                
                <select name="tipo">
                    <option value="Sedan" ${vehiculoEditar != null && vehiculoEditar.tipo == 'Sedan' ? 'selected' : ''}>Sedan</option>
                    <option value="SUV" ${vehiculoEditar != null && vehiculoEditar.tipo == 'SUV' ? 'selected' : ''}>SUV</option>
                    <option value="Pick-up" ${vehiculoEditar != null && vehiculoEditar.tipo == 'Pick-up' ? 'selected' : ''}>Pick-up</option>
                    <option value="Hatchback" ${vehiculoEditar != null && vehiculoEditar.tipo == 'Hatchback' ? 'selected' : ''}>Hatchback</option>
                </select>

                <select name="estado">
                    <option value="NUEVO" ${vehiculoEditar != null && vehiculoEditar.estado == 'NUEVO' ? 'selected' : ''}>NUEVO</option>
                    <option value="USADO" ${vehiculoEditar != null && vehiculoEditar.estado == 'USADO' ? 'selected' : ''}>USADO</option>
                </select>
                
                <input type="text" name="imagenUrl" placeholder="URL Imagen (Dejar vacío para auto-generar)" value="${vehiculoEditar != null ? vehiculoEditar.imagenUrl : ''}">
            </div>
            
            <textarea name="descripcion" placeholder="Descripción breve..." style="height: 80px; margin-bottom: 20px;">${vehiculoEditar != null ? vehiculoEditar.descripcion : ''}</textarea>

            <div style="display: flex; gap: 10px;">
                <button type="submit" class="btn btn-primary">
                    ${vehiculoEditar != null ? 'Guardar Cambios' : 'Registrar Vehículo'}
                </button>
                
                <c:if test="${vehiculoEditar != null}">
                    <a href="${pageContext.request.contextPath}/vehiculos" class="btn btn-secondary">Cancelar</a>
                </c:if>
            </div>
        </form>
    </div>

    <div class="vehicle-grid">
        <c:forEach var="v" items="${vehiculos}">
            <div class="vehicle-card">
                <c:choose>
                    <c:when test="${not empty v.imagenUrl}">
                        <img src="${v.imagenUrl}" class="vehicle-img" alt="Foto">
                    </c:when>
                    <c:otherwise>
                        <img src="https://placehold.co/600x400/5D4037/FFF?text=${v.marca}+${v.modelo}&font=roboto" class="vehicle-img" alt="Placeholder">
                    </c:otherwise>
                </c:choose>
                
                <div class="vehicle-info">
                    <div class="vehicle-details">
                        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 8px;">
                            <h3 style="margin:0; font-size:1.1rem; color:#333;">${v.marca} ${v.modelo}</h3>
                            <span class="badge badge-${v.estado.toLowerCase()}">${v.estado}</span>
                        </div>
                        <p style="color:#666; font-size: 0.9rem; margin:0;">${v.anio} • ${v.tipo} • ${v.color}</p>
                        <p style="margin-top: 10px; font-size: 0.95rem; line-height: 1.4; color: #444;">${v.descripcion}</p>
                        <span class="price-tag">$ ${v.precio}</span>
                    </div>

                    <div class="card-actions">
                        <a href="${pageContext.request.contextPath}/vehiculos?action=editar&id=${v.id}" class="btn btn-warning">
                            Editar
                        </a>
                        <button type="button" class="btn btn-danger" onclick="abrirModal('${v.id}')">
                           Eliminar
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<div id="deleteModal" class="modal-backdrop">
    <div class="modal-card">
        <div class="modal-icon-container">
            <span class="material-symbols-rounded modal-icon">warning</span>
        </div>
        
        <h3 class="modal-title">¿Eliminar vehículo?</h3>
        <p class="modal-text">Esta acción es permanente y no se puede deshacer.</p>
        
        <div class="modal-actions">
            <button class="btn btn-warning" onclick="cerrarModal()">Cancelar</button>
            
            <a id="confirmDeleteBtn" href="#" class="btn btn-danger">Eliminar</a>
        </div>
    </div>
</div>

<script>
    const modal = document.getElementById('deleteModal');
    const confirmBtn = document.getElementById('confirmDeleteBtn');
    const baseUrl = "${pageContext.request.contextPath}/vehiculos?action=eliminar&id=";

    function abrirModal(idVehiculo) {
        confirmBtn.href = baseUrl + idVehiculo;
        modal.classList.add('show');
    }

    function cerrarModal() {
        modal.classList.remove('show');
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            cerrarModal();
        }
    }
</script>

</body>
</html>