-- Version optimisée sans table reservations

CREATE DATABASE IF NOT EXISTS gestion_rendez_vous;
USE gestion_rendez_vous;

-- Suppression des tables dans le bon ordre
DROP TABLE IF EXISTS rendez_vous;
DROP TABLE IF EXISTS time_slots;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS docteurs;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS utilisateurs;
DROP TABLE IF EXISTS salles;

-- Table pour les salles
CREATE TABLE salles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    etage INT NOT NULL,
    capacite INT NOT NULL,
    UNIQUE KEY salle_unique (numero, etage)
);

-- Table utilisateur de base
CREATE TABLE utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    date_naissance DATE,
    mot_de_passe VARCHAR(255) NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    derniere_connexion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table pour les rôles
CREATE TABLE roles (
    utilisateur_id INT PRIMARY KEY,
    role ENUM('PATIENT', 'DOCTEUR', 'SCHEDULER', 'ADMIN') NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Table patient
CREATE TABLE patients (
    utilisateur_id INT PRIMARY KEY,
    numero_secu VARCHAR(20) UNIQUE,
    dossier_medical TEXT,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Table docteur
CREATE TABLE docteurs (
    utilisateur_id INT PRIMARY KEY,
    specialite VARCHAR(100) NOT NULL,
    matricule VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Table pour les créneaux horaires
CREATE TABLE time_slots (
    id INT AUTO_INCREMENT PRIMARY KEY,
    debut DATETIME NOT NULL,
    fin DATETIME NOT NULL,
    statut ENUM('DISPONIBLE', 'RESERVE', 'INDISPONIBLE') NOT NULL DEFAULT 'DISPONIBLE',
    UNIQUE KEY creneau_unique (debut, fin)
);

-- Table pour les schedulers
CREATE TABLE schedulers (
    utilisateur_id INT PRIMARY KEY,
    fonction VARCHAR(100) NOT NULL,
	FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Table pour les rendez-vous (intègre les informations de réservation)
CREATE TABLE rendez_vous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('PLANIFIE', 'CONFIRME', 'EN_COURS', 'TERMINE', 'ANNULE', 'REPORTE') NOT NULL DEFAULT 'PLANIFIE',
    commentaire TEXT,
    duree INT NOT NULL DEFAULT 30,
    patient_id INT NOT NULL,
    time_slot_id INT NOT NULL,
    salle_id INT,
    scheduler_id INT,
    docteur_id INT,
    FOREIGN KEY (patient_id) REFERENCES patients(utilisateur_id) ON DELETE CASCADE,
    FOREIGN KEY (time_slot_id) REFERENCES time_slots(id) ON DELETE CASCADE,
    FOREIGN KEY (salle_id) REFERENCES salles(id) ON DELETE SET NULL,
    FOREIGN KEY (scheduler_id) REFERENCES schedulers(utilisateur_id) ON DELETE SET NULL,
    FOREIGN KEY (docteur_id) REFERENCES docteurs(utilisateur_id) ON DELETE SET NULL
);

-- Index pour optimiser les performances
CREATE INDEX idx_utilisateur_nom_prenom ON utilisateurs(nom, prenom);
CREATE INDEX idx_docteur_specialite ON docteurs(specialite);
CREATE INDEX idx_time_slot_dates ON time_slots(debut, fin);
CREATE INDEX idx_rendez_vous_statut ON rendez_vous(statut);
CREATE INDEX idx_rendez_vous_patient ON rendez_vous(patient_id);