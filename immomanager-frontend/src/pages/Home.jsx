import React from "react";
import "./Home.css";

// ==== Importation des images ====
import chambre1 from "../assets/images/chambre1.jpg";
import maison2 from "../assets/images/maison2.jpg";
import salleAManger from "../assets/images/salleAManger.jpg";

function Home() {
  return (
    <div className="home-container">

      {/* ===== HERO ===== */}
      <header 
        className="hero" 
        style={{ backgroundImage: "url('/images/image1.jpg')" }}
      >
        <h2>
          Le logement dont <span>vous rêviez</span>
        </h2>

        {/* Barre de recherche */}
        <div className="search-box">
          <div className="search-options">
            <button>Acheter</button>
            <button>Vendre</button>
            <button>Louer</button>
          </div>

          <div className="search-input">
            <input type="text" placeholder="Chercher un bien" />
            <button className="search-btn">Chercher</button>
          </div>
        </div>

        {/* Avis Google */}
        <div className="avis-google">
          <img
            src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"
            alt="Google logo"
          />
          <p>
            <span className="stars">⭐ 4.9</span> étoiles | 149 avis
          </p>
        </div>
      </header>

      {/* ===== ANNONCES ===== */}
      <section className="annonces">
        <h3>Nos dernières annonces immobilières</h3>
        <div className="annonces-grid">

          <div className="annonce-card">
            <img src={chambre1} alt="Appartement" />
            <h4>Appartement - 128 000fcfa</h4>
            <p>64m² · 3 pièces · 2 chambres</p>
            <p>Sebenincoro</p>
            <button>Voir</button>
          </div>

          <div className="annonce-card">
            <img src={maison2} alt="Maison" />
            <h4>Maison à louer - 249 000fcfa</h4>
            <p>97m² · 4 pièces · 3 chambres</p>
            <p>Hamdalaye ACI 2000</p>
            <button>Voir</button>
          </div>

          <div className="annonce-card">
            <img src={salleAManger} alt="Appartement" />
            <h4>Appartement - 456 000€</h4>
            <p>112m² · 6 pièces · 3 chambres</p>
            <p>Kati</p>
            <button>Voir</button>
          </div>
        </div>

        <button className="voir-tous-btn">Voir tous les biens</button>
      </section>

      {/* ===== NOTRE ÉQUIPE ===== */}
      <section className="equipe">
        <h3>Notre équipe est à votre écoute</h3>
        <div className="equipe-grid">
          <div className="equipe-card">
            <img src="https://via.placeholder.com/100" alt="Agent" />
            <h4>Ousmane Diallo</h4>
            <p>Conseiller immobilier</p>
          </div>
          <div className="equipe-card">
            <img src="https://via.placeholder.com/100" alt="Agent" />
            <h4>Marie Traoré</h4>
            <p>Gestion locative</p>
          </div>
          <div className="equipe-card">
            <img src="https://via.placeholder.com/100" alt="Agent" />
            <h4>Ali Konaté</h4>
            <p>Expert en financement</p>
          </div>
        </div>
      </section>

      {/* ===== AVIS CLIENTS ===== */}
      <section className="avis-clients">
        <h3>Ce que disent nos clients</h3>
        <div className="avis-grid">
          <div className="avis-card">
            <p>"Super agence, j’ai trouvé mon appartement très rapidement !"</p>
            <p className="auteur">— Sophie</p>
          </div>
          <div className="avis-card">
            <p>"Équipe très professionnelle et à l’écoute, je recommande."</p>
            <p className="auteur">— Karim</p>
          </div>
          <div className="avis-card">
            <p>"Un service impeccable pour la vente de ma maison."</p>
            <p className="auteur">— Awa</p>
          </div>
        </div>
      </section>

      {/* ===== FOOTER ===== */}
      <footer className="footer">
        <p>© 2025 Immo-Manager | Tous droits réservés</p>
      </footer>
    </div>
  );
}

export default Home;