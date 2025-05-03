import React from 'react';
import { Link } from 'react-router-dom';
import ServicioAuthUser from '../service/ServicioAuthUser';

/**
 * 
 * Este componente maneja la barra de navegación de la aplicación
 *
 */

function Navbar() {
    const isAuthenticated = ServicioAuthUser.isAuthenticated();
    const isAdmin = ServicioAuthUser.isAdmin();

    /**
     * Funcion para manejar el cierre de sesión
     * 
     */

    const handleLogout = () => {
        const confirmDelete = window.confirm('¿Estás seguro de que deseas cerrar sesión?');
        if (confirmDelete) {
            ServicioAuthUser.logout();
            window.location.href = "/"; 

        }
    };


    return (
        <nav>
            <ul>
                {!isAuthenticated && <li><Link to="/"> Inicio de Sesión</Link></li>}

                {ServicioAuthUser.isUser() && <li><Link to="/home">Inicio</Link></li>}

        {isAuthenticated && ServicioAuthUser.isTeacher() && (
  <li><Link to="/home-docente">Inicio</Link></li>
)}        
                {isAuthenticated && ServicioAuthUser.isTeacher() && (
            <li><Link to="/crear-recurso">Crear Recurso</Link></li>
        )}
        
           

                {isAuthenticated && <li><Link to="/profile">Perfil</Link></li>}
                {isAdmin && <li><Link to="/admin/user-management"> Gestión de Usuarios</Link></li>}
                {isAuthenticated && <li><Link to="/" onClick={handleLogout}>Logout</Link></li>}
            </ul>
        </nav>
    );
}

export default Navbar;
