import React from "react";
import { NavLink } from "react-router-dom";
import "./Header.css";
import logo from "../assets/images/Mes-logos/logo.png"; // ✅ Chemin correct

export default function Header() {
  return (
    <header className="app-bar">
      <div className="app-bar__left">
        <img src={logo} alt="logo" className="logo-img" /> {/* ✅ Utilisation du logo importé */}
        <span className="brand">Immo-Manager</span>
      </div>

      <nav className="app-bar__center">
        <ul className="nav-links">
          <li><NavLink to="/" className={({ isActive }) => (isActive ? "active" : "")}>Accueil</NavLink></li>
          <li><NavLink to="/biens" className={({ isActive }) => (isActive ? "active" : "")}>Biens</NavLink></li>
          <li><NavLink to="/clients" className={({ isActive }) => (isActive ? "active" : "")}>Agences</NavLink></li>
          <li><NavLink to="/reservation" className={({ isActive }) => (isActive ? "active" : "")}>Gestion locative</NavLink></li>
          <li><NavLink to="/inscription" className={({ isActive }) => (isActive ? "active" : "")}>S’inscrire</NavLink></li>
        </ul>
      </nav>

      <div className="app-bar__right">
        <NavLink to="/connexion" className="btn-connect">Se connecter</NavLink>
      </div>
    </header>
  );
}