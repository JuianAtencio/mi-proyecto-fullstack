// src/components/UserProfileCard.tsx
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";

type UserProfile = {
  id: number;
  nombres: string;
  apellidos: string;
  alias: string;
  fechaNacimiento: string;
};

const UserProfileCard = () => {
  const { token } = useAuth();
  const [profile, setProfile] = useState<UserProfile | null>(null);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await axios.get("http://localhost:8082/users/profile", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProfile(res.data);
      } catch (err) {
        console.error("Error al obtener el perfil", err);
      }
    };
    fetchProfile();
  }, [token]);

  if (!profile) return <p>Cargando perfil...</p>;

  return (
    <div>
      <p><strong>Nombres:</strong> {profile.nombres}</p>
      <p><strong>Apellidos:</strong> {profile.apellidos}</p>
      <p><strong>Alias:</strong> {profile.alias}</p>
      <p><strong>Fecha de nacimiento:</strong> {new Date(profile.fechaNacimiento).toLocaleDateString()}</p>
    </div>
  );
};

export default UserProfileCard;