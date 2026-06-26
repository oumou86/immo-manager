import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";

function SuperAdminDashboard() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));

  const [stats, setStats] = useState({ totalClients: 0, totalAdmins: 0 });
  const [totalBiens, setTotalBiens] = useState(0);
  const [totalAgences, setTotalAgences] = useState(0);
  const [utilisateurs, setUtilisateurs] = useState([]);
  const [admins, setAdmins] = useState([]);
  const [showUtilisateurs, setShowUtilisateurs] = useState(false);
  const [showFormAdmin, setShowFormAdmin] = useState(false);
  const [showFormAgence, setShowFormAgence] = useState(false);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("");

  const [formAdmin, setFormAdmin] = useState({
    nom: "", prenom: "", email: "", motDePasse: "", telephone: "",
  });

  const [formAgence, setFormAgence] = useState({
    nom: "", adresse: "", telephone: "", email: "", adminId: "",
  });

  useEffect(() => { chargerStats(); }, []);

  const chargerStats = async () => {
    try {
      const [statsRes, biensRes, agencesRes] = await Promise.all([
        api.get("/utilisateurs/stats"),
        api.get("/biens"),
        api.get("/agences"),
      ]);
      setStats(statsRes.data);
      setTotalBiens(biensRes.data.length);
      setTotalAgences(agencesRes.data.length);
    } catch (error) {
      console.error("Erreur chargement stats:", error);
    }
  };

  const chargerUtilisateurs = async () => {
    try {
      const res = await api.get("/utilisateurs/tous");
      setUtilisateurs(res.data);
      setShowUtilisateurs(true);
      setShowFormAdmin(false);
      setShowFormAgence(false);
    } catch (error) {
      console.error("Erreur:", error);
    }
  };

  const chargerAdmins = async () => {
    try {
      const res = await api.get("/utilisateurs/tous");
      const adminsList = res.data.filter(u => u.role === "ADMIN");
      setAdmins(adminsList);
    } catch (error) {
      console.error("Erreur chargement admins:", error);
    }
  };

  const handleCreerAdmin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    try {
      await api.post("/utilisateurs/creer-admin", formAdmin);
      setMessage("✅ Compte Admin créé avec succès !");
      setMessageType("success");
      setFormAdmin({ nom: "", prenom: "", email: "", motDePasse: "", telephone: "" });
      chargerStats();
    } catch (error) {
      setMessage(error.response?.status === 409
        ? "❌ Un compte existe déjà avec cet email."
        : "❌ Une erreur est survenue.");
      setMessageType("error");
    } finally {
      setLoading(false);
    }
  };

  const handleCreerAgence = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    try {
      // 1. Créer l'agence
      const agenceRes = await api.post("/agences", {
        nom: formAgence.nom,
        adresse: formAgence.adresse,
        telephone: formAgence.telephone,
        email: formAgence.email,
      });

      // 2. Associer l'Admin à l'agence si sélectionné
      if (formAgence.adminId) {
        const admin = admins.find(a => a.id === parseInt(formAgence.adminId));
        if (admin) {
          await api.put(`/utilisateurs/${formAgence.adminId}`, {
            ...admin,
            agence: { id: agenceRes.data.id }
          });
        }
      }

      setMessage("✅ Agence créée avec succès !");
      setMessageType("success");
      setFormAgence({ nom: "", adresse: "", telephone: "", email: "", adminId: "" });
      chargerStats();
    } catch (error) {
      setMessage("❌ Une erreur est survenue lors de la création de l'agence.");
      setMessageType("error");
    } finally {
      setLoading(false);
    }
  };

  const handleToggleActif = async (id, actifActuel) => {
    try {
      await api.put(`/utilisateurs/${id}/toggle-actif`);
      setUtilisateurs(utilisateurs.map(u =>
        u.id === id ? { ...u, actif: !actifActuel } : u
      ));
    } catch (error) {
      console.error("Erreur toggle actif:", error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/connexion");
  };

  const cardStyle = {
    background: "white", padding: "24px", borderRadius: "12px",
    boxShadow: "0 2px 8px rgba(0,0,0,0.06)", marginBottom: "24px"
  };

  const inputStyle = {
    width: "100%", border: "1.5px solid #e2e8f2", borderRadius: "8px",
    background: "#fafbfd", padding: "10px 12px", fontSize: "14px",
    color: "#0f2a4d", boxSizing: "border-box"
  };

  const btnPrimary = {
    background: "#1565d8", color: "white", border: "none",
    padding: "10px 20px", borderRadius: "8px", cursor: "pointer", fontWeight: "600"
  };

  const btnSecondary = {
    background: "#f0f4ff", color: "#1565d8", border: "1px solid #1565d8",
    padding: "10px 20px", borderRadius: "8px", cursor: "pointer", fontWeight: "600"
  };

  const btnGreen = {
    background: "#276749", color: "white", border: "none",
    padding: "10px 20px", borderRadius: "8px", cursor: "pointer", fontWeight: "600"
  };

  return (
    <div style={{ padding: "32px 40px", background: "#f5f8fc", minHeight: "calc(100vh - 72px)" }}>

      {/* Header */}
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "32px" }}>
        <div>
          <h1 style={{ color: "#0f2a4d", marginBottom: "4px", fontSize: "28px" }}>
            Tableau de bord Super Admin
          </h1>
          <p style={{ color: "#6b7c93" }}>Bienvenue, {user?.nom} {user?.prenom}</p>
        </div>
        <button onClick={handleLogout} style={{
          background: "#e53e3e", color: "white", border: "none",
          padding: "10px 20px", borderRadius: "8px", cursor: "pointer", fontWeight: "600"
        }}>
          Se déconnecter
        </button>
      </div>

      {/* Stats */}
      <div style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)", gap: "20px", marginBottom: "24px" }}>
        {[
          { label: "Clients inscrits", value: stats.totalClients },
          { label: "Agences", value: totalAgences },
          { label: "Biens publiés", value: totalBiens },
        ].map((stat, i) => (
          <div key={i} style={cardStyle}>
            <p style={{ color: "#6b7c93", fontSize: "13px", marginBottom: "8px" }}>{stat.label}</p>
            <h2 style={{ color: "#1565d8", fontSize: "32px" }}>{stat.value}</h2>
          </div>
        ))}
      </div>

      {/* Actions rapides */}
      <div style={cardStyle}>
        <h3 style={{ color: "#0f2a4d", marginBottom: "16px" }}>Actions rapides</h3>
        <div style={{ display: "flex", gap: "12px", flexWrap: "wrap" }}>
          <button onClick={() => {
            setShowFormAdmin(true);
            setShowFormAgence(false);
            setShowUtilisateurs(false);
            setMessage("");
          }} style={btnPrimary}>
            + Créer un compte Admin
          </button>
          <button onClick={() => {
            setShowFormAgence(true);
            setShowFormAdmin(false);
            setShowUtilisateurs(false);
            setMessage("");
            chargerAdmins();
          }} style={btnGreen}>
            + Créer une agence
          </button>
          <button onClick={chargerUtilisateurs} style={btnSecondary}>
            Voir tous les utilisateurs
          </button>
        </div>
      </div>

      {/* Formulaire créer Admin */}
      {showFormAdmin && (
        <div style={cardStyle}>
          <h3 style={{ color: "#0f2a4d", marginBottom: "20px" }}>Créer un compte Admin / Agence</h3>
          {message && (
            <p style={{
              padding: "10px 14px", borderRadius: "8px", marginBottom: "16px", fontSize: "14px",
              background: messageType === "success" ? "#f0fff4" : "#fdecea",
              color: messageType === "success" ? "#276749" : "#c0392b"
            }}>{message}</p>
          )}
          <form onSubmit={handleCreerAdmin}>
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px", marginBottom: "16px" }}>
              {[
                { name: "nom", label: "Nom", placeholder: "Diallo" },
                { name: "prenom", label: "Prénom", placeholder: "Ousmane" },
                { name: "email", label: "Email", placeholder: "admin@agence.com", type: "email" },
                { name: "motDePasse", label: "Mot de passe", placeholder: "••••••••", type: "password" },
                { name: "telephone", label: "Téléphone", placeholder: "+223 76 00 00 00" },
              ].map((field) => (
                <div key={field.name}>
                  <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>
                    {field.label}
                  </label>
                  <input
                    type={field.type || "text"}
                    name={field.name}
                    placeholder={field.placeholder}
                    value={formAdmin[field.name]}
                    onChange={(e) => setFormAdmin({ ...formAdmin, [e.target.name]: e.target.value })}
                    required={field.name !== "telephone"}
                    style={inputStyle}
                  />
                </div>
              ))}
            </div>
            <div style={{ display: "flex", gap: "12px" }}>
              <button type="submit" disabled={loading} style={btnPrimary}>
                {loading ? "Création en cours..." : "Créer le compte Admin"}
              </button>
              <button type="button" onClick={() => setShowFormAdmin(false)} style={btnSecondary}>
                Annuler
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Formulaire créer Agence */}
      {showFormAgence && (
        <div style={cardStyle}>
          <h3 style={{ color: "#0f2a4d", marginBottom: "20px" }}>Créer une nouvelle agence</h3>
          {message && (
            <p style={{
              padding: "10px 14px", borderRadius: "8px", marginBottom: "16px", fontSize: "14px",
              background: messageType === "success" ? "#f0fff4" : "#fdecea",
              color: messageType === "success" ? "#276749" : "#c0392b"
            }}>{message}</p>
          )}
          <form onSubmit={handleCreerAgence}>
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px", marginBottom: "16px" }}>
              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>
                  Nom de l'agence *
                </label>
                <input
                  type="text" placeholder="Agence Konaté Immobilier"
                  value={formAgence.nom}
                  onChange={(e) => setFormAgence({ ...formAgence, nom: e.target.value })}
                  required style={inputStyle}
                />
              </div>
              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>
                  Adresse *
                </label>
                <input
                  type="text" placeholder="Rue 42, Badalabougou, Bamako"
                  value={formAgence.adresse}
                  onChange={(e) => setFormAgence({ ...formAgence, adresse: e.target.value })}
                  required style={inputStyle}
                />
              </div>
              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>
                  Téléphone
                </label>
                <input
                  type="text" placeholder="+223 20 00 00 00"
                  value={formAgence.telephone}
                  onChange={(e) => setFormAgence({ ...formAgence, telephone: e.target.value })}
                  style={inputStyle}
                />
              </div>
              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>
                  Email de l'agence
                </label>
                <input
                  type="email" placeholder="contact@agence.com"
                  value={formAgence.email}
                  onChange={(e) => setFormAgence({ ...formAgence, email: e.target.value })}
                  style={inputStyle}
                />
              </div>
              <div style={{ gridColumn: "1 / -1" }}>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>
                  Associer à un Admin (optionnel)
                </label>
                <select
                  value={formAgence.adminId}
                  onChange={(e) => setFormAgence({ ...formAgence, adminId: e.target.value })}
                  style={{ ...inputStyle, cursor: "pointer" }}
                >
                  <option value="">-- Choisir un Admin --</option>
                  {admins.map(admin => (
                    <option key={admin.id} value={admin.id}>
                      {admin.nom} {admin.prenom} ({admin.email})
                    </option>
                  ))}
                </select>
              </div>
            </div>
            <div style={{ display: "flex", gap: "12px" }}>
              <button type="submit" disabled={loading} style={btnGreen}>
                {loading ? "Création en cours..." : "Créer l'agence"}
              </button>
              <button type="button" onClick={() => setShowFormAgence(false)} style={btnSecondary}>
                Annuler
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Liste des utilisateurs */}
      {showUtilisateurs && (
        <div style={cardStyle}>
          <h3 style={{ color: "#0f2a4d", marginBottom: "20px" }}>
            Tous les utilisateurs ({utilisateurs.length})
          </h3>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr style={{ background: "#f5f8fc" }}>
                {["Nom", "Prénom", "Email", "Rôle", "Statut", "Action"].map(h => (
                  <th key={h} style={{
                    padding: "12px 16px", textAlign: "left", fontSize: "12px",
                    color: "#6b7c93", fontWeight: "600", textTransform: "uppercase"
                  }}>{h}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {utilisateurs.map((u) => (
                <tr key={u.id} style={{ borderBottom: "1px solid #f0f4ff" }}>
                  <td style={{ padding: "12px 16px", color: "#0f2a4d" }}>{u.nom}</td>
                  <td style={{ padding: "12px 16px", color: "#0f2a4d" }}>{u.prenom}</td>
                  <td style={{ padding: "12px 16px", color: "#6b7c93", fontSize: "13px" }}>{u.email}</td>
                  <td style={{ padding: "12px 16px" }}>
                    <span style={{
                      padding: "4px 10px", borderRadius: "20px", fontSize: "12px", fontWeight: "600",
                      background: u.role === "SUPER_ADMIN" ? "#fff3e0" : u.role === "ADMIN" ? "#e8f4fd" : "#f0fff4",
                      color: u.role === "SUPER_ADMIN" ? "#e65100" : u.role === "ADMIN" ? "#1565d8" : "#276749"
                    }}>
                      {u.role}
                    </span>
                  </td>
                  <td style={{ padding: "12px 16px" }}>
                    <span style={{
                      padding: "4px 10px", borderRadius: "20px", fontSize: "12px", fontWeight: "600",
                      background: u.actif ? "#f0fff4" : "#fdecea",
                      color: u.actif ? "#276749" : "#c0392b"
                    }}>
                      {u.actif ? "Actif" : "Inactif"}
                    </span>
                  </td>
                  <td style={{ padding: "12px 16px" }}>
                    {u.role !== "SUPER_ADMIN" && (
                      <button
                        onClick={() => handleToggleActif(u.id, u.actif)}
                        style={{
                          background: u.actif ? "#fdecea" : "#f0fff4",
                          color: u.actif ? "#c0392b" : "#276749",
                          border: "none", padding: "6px 12px", borderRadius: "6px",
                          cursor: "pointer", fontSize: "12px", fontWeight: "600"
                        }}>
                        {u.actif ? "Désactiver" : "Activer"}
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default SuperAdminDashboard;