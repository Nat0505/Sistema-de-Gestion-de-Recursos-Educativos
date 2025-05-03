import axios from "axios";
/**
 * Esta clase maneja todas las operaciones relacionadas con la autenticación de usuarios
 * y la gestión de usuarios en el sistema (iniciar sesión, registro, obtener perfil, 
 * gestion de usuarios, etc.)
 */
class ServicioAuthUser{
    static BASE_URL = "http://localhost:1010"

    static async login(correo, password){
        try{
            const response = await axios.post(`${ServicioAuthUser.BASE_URL}/auth/login`, {correo, password})
            return response.data;

        }catch(err){
            throw err;
        }
    }

    static async register(userData, token){
        try{
            const response = await axios.post(`${ServicioAuthUser.BASE_URL}/auth/register`, userData, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static async getAllUsers(token){
        try{
            const response = await axios.get(`${ServicioAuthUser.BASE_URL}/admin/get-all-users`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }


    static async getYourProfile(token){
        try{
            const response = await axios.get(`${ServicioAuthUser.BASE_URL}/adminestudiantedocente/get-profile`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static async getUserById(userId, token){
        try{
            const response = await axios.get(`${ServicioAuthUser.BASE_URL}/admin/get-user/${userId}`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static async deleteUser(userId, token){
        try{
            const response = await axios.delete(`${ServicioAuthUser.BASE_URL}/admin/delete-user/${userId}`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }


    static async updateUser(userId, userData, token){
        try{
            const response = await axios.put(`${ServicioAuthUser.BASE_URL}/admin/update-user/${userId}`, userData,
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static logout(){
        localStorage.removeItem('token')
        localStorage.removeItem('rol')
    }

    static isAuthenticated(){
        const token = localStorage.getItem('token')
        return !!token
    }

    static isAdmin(){
        const rol = localStorage.getItem('rol')
        return rol === 'ADMIN';
    }

    static isUser(){
        const rol = localStorage.getItem('rol')
        return rol === 'ESTUDIANTE';
    }
    
    static isTeacher(){
        const rol = localStorage.getItem('rol');
        return rol === 'DOCENTE';
    }

    static adminOnly(){
        return this.isAuthenticated() && this.isAdmin();
    }

}

export default ServicioAuthUser;