import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import Modal from "../components/Modal";
import CreatePostForm from "../components/CreatePostForm";
import UserProfileCard from "../components/UserProfileCard";

type Post = {
  id: number;
  mensaje: string;
  usuario: string;
  fechaPublicacion: string;
  totalLikes: number;
};

const PostListPage = () => {
  const { token } = useAuth();
  const [posts, setPosts] = useState<Post[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [showProfile, setShowProfile] = useState(false);

  const fetchPosts = async () => {
    try {
      const response = await axios.get("http://localhost:8083/posts", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setPosts(response.data);
    } catch (err: any) {
      console.error("Error al obtener posts:", err.response || err);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const handleLike = async (id: number) => {
    try {
      await axios.post(
        `http://localhost:8083/posts/${id}/like`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      fetchPosts();
    } catch (error) {
      console.error("Error al dar like", error);
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Publicaciones</h2>

      <div style={{ marginBottom: "1rem" }}>
        <button onClick={() => setShowModal(true)} style={{ marginRight: "1rem" }}>
          Nueva publicación
        </button>
        <button onClick={() => setShowProfile(true)}>Mi Perfil</button>
      </div>

      {posts.length === 0 ? (
        <p>No hay publicaciones aún.</p>
      ) : (
        <table style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th style={thStyle}>Usuario</th>
              <th style={thStyle}>Mensaje</th>
              <th style={thStyle}>Fecha</th>
              <th style={thStyle}>Likes</th>
              <th style={thStyle}>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {posts.map((post) => (
              <tr key={post.id}>
                <td style={tdStyle}>{post.usuario}</td>
                <td style={tdStyle}>{post.mensaje}</td>
                <td style={tdStyle}>
                  {new Date(post.fechaPublicacion).toLocaleString()}
                </td>
                <td style={tdStyle}>{post.totalLikes}</td>
                <td style={tdStyle}>
                  <button onClick={() => handleLike(post.id)}>👍 Like </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {showModal && (
        <Modal title="Crear publicación" onClose={() => setShowModal(false)}>
          <CreatePostForm
            onSuccess={() => {
              setShowModal(false);
              fetchPosts();
            }}
          />
        </Modal>
      )}

      {showProfile && (
        <Modal title="Mi Perfil" onClose={() => setShowProfile(false)}>
          <UserProfileCard />
        </Modal>
      )}
    </div>
  );
};

const thStyle: React.CSSProperties = {
  border: "1px solid #ccc",
  padding: "0.75rem",
  background: "#0066cc",
  color: "white",
  textAlign: "left",
};

const tdStyle: React.CSSProperties = {
  border: "1px solid #ddd",
  padding: "0.75rem",
  backgroundColor: "#f9f9f9",
};



export default PostListPage;