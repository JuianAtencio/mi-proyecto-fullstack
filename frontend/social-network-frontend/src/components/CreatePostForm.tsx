import React, { useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";

interface Props {
  onSuccess: () => void;
}

const CreatePostForm = ({ onSuccess }: Props) => {
  const [mensaje, setMensaje] = useState("");
  const { token } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await axios.post(
        "http://localhost:8083/posts",
        { mensaje },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      onSuccess(); // cerrar modal y recargar posts
    } catch (error) {
      alert("Error al crear publicación");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <textarea
        rows={4}
        value={mensaje}
        onChange={(e) => setMensaje(e.target.value)}
        placeholder="Escribe tu mensaje"
        required
        style={{ width: "100%", marginBottom: "1rem" }}
      />
      <button type="submit">Publicar</button>
    </form>
  );
};

export default CreatePostForm;