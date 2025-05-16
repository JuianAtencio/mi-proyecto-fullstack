import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";

interface Profile {
  nombres: string;
  apellidos: string;
  fechaNacimiento: string;
  alias: string;
}

const ProfilePage = () => {
  const [perfil, setPerfil] = useState<Profile | null>(null);
  const { token } = useAuth();

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await axios.get("http://localhost:8082/users/profile", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setPerfil(response.data);
      } catch (error) {
        console.error("Error al obtener perfil:", error);
      }
    };

    fetchProfile();
  }, [token]);

  if (!perfil) {
    return <p>Cargando perfil...</p>;
  }

  return (
    <div>
      <h2>Perfil de Usuario</h2>
      <p><strong>Nombre:</strong> {perfil.nombres} {perfil.apellidos}</p>
      <p><strong>Alias:</strong> {perfil.alias}</p>
      <p><strong>Fecha de nacimiento:</strong> {perfil.fechaNacimiento}</p>
    </div>
  );
};

export default ProfilePage;