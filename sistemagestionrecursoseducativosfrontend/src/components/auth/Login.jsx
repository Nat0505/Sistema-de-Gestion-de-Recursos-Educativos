import React, {useState} from "react";
import { useNavigate } from "react-router-dom";
import ServicioAuthUser from "../service/ServicioAuthUser";
import "../../Styles/Login.css";

function Login(){
// Estados locales para manejar los datos del formulario y los errores

const [correo, setCorreo] = useState('')
const [password, setPassword] = useState('')
const [error, setError] = useState('')
const navigate = useNavigate();


// Función que maneja el evento de envío del formulario

const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Llamada al servicio de autenticación para verificar las credenciales

        const userData = await ServicioAuthUser.login(correo, password)
        console.log(userData)
        if (userData.token) {
            localStorage.setItem('token', userData.token)
            localStorage.setItem('rol', userData.rol)
            
    // Redirección según el rol
    if (userData.rol === 'ADMIN') {
        navigate('/profile');
    } else if (userData.rol === 'ESTUDIANTE') {
        navigate('/home');
    } else if (userData.rol === 'DOCENTE') {
        navigate('/home-docente');
    }
            window.location.reload() 
        }else{
            setError(userData.message)
        }
        
    } catch (error) {
        console.log(error)
        setError(error.message)
        setTimeout(()=>{
            setError('');
        }, 5000);
    }
}


    return(
       <div className="login-container" >
        <div className="auth-container">
            <h2>UTP</h2>
            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Correo: </label>
                    <input type="email" value={correo} onChange={(e) => setCorreo(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>Password: </label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
        </div>
    )

}

export default Login;