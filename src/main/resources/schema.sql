DROP DATABASE IF EXISTS dentalsoftdb;
CREATE DATABASE dentalsoftdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dentalsoftdb;

CREATE TABLE cabinet (
  id BIGINT PRIMARY KEY,
  nom VARCHAR(120) NOT NULL,
  adresse VARCHAR(255),
  telephone VARCHAR(30),
  email VARCHAR(150),
  logo_path VARCHAR(255),
  CONSTRAINT chk_cabinet_unique CHECK (id = 1)
) ENGINE=InnoDB;

CREATE TABLE utilisateur (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(80) NOT NULL UNIQUE,
  password_hash VARCHAR(512) NOT NULL,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  email VARCHAR(150),
  telephone VARCHAR(30),
  adresse VARCHAR(255),
  specialite VARCHAR(100),
  actif BOOLEAN NOT NULL DEFAULT TRUE,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE role (
  code VARCHAR(20) PRIMARY KEY,
  libelle VARCHAR(50) NOT NULL,
  CONSTRAINT chk_role_code CHECK (code IN ('ADMIN','MEDECIN','SECRETAIRE'))
) ENGINE=InnoDB;

CREATE TABLE utilisateur_role (
  utilisateur_id BIGINT NOT NULL,
  role_code VARCHAR(20) NOT NULL,
  PRIMARY KEY (utilisateur_id, role_code),
  FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id),
  FOREIGN KEY (role_code) REFERENCES role(code)
) ENGINE=InnoDB;

CREATE TABLE patient (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  date_naissance DATE NOT NULL,
  sexe VARCHAR(20),
  cin VARCHAR(30) UNIQUE,
  passeport VARCHAR(30) UNIQUE,
  email VARCHAR(150),
  telephone VARCHAR(30),
  adresse VARCHAR(255),
  assurance VARCHAR(80),
  archive BOOLEAN NOT NULL DEFAULT FALSE,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_patient_nom (nom, prenom),
  INDEX idx_patient_tel (telephone)
) ENGINE=InnoDB;

CREATE TABLE dossier_medical (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT NOT NULL UNIQUE,
  medecin_id BIGINT NOT NULL,
  date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES patient(id),
  FOREIGN KEY (medecin_id) REFERENCES utilisateur(id)
) ENGINE=InnoDB;

CREATE TABLE antecedent (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  libelle VARCHAR(150) NOT NULL,
  categorie VARCHAR(60) NOT NULL,
  actif BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE dossier_antecedent (
  dossier_id BIGINT NOT NULL,
  antecedent_id BIGINT NOT NULL,
  niveau_risque VARCHAR(20),
  commentaire TEXT,
  actif BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (dossier_id, antecedent_id),
  FOREIGN KEY (dossier_id) REFERENCES dossier_medical(id),
  FOREIGN KEY (antecedent_id) REFERENCES antecedent(id),
  CONSTRAINT chk_niveau_risque CHECK (niveau_risque IS NULL OR niveau_risque IN ('FAIBLE','MOYEN','ELEVE'))
) ENGINE=InnoDB;

CREATE TABLE acte (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  libelle VARCHAR(150) NOT NULL,
  categorie VARCHAR(60),
  prix_base DECIMAL(12,2) NOT NULL,
  actif BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT chk_acte_prix CHECK (prix_base >= 0)
) ENGINE=InnoDB;

CREATE TABLE medicament (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(150) NOT NULL,
  forme VARCHAR(80),
  actif BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE rdv (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  dossier_id BIGINT NOT NULL,
  medecin_id BIGINT NOT NULL,
  debut DATETIME NOT NULL,
  fin DATETIME NOT NULL,
  motif VARCHAR(255),
  statut VARCHAR(20) NOT NULL DEFAULT 'PLANIFIE',
  arrivee_at DATETIME NULL,
  FOREIGN KEY (dossier_id) REFERENCES dossier_medical(id),
  FOREIGN KEY (medecin_id) REFERENCES utilisateur(id),
  CONSTRAINT chk_rdv_periode CHECK (fin > debut),
  CONSTRAINT chk_rdv_statut CHECK (statut IN ('PLANIFIE','CONFIRME','ARRIVE','EN_COURS','TERMINE','ANNULE','ABSENT')),
  INDEX idx_rdv_medecin (medecin_id, debut, fin)
) ENGINE=InnoDB;

CREATE TABLE indisponibilite (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  medecin_id BIGINT NOT NULL,
  debut DATETIME NOT NULL,
  fin DATETIME NOT NULL,
  motif VARCHAR(150),
  FOREIGN KEY (medecin_id) REFERENCES utilisateur(id),
  CONSTRAINT chk_indispo_periode CHECK (fin > debut)
) ENGINE=InnoDB;

CREATE TABLE consultation (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  dossier_id BIGINT NOT NULL,
  medecin_id BIGINT NOT NULL,
  rdv_id BIGINT NULL UNIQUE,
  date_consultation DATETIME NOT NULL,
  observation TEXT,
  statut VARCHAR(20) NOT NULL DEFAULT 'BROUILLON',
  FOREIGN KEY (dossier_id) REFERENCES dossier_medical(id),
  FOREIGN KEY (medecin_id) REFERENCES utilisateur(id),
  FOREIGN KEY (rdv_id) REFERENCES rdv(id),
  CONSTRAINT chk_consultation_statut CHECK (statut IN ('BROUILLON','VALIDEE'))
) ENGINE=InnoDB;

CREATE TABLE intervention (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  consultation_id BIGINT NOT NULL,
  acte_id BIGINT NOT NULL,
  libelle_acte_snapshot VARCHAR(150) NOT NULL,
  dent VARCHAR(10) NULL,
  quantite INT NOT NULL,
  prix_unitaire DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (consultation_id) REFERENCES consultation(id),
  FOREIGN KEY (acte_id) REFERENCES acte(id),
  CONSTRAINT chk_interv_qte CHECK (quantite > 0),
  CONSTRAINT chk_interv_prix CHECK (prix_unitaire >= 0)
) ENGINE=InnoDB;

CREATE TABLE ordonnance (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  consultation_id BIGINT NOT NULL,
  date_emission DATETIME NULL,
  statut VARCHAR(20) NOT NULL DEFAULT 'BROUILLON',
  FOREIGN KEY (consultation_id) REFERENCES consultation(id),
  CONSTRAINT chk_ordonnance_statut CHECK (statut IN ('BROUILLON','EMISE'))
) ENGINE=InnoDB;

CREATE TABLE prescription (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  ordonnance_id BIGINT NOT NULL,
  medicament_id BIGINT NOT NULL,
  nom_medicament_snapshot VARCHAR(150) NOT NULL,
  quantite INT NOT NULL,
  frequence VARCHAR(150) NOT NULL,
  duree_jours INT NOT NULL,
  FOREIGN KEY (ordonnance_id) REFERENCES ordonnance(id),
  FOREIGN KEY (medicament_id) REFERENCES medicament(id),
  CONSTRAINT chk_presc_qte CHECK (quantite > 0),
  CONSTRAINT chk_presc_duree CHECK (duree_jours > 0)
) ENGINE=InnoDB;

CREATE TABLE certificat (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  consultation_id BIGINT NOT NULL,
  date_debut DATE NOT NULL,
  date_fin DATE NOT NULL,
  motif TEXT,
  date_emission DATETIME NULL,
  statut VARCHAR(20) NOT NULL DEFAULT 'BROUILLON',
  FOREIGN KEY (consultation_id) REFERENCES consultation(id),
  CONSTRAINT chk_certif_dates CHECK (date_fin >= date_debut),
  CONSTRAINT chk_certif_statut CHECK (statut IN ('BROUILLON','EMIS'))
) ENGINE=InnoDB;

CREATE TABLE facture (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  consultation_id BIGINT NOT NULL UNIQUE,
  numero VARCHAR(40) NOT NULL UNIQUE,
  date_facture DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(12,2) NOT NULL DEFAULT 0,
  statut VARCHAR(20) NOT NULL DEFAULT 'BROUILLON',
  motif_annulation VARCHAR(255),
  FOREIGN KEY (consultation_id) REFERENCES consultation(id),
  CONSTRAINT chk_facture_total CHECK (total >= 0),
  CONSTRAINT chk_facture_statut CHECK (statut IN ('BROUILLON','VALIDEE','ANNULEE'))
) ENGINE=InnoDB;

CREATE TABLE paiement (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  facture_id BIGINT NOT NULL,
  auteur_id BIGINT NOT NULL,
  reference CHAR(36) NOT NULL UNIQUE,
  date_paiement DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  montant DECIMAL(12,2) NOT NULL,
  mode VARCHAR(20) NOT NULL,
  FOREIGN KEY (facture_id) REFERENCES facture(id),
  FOREIGN KEY (auteur_id) REFERENCES utilisateur(id),
  CONSTRAINT chk_paiement_montant CHECK (montant > 0),
  CONSTRAINT chk_paiement_mode CHECK (mode IN ('ESPECES','CARTE','CHEQUE','VIREMENT','AUTRE'))
) ENGINE=InnoDB;

CREATE TABLE charge (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  auteur_id BIGINT NOT NULL,
  titre VARCHAR(150) NOT NULL,
  description VARCHAR(255),
  montant DECIMAL(12,2) NOT NULL,
  date_charge DATETIME NOT NULL,
  FOREIGN KEY (auteur_id) REFERENCES utilisateur(id),
  CONSTRAINT chk_charge_montant CHECK (montant > 0)
) ENGINE=InnoDB;