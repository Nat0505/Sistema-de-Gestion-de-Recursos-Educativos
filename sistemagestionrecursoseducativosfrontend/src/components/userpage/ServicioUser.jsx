
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import ServicioAuthUser from '../service/ServicioAuthUser';
/**
 * Componente para gestionar los usuarios en la plataforma
 */
function ServicioUser() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await ServicioAuthUser.getAllUsers(token);
      console.log('Respuesta del backend:', response);
      setUsers(response.usuarioList); 
    } catch (error) {
      console.error('Error al obtener los usuarios:', error);
    }
  };
  

  const deleteUser = async (userId) => {
    try {
      const confirmDelete = window.confirm('¿Estás seguro de que quieres eliminar a este usuario?');

      const token = localStorage.getItem('token'); 
      if (confirmDelete) {
        await ServicioAuthUser.deleteUser(userId, token);
        fetchUsers();
      }
    } catch (error) {
      console.error('Error al eliminar usuario:', error);
    }
  };

  return (
    <div className="user-management-container">
        <br />
        <br />
        <br />
      <button className='reg-button'> <Link to="/register">Agregar usuario</Link></button>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
  {users && users.length > 0 ? (
    users.map(user => (
      <tr key={user.id}>
        <td>{user.id}</td>
        <td>{user.nombre}</td>
        <td>{user.correo}</td>
        <td>
          <button className='delete-button' onClick={() => deleteUser(user.id)}>Eliminar</button>
          <button><Link to={`/update-user/${user.id}`}>
            Actualizar
          </Link></button>
        </td>
      </tr>
    ))
  ) : (
    <tr>
      <td colSpan="4">No se encontraron usuarios.</td>
    </tr>
  )}
</tbody>

      </table>
    </div>
  );
}

export default ServicioUser;
