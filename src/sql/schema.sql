-- Création de la base de données
CREATE DATABASE IF NOT EXISTS gestion_rendez_vous;
USE gestion_rendez_vous;

-- Suppression des tables (si elles existent) dans l'ordre inverse de création
DROP TABLE IF EXISTS rendez_vous;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS time_slots;
DROP TABLE IF EXISTS schedulers;
DROP TABLE IF EXISTS docteurs;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS personnes;
DROP TABLE IF EXISTS salles;

-- Table pour les salles
CREATE TABLE salles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    etage INT NOT NULL,
    capacite INT NOT NULL,
    UNIQUE KEY salle_unique (numero, etage)
);

-- Table personne
CREATE TABLE personnes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    date_naissance DATETIME,
    type_personne ENUM('PATIENT', 'DOCTEUR', 'SCHEDULER', 'ADMIN') NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    derniere_connexion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table patient
CREATE TABLE patients (
    id INT PRIMARY KEY,
    numero_secu VARCHAR(20) UNIQUE,
    dossier_medical TEXT,
    FOREIGN KEY (id) REFERENCES personnes(id) ON DELETE CASCADE
);

-- Table docteur
CREATE TABLE docteurs (
    id INT PRIMARY KEY,
    specialite VARCHAR(100) NOT NULL,
    matricule VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (id) REFERENCES personnes(id) ON DELETE CASCADE
);

-- Table scheduler
CREATE TABLE schedulers (
    id INT PRIMARY KEY,
    fonction VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES personnes(id) ON DELETE CASCADE
);

-- Table pour les créneaux horaires
CREATE TABLE time_slots (
    id INT AUTO_INCREMENT PRIMARY KEY,
    debut DATETIME NOT NULL,
    fin DATETIME NOT NULL,
    statut ENUM('DISPONIBLE', 'RESERVE', 'INDISPONIBLE') NOT NULL DEFAULT 'DISPONIBLE',
    docteur_id INT NOT NULL,
    FOREIGN KEY (docteur_id) REFERENCES docteurs(id) ON DELETE CASCADE,
    UNIQUE KEY creneau_unique (debut, fin, docteur_id)
);

-- Table pour les réservations
CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('EN_ATTENTE', 'CONFIRMEE', 'ANNULEE') NOT NULL DEFAULT 'EN_ATTENTE',
    patient_id INT NOT NULL,
    time_slot_id INT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (time_slot_id) REFERENCES time_slots(id) ON DELETE CASCADE
);

-- Table pour les rendez-vous
CREATE TABLE rendez_vous (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('PLANIFIE', 'CONFIRME', 'EN_COURS', 'TERMINE', 'ANNULE', 'REPORTE') NOT NULL DEFAULT 'PLANIFIE',
    commentaire TEXT,
    duree INT NOT NULL DEFAULT 30,
    reservation_id INT NOT NULL,
    salle_id INT,
    docteur_id INT,
    FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE,
    FOREIGN KEY (salle_id) REFERENCES salles(id) ON DELETE SET NULL,
    FOREIGN KEY (docteur_id) REFERENCES docteurs(id) ON DELETE SET NULL
);

-- Ajout d'index pour optimiser les recherches
CREATE INDEX idx_personne_nom_prenom ON personnes(nom, prenom);
CREATE INDEX idx_docteur_specialite ON docteurs(specialite);
CREATE INDEX idx_time_slot_dates ON time_slots(debut, fin);
CREATE INDEX idx_rendez_vous_statut ON rendez_vous(statut);
CREATE INDEX idx_reservation_patient ON reservation(patient_id);
CREATE INDEX idx_reservation_statut ON reservation(statut);