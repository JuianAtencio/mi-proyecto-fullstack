import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";

import LoginPage from "./pages/LoginPage";
import ProfilePage from "./pages/ProfilePage";
import PostListPage from "./pages/PostListPage";
import CreatePostPage from "./pages/CreatePostPage";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/posts" element={<PostListPage />} />
          <Route path="/create" element={<CreatePostPage />} />     
          <Route path="/profile" element={<ProfilePage />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
