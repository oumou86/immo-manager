import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/axiosConfig";
import "./S'inscrire.css";

function Inscription() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    nom: "",
    prenom: "",
    email: "",
    motDePasse: "",
    telephone: "",
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    setServerError("");
    setLoading(true);

    try {
      const response = await api.post("/auth/register", formData);

      localStorage.setItem("token", response.data.token);
      localStorage.setItem("user", JSON.stringify(response.data));

      navigate("/");
    } catch (error) {
      if (error.response) {
        if (error.response.status === 409) {
          setServerError("Un compte existe déjà avec cet email.");
        } else if (
          error.response.status === 400 &&
          error.response.data.errors
        ) {
          setErrors(error.response.data.errors);
        } else {
          setServerError(
            error.response.data.message || "Une erreur est survenue."
          );
        }
      } else {
        setServerError(
          "Impossible de contacter le serveur. Vérifiez votre connexion."
        );
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="inscription-page">
      <div className="inscription-wrapper">
        <div className="inscription-card">
          <h2>Créer un compte</h2>
          <p className="inscription-subtitle">
            Trouvez le logement dont vous rêviez
          </p>

          {serverError && (
            <p className="error-message">{serverError}</p>
          )}

          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Nom</label>
                <input
                  type="text"
                  name="nom"
                  placeholder=""
                  value={formData.nom}
                  onChange={handleChange}
                  required
                />
                {errors.nom && (
                  <span className="field-error">{errors.nom}</span>
                )}
              </div>

              <div className="form-group">
                <label>Prénom</label>
                <input
                  type="text"
                  name="prenom"
                  placeholder=""
                  value={formData.prenom}
                  onChange={handleChange}
                  required
                />
                {errors.prenom && (
                  <span className="field-error">{errors.prenom}</span>
                )}
              </div>
            </div>

            <div className="form-group">
              <label>Email</label>
              <div className="input-icon-wrapper">
                <i className="ti ti-mail"></i>
                <input
                  type="email"
                  name="email"
                  placeholder=""
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
              {errors.email && (
                <span className="field-error">{errors.email}</span>
              )}
            </div>

            <div className="form-group">
              <label>Mot de passe</label>
              <div className="input-icon-wrapper">
                <i className="ti ti-lock"></i>
                <input
                  type="password"
                  name="motDePasse"
                  placeholder=""
                  value={formData.motDePasse}
                  onChange={handleChange}
                  required
                  minLength={6}
                />
              </div>
              {errors.motDePasse && (
                <span className="field-error">{errors.motDePasse}</span>
              )}
            </div>

            <div className="form-group">
              <label>Téléphone</label>
              <div className="input-icon-wrapper">
                <i className="ti ti-phone"></i>
                <input
                  type="text"
                  name="telephone"
                  placeholder=""
                  value={formData.telephone}
                  onChange={handleChange}
                />
              </div>
            </div>

            <button type="submit" disabled={loading}>
              {loading ? (
                "Inscription en cours..."
              ) : (
                <>
                  S'inscrire <i className="ti ti-arrow-right"></i>
                </>
              )}
            </button>
          </form>

          <p className="login-link">
            Déjà un compte ? <Link to="/connexion">Se connecter</Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Inscription;