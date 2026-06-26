import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../api/axiosConfig";
import "./Login.css";

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    motDePasse: "",
  });

  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setServerError("");
    setLoading(true);

    try {
      const response = await api.post("/auth/login", formData);

      // Stockage du token et des infos utilisateur
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("user", JSON.stringify(response.data));

      // Redirection selon le rôle
      const role = response.data.role;

      if (role === "SUPER_ADMIN") {
        navigate("/super-admin/dashboard");
      } else if (role === "ADMIN") {
        navigate("/admin/dashboard");
      } else {
        navigate("/");
      }

    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          setServerError("Email ou mot de passe incorrect.");
        } else if (error.response.status === 403) {
          setServerError("Ce compte a été désactivé.");
        } else {
          setServerError("Une erreur est survenue. Veuillez réessayer.");
        }
      } else {
        setServerError("Impossible de contacter le serveur. Vérifiez votre connexion.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-wrapper">

        <div className="login-logo">
          <div className="login-logo-icon">
            <i className="ti ti-building-estate"></i>
          </div>
          <span>Immo-Manager</span>
        </div>

        <div className="login-card">
          <h2>Se connecter</h2>
          <p className="login-subtitle">Bienvenue, entrez vos identifiants</p>

          {serverError && <p className="error-message">{serverError}</p>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Email</label>
              <div className="input-icon-wrapper">
                <i className="ti ti-mail"></i>
                <input
                  type="email"
                  name="email"
                  placeholder="votre@email.com"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="form-group">
              <label>Mot de passe</label>
              <div className="input-icon-wrapper">
                <i className="ti ti-lock"></i>
                <input
                  type="password"
                  name="motDePasse"
                  placeholder="••••••••"
                  value={formData.motDePasse}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <button type="submit" disabled={loading}>
              {loading ? "Connexion en cours..." : (
                <>Se connecter <i className="ti ti-arrow-right"></i></>
              )}
            </button>
          </form>

          <p className="register-link">
            Pas encore de compte ? <Link to="/inscription">S'inscrire</Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Login;