import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";

function AdminDashboard() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));

  const [biens, setBiens] = useState([]);
  const [demandes, setDemandes] = useState([]);
  const [showFormBien, setShowFormBien] = useState(false);
  const [showBiens, setShowBiens] = useState(false);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("");
  const [bienEnEdition, setBienEnEdition] = useState(null);

  const [formBien, setFormBien] = useState({
    titre: "", description: "", prix: "", surface: "",
    nbPieces: "", nbChambres: "", ville: "", adresse: "",
    type: "APPARTEMENT", statut: "DISPONIBLE",
  });

  useEffect(() => {
    if (user?.userId) {
      chargerBiens();
    }
  }, []);

  const chargerBiens = async () => {
    try {
      const agenceId = user?.agenceId || await getAgenceId();
      if (!agenceId) return;
      const res = await api.get(`/biens/agence/${agenceId}`);
      setBiens(res.data);
      setShowBiens(true);
    } catch (error) {
      console.error("Erreur chargement biens:", error);
    }
  };

  const getAgenceId = async () => {
    try {
      const res = await api.get(`/utilisateurs/${user.userId}`);
      return res.data.agence?.id;
    } catch (error) {
      console.error("Erreur récupération agence:", error);
      return null;
    }
  };

  const handleSubmitBien = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const agenceId = await getAgenceId();
      const payload = {
        ...formBien,
        prix: parseFloat(formBien.prix),
        surface: formBien.surface ? parseFloat(formBien.surface) : null,
        nbPieces: formBien.nbPieces ? parseInt(formBien.nbPieces) : null,
        nbChambres: formBien.nbChambres ? parseInt(formBien.nbChambres) : null,
        agence: { id: agenceId },
      };

      if (bienEnEdition) {
        await api.put(`/biens/${bienEnEdition.id}`, payload);
        setMessage("✅ Bien modifié avec succès !");
      } else {
        await api.post("/biens", payload);
        setMessage("✅ Bien ajouté avec succès !");
      }

      setMessageType("success");
      setFormBien({
        titre: "", description: "", prix: "", surface: "",
        nbPieces: "", nbChambres: "", ville: "", adresse: "",
        type: "APPARTEMENT", statut: "DISPONIBLE",
      });
      setBienEnEdition(null);
      setShowFormBien(false);
      chargerBiens();
    } catch (error) {
      setMessage("❌ Une erreur est survenue.");
      setMessageType("error");
    } finally {
      setLoading(false);
    }
  };

  const handleEditer = (bien) => {
    setBienEnEdition(bien);
    setFormBien({
      titre: bien.titre || "",
      description: bien.description || "",
      prix: bien.prix || "",
      surface: bien.surface || "",
      nbPieces: bien.nbPieces || "",
      nbChambres: bien.nbChambres || "",
      ville: bien.ville || "",
      adresse: bien.adresse || "",
      type: bien.type || "APPARTEMENT",
      statut: bien.statut || "DISPONIBLE",
    });
    setShowFormBien(true);
    setShowBiens(false);
  };

  const handleSupprimer = async (id) => {
    if (!window.confirm("Confirmer la suppression de ce bien ?")) return;
    try {
      await api.delete(`/biens/${id}`);
      setBiens(biens.filter(b => b.id !== id));
      setMessage("✅ Bien supprimé.");
      setMessageType("success");
    } catch (error) {
      setMessage("❌ Erreur lors de la suppression.");
      setMessageType("error");
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

  const statutColor = {
    DISPONIBLE: { bg: "#f0fff4", color: "#276749" },
    LOUE: { bg: "#fff3e0", color: "#e65100" },
    VENDU: { bg: "#fdecea", color: "#c0392b" },
  };

  return (
    <div style={{ padding: "32px 40px", background: "#f5f8fc", minHeight: "calc(100vh - 72px)" }}>

      {/* Header */}
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "32px" }}>
        <div>
          <h1 style={{ color: "#0f2a4d", marginBottom: "4px", fontSize: "28px" }}>
            Dashboard Agence
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
        <div style={cardStyle}>
          <p style={{ color: "#6b7c93", fontSize: "13px", marginBottom: "8px" }}>Total biens</p>
          <h2 style={{ color: "#1565d8", fontSize: "32px" }}>{biens.length}</h2>
        </div>
        <div style={cardStyle}>
          <p style={{ color: "#6b7c93", fontSize: "13px", marginBottom: "8px" }}>Disponibles</p>
          <h2 style={{ color: "#276749", fontSize: "32px" }}>
            {biens.filter(b => b.statut === "DISPONIBLE").length}
          </h2>
        </div>
        <div style={cardStyle}>
          <p style={{ color: "#6b7c93", fontSize: "13px", marginBottom: "8px" }}>Loués/Vendus</p>
          <h2 style={{ color: "#e65100", fontSize: "32px" }}>
            {biens.filter(b => b.statut !== "DISPONIBLE").length}
          </h2>
        </div>
      </div>

      {/* Message global */}
      {message && !showFormBien && (
        <p style={{
          padding: "10px 14px", borderRadius: "8px", marginBottom: "16px", fontSize: "14px",
          background: messageType === "success" ? "#f0fff4" : "#fdecea",
          color: messageType === "success" ? "#276749" : "#c0392b"
        }}>{message}</p>
      )}

      {/* Actions */}
      <div style={cardStyle}>
        <h3 style={{ color: "#0f2a4d", marginBottom: "16px" }}>Actions rapides</h3>
        <div style={{ display: "flex", gap: "12px" }}>
          <button onClick={() => {
            setShowFormBien(true);
            setShowBiens(false);
            setBienEnEdition(null);
            setMessage("");
            setFormBien({
              titre: "", description: "", prix: "", surface: "",
              nbPieces: "", nbChambres: "", ville: "", adresse: "",
              type: "APPARTEMENT", statut: "DISPONIBLE",
            });
          }} style={btnPrimary}>
            + Ajouter un bien
          </button>
          <button onClick={chargerBiens} style={btnSecondary}>
            Voir mes biens
          </button>
        </div>
      </div>

      {/* Formulaire ajouter/modifier bien */}
      {showFormBien && (
        <div style={cardStyle}>
          <h3 style={{ color: "#0f2a4d", marginBottom: "20px" }}>
            {bienEnEdition ? "Modifier le bien" : "Ajouter un nouveau bien"}
          </h3>

          {message && (
            <p style={{
              padding: "10px 14px", borderRadius: "8px", marginBottom: "16px", fontSize: "14px",
              background: messageType === "success" ? "#f0fff4" : "#fdecea",
              color: messageType === "success" ? "#276749" : "#c0392b"
            }}>{message}</p>
          )}

          <form onSubmit={handleSubmitBien}>
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "16px", marginBottom: "16px" }}>
              <div style={{ gridColumn: "1 / -1" }}>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Titre *</label>
                <input type="text" placeholder="Ex: Appartement F3 Badalabougou"
                  value={formBien.titre}
                  onChange={(e) => setFormBien({ ...formBien, titre: e.target.value })}
                  required style={inputStyle} />
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Prix (FCFA) *</label>
                <input type="number" placeholder="150000"
                  value={formBien.prix}
                  onChange={(e) => setFormBien({ ...formBien, prix: e.target.value })}
                  required style={inputStyle} />
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Surface (m²)</label>
                <input type="number" placeholder="80"
                  value={formBien.surface}
                  onChange={(e) => setFormBien({ ...formBien, surface: e.target.value })}
                  style={inputStyle} />
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Nb pièces</label>
                <input type="number" placeholder="3"
                  value={formBien.nbPieces}
                  onChange={(e) => setFormBien({ ...formBien, nbPieces: e.target.value })}
                  style={inputStyle} />
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Nb chambres</label>
                <input type="number" placeholder="2"
                  value={formBien.nbChambres}
                  onChange={(e) => setFormBien({ ...formBien, nbChambres: e.target.value })}
                  style={inputStyle} />
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Ville *</label>
                <input type="text" placeholder="Bamako"
                  value={formBien.ville}
                  onChange={(e) => setFormBien({ ...formBien, ville: e.target.value })}
                  required style={inputStyle} />
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Adresse</label>
                <input type="text" placeholder="Rue 42, Badalabougou"
                  value={formBien.adresse}
                  onChange={(e) => setFormBien({ ...formBien, adresse: e.target.value })}
                  style={inputStyle} />
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Type *</label>
                <select value={formBien.type}
                  onChange={(e) => setFormBien({ ...formBien, type: e.target.value })}
                  style={{ ...inputStyle, cursor: "pointer" }}>
                  <option value="APPARTEMENT">Appartement</option>
                  <option value="MAISON">Maison</option>
                  <option value="TERRAIN">Terrain</option>
                  <option value="VILLA">Villa</option>
                </select>
              </div>

              <div>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Statut *</label>
                <select value={formBien.statut}
                  onChange={(e) => setFormBien({ ...formBien, statut: e.target.value })}
                  style={{ ...inputStyle, cursor: "pointer" }}>
                  <option value="DISPONIBLE">Disponible</option>
                  <option value="LOUE">Loué</option>
                  <option value="VENDU">Vendu</option>
                </select>
              </div>

              <div style={{ gridColumn: "1 / -1" }}>
                <label style={{ display: "block", fontSize: "12px", color: "#6b7c93", marginBottom: "5px" }}>Description</label>
                <textarea placeholder="Description du bien..."
                  value={formBien.description}
                  onChange={(e) => setFormBien({ ...formBien, description: e.target.value })}
                  rows={3}
                  style={{ ...inputStyle, resize: "vertical" }} />
              </div>
            </div>

            <div style={{ display: "flex", gap: "12px" }}>
              <button type="submit" disabled={loading} style={btnPrimary}>
                {loading ? "Enregistrement..." : bienEnEdition ? "Modifier le bien" : "Ajouter le bien"}
              </button>
              <button type="button" onClick={() => {
                setShowFormBien(false);
                setBienEnEdition(null);
              }} style={btnSecondary}>
                Annuler
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Liste des biens */}
      {showBiens && biens.length === 0 && (
        <div style={{ ...cardStyle, textAlign: "center", color: "#6b7c93" }}>
          <p>Aucun bien publié pour le moment. Cliquez sur "+ Ajouter un bien" pour commencer.</p>
        </div>
      )}

      {showBiens && biens.length > 0 && (
        <div style={cardStyle}>
          <h3 style={{ color: "#0f2a4d", marginBottom: "20px" }}>
            Mes biens ({biens.length})
          </h3>
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr style={{ background: "#f5f8fc" }}>
                {["Titre", "Type", "Ville", "Prix (FCFA)", "Statut", "Actions"].map(h => (
                  <th key={h} style={{
                    padding: "12px 16px", textAlign: "left", fontSize: "12px",
                    color: "#6b7c93", fontWeight: "600", textTransform: "uppercase"
                  }}>{h}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {biens.map((bien) => (
                <tr key={bien.id} style={{ borderBottom: "1px solid #f0f4ff" }}>
                  <td style={{ padding: "12px 16px", color: "#0f2a4d", fontWeight: "500" }}>{bien.titre}</td>
                  <td style={{ padding: "12px 16px", color: "#6b7c93" }}>{bien.type}</td>
                  <td style={{ padding: "12px 16px", color: "#6b7c93" }}>{bien.ville}</td>
                  <td style={{ padding: "12px 16px", color: "#1565d8", fontWeight: "600" }}>
                    {bien.prix?.toLocaleString()}
                  </td>
                  <td style={{ padding: "12px 16px" }}>
                    <span style={{
                      padding: "4px 10px", borderRadius: "20px", fontSize: "12px", fontWeight: "600",
                      background: statutColor[bien.statut]?.bg,
                      color: statutColor[bien.statut]?.color
                    }}>
                      {bien.statut}
                    </span>
                  </td>
                  <td style={{ padding: "12px 16px" }}>
                    <div style={{ display: "flex", gap: "8px" }}>
                      <button onClick={() => handleEditer(bien)} style={{
                        background: "#f0f4ff", color: "#1565d8", border: "none",
                        padding: "6px 12px", borderRadius: "6px", cursor: "pointer",
                        fontSize: "12px", fontWeight: "600"
                      }}>Modifier</button>
                      <button onClick={() => handleSupprimer(bien.id)} style={{
                        background: "#fdecea", color: "#c0392b", border: "none",
                        padding: "6px 12px", borderRadius: "6px", cursor: "pointer",
                        fontSize: "12px", fontWeight: "600"
                      }}>Supprimer</button>
                    </div>
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

export default AdminDashboard;