
import React, { useState, useEffect } from 'react';
import ServicioAuthUser from '../service/ServicioAuthUser';
import { Link } from 'react-router-dom';


/**
 * Componente que muestra la información del perfil del usuario
 */

function Perfil() {
    const [profileInfo, setProfileInfo] = useState({});

    useEffect(() => {
        fetchProfileInfo();
    }, []);

    const fetchProfileInfo = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await ServicioAuthUser.getYourProfile(token);
            console.log("Respuesta de la información del perfil", response); 
    
            setProfileInfo(response.usuario); 
        } catch (error) {
            console.error('Error al obtener la información del perfil:', error);
        }
    };
    
    

    return (
        <div className="profile-page-container">
            <h2>Información del perfil</h2>



            <p> <b> Nombre: </b> {profileInfo.nombre}</p>
            <br />
            <p> <b> Apellidos:</b> {profileInfo.apellidos}</p>
            <br />
            <p> <b>Correo: </b> {profileInfo.correo}</p>
            <br />
            <p> <b> DNI: </b>{profileInfo.dni}</p>
            <br />
            <p> <b> Celular:</b>{profileInfo.celular}</p>
            <br />
            <p> <b> Dirección:</b> {profileInfo.direccion}</p>
            <br />
            <p> <b>Fecha de nacimiento: </b> {profileInfo.nacimiento}</p>


            {profileInfo.rol === "ADMIN" && (
                <button><Link to={`/update-user/${profileInfo.id}`}>Actualizar Perfil </Link></button>
            )}
        </div>
    );
}

export default Perfil;

