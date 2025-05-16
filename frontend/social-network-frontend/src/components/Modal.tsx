import React from "react";

interface Props {
  title: string;
  children: React.ReactNode;
  onClose: () => void;
}

const Modal = ({ title, children, onClose }: Props) => {
  return (
    <div style={backdropStyle}>
      <div style={modalStyle}>
        <h2>{title}</h2>
        <button onClick={onClose} style={{ float: "right" }}>Cerrar</button>
        <div>{children}</div>
      </div>
    </div>
  );
};

const backdropStyle: React.CSSProperties = {
  position: "fixed",
  top: 0, left: 0,
  width: "100%",
  height: "100%",
  backgroundColor: "rgba(0,0,0,0.5)",
  display: "flex",
  alignItems: "center",
  justifyContent: "center"
};

const modalStyle: React.CSSProperties = {
  background: "#fff",
  padding: "2rem",
  borderRadius: "8px",
  width: "500px",
  maxHeight: "80vh",
  overflowY: "auto"
};

export default Modal;