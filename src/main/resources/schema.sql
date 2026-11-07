DROP DATABASE IF EXISTS dentalsoftdb;
CREATE DATABASE dentalsoftdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dentalsoftdb;

CREATE TABLE cabinet (
  id BIGINT PRIMARY KEY,
  nom VARCHAR(120) NOT NULL,
  adresse VARCHAR(255),
  telephone VARCHAR(30),
  email VARCHAR(150),
  logo_path VARCHAR(255)
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
  CONSTRAINT chk_role CHECK (code IN ('ADMIN','MEDECIN','SECRETAIRE'))
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
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE dossier_medical (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT NOT NULL UNIQUE,
  medecin_id BIGINT NOT NULL,
  date_creation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES patient(id),
  FOREIGN KEY (medecin_id) REFERENCES utilisateur(id)
) ENGINE=InnoDB;