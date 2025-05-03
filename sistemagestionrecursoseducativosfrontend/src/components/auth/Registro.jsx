import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ServicioAuthUser from '../service/ServicioAuthUser';
/**
 * Este componente gestiona el proceso de registro de un nuevo usuario en la aplicación.
 * Permite a los administradores registrar a nuevos usuarios con su información personal.
 *
 */
function Registro() {
    const navigate = useNavigate();
    //Estado que almacena los datos del formulario de registro.

    const [formData, setFormData] = useState({
        nombre: '',
        apellidos: '',
        correo: '',
        password: '',
        dni: '',
        rol: '',
        celular: '',
        direccion: '',
        nacimiento: ''
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Call the register method from UserService

            const token = localStorage.getItem('token');
            await ServicioAuthUser.register(formData, token);

            // Clear the form fields after successful registration
            setFormData({
                nombre: '',
                apellidos: '',
                correo: '',
                password: '',
                dni: '',
                rol: '',
                celular: '',
                direccion: '',
                nacimiento: ''
            });
            alert('Usuario registrado exitosamente');
            navigate('/admin/user-management');

        } catch (error) {
            console.error('Error al registrar el usuario:', error);
            alert('Ocurrió un error al registrar al usuario');
        }
    };

    return (
        <div className="auth-container">
<br /> <br /> <br /> <br /> <br /> <br />
<br /> <br /> <br /> <br /> <br /> <br /> <br />
<br />
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value={formData.nombre} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Apellido:</label>
                    <input type="text" name="apellidos" value={formData.apellidos} onChange={handleInputChange} required />
                </div>

                <div className="form-group">
                    <label>Correo:</label>
                    <input type="email" name="correo" value={formData.correo} onChange={handleInputChange} required />
                </div>
                
                <div className="form-group">
                    <label>DNI:</label>
                    <input type="text" name="dni" value={formData.dni} onChange={handleInputChange} required />
                </div>

                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" name="password" value={formData.password} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <label>Rol:</label>
                    <input type="text" name="rol" value={formData.rol} onChange={handleInputChange} placeholder="Ingresar rol " required />
                </div>
                <div className="form-group">
                    <label>Fecha de nacimiento:</label>
                    <input type="text" name="nacimiento" value={formData.nacimiento} onChange={handleInputChange} placeholder="AAAA-MM-DD" required />
                </div>
                <div className="form-group">
                    <label>Dirección:</label>
                    <input type="text" name="direccion" value={formData.direccion} onChange={handleInputChange} placeholder="Ingresar direccion" required />
                </div>
                <div className="form-group">
                    <label>Celular:</label>
                    <input type="text" name="celular" value={formData.celular} onChange={handleInputChange} placeholder="Ingresar número " required />
                </div>
                <button type="submit">Registrar</button>
            </form>
        </div>
    );
}

export default Registro;