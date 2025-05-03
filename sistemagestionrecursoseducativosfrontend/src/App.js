
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navbar from './components/common/Navbar';
import LoginPage from './components/auth/Login';
import RegistrationPage from './components/auth/Registro';
import ServicioAuthUser from './components/service/ServicioAuthUser';
import ActuUser from './components/userpage/ActuUser';
import ServicioUser from './components/userpage/ServicioUser';
import ProfilePage from './components/userpage/Perfil';

import InicioEstudiante from './components/userpage/InicioEstudiante'; 
import InicioDocente from './components/userpage/InicioDocente';

function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route exact path="/" element={<LoginPage />} />
            <Route exact path="/login" element={<LoginPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/home" element={<InicioEstudiante />} />
            <Route path="/home-docente" element={<InicioDocente />} />

            {ServicioAuthUser.adminOnly() && (
              <>
                <Route path="/register" element={<RegistrationPage />} />
                <Route path="/admin/user-management" element={<ServicioUser />} />
                <Route path="/update-user/:userId" element={<ActuUser />} />
              </>
            )}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;

