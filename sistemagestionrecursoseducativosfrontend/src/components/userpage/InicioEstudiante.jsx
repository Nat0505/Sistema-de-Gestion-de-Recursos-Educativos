import React, { useEffect, useState } from 'react';
import ServicioAuthUser from '../service/ServicioAuthUser';
import "../../Styles/Inicio.css";
 /**
 * Componente que representa la página de inicio para los estudiantes
 *  
 */
function InicioEstudiante() {
    const [profileInfo, setProfileInfo] = useState({});

    useEffect(() => {
        const fetchProfileInfo = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await ServicioAuthUser.getYourProfile(token);
                setProfileInfo(response.usuario);
            } catch (error) {
                console.error('Error al obtener datos del estudiante:', error);
            }
        };

        fetchProfileInfo();
    }, []);

    return (
        <div className='section-home'>
            <div className='section-home-greeting'>
            <p>Bienvenido(a), {profileInfo.nombre}</p>
            <br />
            <h3>Cursos</h3>
            <br />
            <hr />
            </div>
            <div className='cursos-conteiner'>
            <div className="card">
                <img src="https://class.utp.edu.pe/static/media/courseP1.6604957d.png" alt="" />
                <div className="card-content">
                    <h4>Desarrollo Web Integrado</h4>
                    <br />
                    <p>Dante Vázquez Lira</p>
                </div>
            </div>
            <div className="card">
                <img src="https://class.utp.edu.pe/static/media/courseP1.6604957d.png" alt="" />
                <div className="card-content">
                    <h4> Administracion de empresas</h4>
                    <br />
                    <p>Dante Vázquez Lira</p>
                </div>
            </div>
            <div className="card">
                <img src="https://class.utp.edu.pe/static/media/course24%E2%81%847.c43c482e.png" alt="" />
                <div className="card-content">
                    <h4>Teoria de sistemas</h4>
                    <br />
                    <p>Dante Vázquez Lira</p>
                </div>
            </div>
            <div className="card">
                <img src="https://class.utp.edu.pe/static/media/courseP1.6604957d.png" alt="" />
                <div className="card-content">
                    <h4>Desarrollo Web Integrado</h4>
                    <br />
                    <p>Dante Vázquez Lira</p>
                </div>
            </div>
            <div className="card">
                <img src="https://class.utp.edu.pe/static/media/courseP1.6604957d.png" alt="" />
                <div className="card-content">
                    <h4> Taller de programación</h4>
                    <br />
                    <p>Dante Vázquez Lira</p>
                </div>
            </div>
            <div className="card">
                <img src="https://class.utp.edu.pe/static/media/course24%E2%81%847.c43c482e.png" alt="" />
                <div className="card-content">
                    <h4>Base de datos </h4>
                    <br />
                    <p>Dante Vázquez Lira</p>
                </div>
            </div>
            <div className="card">
                <img src="https://class.utp.edu.pe/static/media/course24%E2%81%847.c43c482e.png" alt="" />
                <div className="card-content">
                    <h4>Desarrollo Web Integrado</h4>
                    <br />
                    <p>Dante Vázquez Lira</p>
                </div>
            </div>

            </div>
        </div>
    );
}

export default InicioEstudiante;

