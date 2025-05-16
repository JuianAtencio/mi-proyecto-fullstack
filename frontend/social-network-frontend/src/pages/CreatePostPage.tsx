import React, { useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const CreatePostPage = () => {
  const [mensaje, setMensaje] = useState("");
  const { token } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await axios.post(
        "http://localhost:8083/posts",
        { mensaje }, // solo mensaje, el backend pone la fecha
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Publicación creada con éxito");
      navigate("/posts");
    } catch (error) {
      console.error("Error al crear publicación", error);
      alert("Error al crear publicación");
    }
  };

  return (
    <div>
      <h2>Crear Publicación</h2>
      <form onSubmit={handleSubmit}>
        <textarea
          rows={4}
          value={mensaje}
          onChange={(e) => setMensaje(e.target.value)}
          placeholder="Escribe tu mensaje"
          required
        />
        <br />
        <button type="submit">Publicar</button>
      </form>
    </div>
  );
};

export default CreatePostPage;