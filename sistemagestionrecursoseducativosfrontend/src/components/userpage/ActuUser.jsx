import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ServicioAuthUser from '../service/ServicioAuthUser';
/**
 * Este componente permite que los administradores puedan actualizar la información de un usuario.
 *
 */
function ActuUser() {
  const navigate = useNavigate();
  const { userId } = useParams();


  const [userData, setUserData] = useState({
    nombre: '',
    apellidos: '',
    correo: '',
    dni: '',
    rol: '',
    celular: '',
    direccion: '',
    nacimiento: ''
  });

  useEffect(() => {
    fetchUserDataById(userId); 
  }, [userId]); 



  const fetchUserDataById = async (userId) => {
    try {
      const token = localStorage.getItem('token');
      const response = await ServicioAuthUser.getUserById(userId, token); 
      const { nombre, apellidos, correo, dni, rol, celular, direccion, nacimiento } = response.usuario;
      setUserData({ nombre, apellidos, correo, dni, rol, celular, direccion, nacimiento});
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevUserData) => ({
      ...prevUserData,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const confirmDelete = window.confirm('¿Estás seguro de que quieres actualizar a este usuario?');
      if (confirmDelete) {
        const token = localStorage.getItem('token');
        const res = await ServicioAuthUser.updateUser(userId, userData, token);
        console.log(res)
        navigate("/admin/user-management")
      }

    } catch (error) {
      console.error('Error al actualizar el perfil del usuario:', error);
      alert(error)
    }
  };

  return (
    <div className="auth-container">

        <br />  <br /><br /><br /><br /><br /><br /><br /><br />
      <h2>Actualización de usuario </h2>
      <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value={userData.nombre} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Apellidos:</label>
                    <input type="text" name="apellidos" value={userData.apellidos} onChange={handleInputChange} required />
                </div>

                <div className="form-group">
                    <label>Correo:</label>
                    <input type="email" name="correo" value={userData.correo} onChange={handleInputChange} required />
                </div>
                
                <div className="form-group">
                    <label>DNI::</label>
                    <input type="text" name="dni" value={userData.dni} onChange={handleInputChange} required />
                </div>


                <div className="form-group">
                    <label>Rol:</label>
                    <input type="text" name="rol" value={userData.rol} onChange={handleInputChange} placeholder="Ingresar rol " required />
                </div>
                <div className="form-group">
                    <label>Fecha de nacimiento:</label>
                    <input type="text" name="nacimiento" value={userData.nacimiento} onChange={handleInputChange} placeholder="AAAA-MM-DD"  required />
                </div>
                <div className="form-group">
                    <label>Dirección:</label>
                    <input type="text" name="direccion" value={userData.direccion} onChange={handleInputChange} placeholder="Ingresar direccion" required />
                </div>
                <div className="form-group">
                    <label>Celular:</label>
                    <input type="text" name="celular" value={userData.celular} onChange={handleInputChange} placeholder="Ingresar número" required />
                </div>
        <button type="submit">Actualizar</button>
      </form>
    </div>
  );
}

export default ActuUser;
