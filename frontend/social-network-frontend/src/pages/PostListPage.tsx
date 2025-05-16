import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";
import { Link } from "react-router-dom";

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

  useEffect(() => {
    const fetchPosts = async () => {
        console.log("Token en contexto:", token);
        try {
        const response = await axios.get("http://localhost:8083/posts", {
            headers: { Authorization: `Bearer ${token}` },
        });
        console.log("Respuesta posts:", response.data);
        setPosts(response.data);
        } catch (err: any) {
        console.error("Error al obtener posts:", err.response || err);
        }
    };
    fetchPosts();
  }, [token]);

  const handleLike = async (id: number) => {
    await axios.post(
      `http://localhost:8083/posts/${id}/like`,
      {},
      { headers: { Authorization: `Bearer ${token}` } }
    );
    setPosts((prev) =>
      prev.map((p) =>
        p.id === id ? { ...p, totalLikes: p.totalLikes + 1 } : p
      )
    );
  };

  return (
    <div>
      <h2>Publicaciones</h2>
      {posts.map((post) => (
        <div key={post.id} style={{ marginBottom: "1rem" }}>
          <p><strong>{post.usuario}</strong> dijo:</p>
          <p>{post.mensaje}</p>
          <p>Publicado el: {new Date(post.fechaPublicacion).toLocaleString()}</p>
          <p>Likes: {post.totalLikes}</p>
          <button onClick={() => handleLike(post.id)}>👍 Like</button>
          <Link to="/create">Nueva publicación</Link>
          <Link to="/profile">Mi Perfil</Link>
        </div>
      ))}
    </div>
  );
};

export default PostListPage;