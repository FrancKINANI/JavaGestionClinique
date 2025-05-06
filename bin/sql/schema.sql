-- Création de la base de données
CREATE DATABASE IF NOT EXISTS gestion_rendez_vous;
USE gestion_rendez_vous;

-- Suppression des tables (si elles existent) dans l'ordre inverse de création pour éviter les erreurs de clé étrangère
DROP TABLE IF EXISTS rendez_vous;
DROP TABLE IF EXISTS reservation;  -- Correction ici
DROP TABLE IF EXISTS time_slots;   -- Correction ici
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

-- Table personne (abstraite dans le modèle UML)
CREATE TABLE personnes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    date_naissance DATETIME,
    type_personne ENUM('PATIENT', 'DOCTEUR', 'SCHEDULER') NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,  -- Ajout pour la connexion
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    derniere_connexion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table patient (hérite de personne)
CREATE TABLE patients (
    id INT PRIMARY KEY,
    numero_secu VARCHAR(20) UNIQUE,
    dossier_medical TEXT,
    FOREIGN KEY (id) REFERENCES personnes(id) ON DELETE CASCADE
);

-- Table docteur (hérite de personne)
CREATE TABLE docteurs (
    id INT PRIMARY KEY,
    specialite VARCHAR(100) NOT NULL,
    matricule VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (id) REFERENCES personnes(id) ON DELETE CASCADE
);

-- Table scheduler (hérite de personne)
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
    duree INT NOT NULL DEFAULT 30, -- durée en minutes
    reservation_id INT NOT NULL,
    salle_id INT,
    FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE,
    FOREIGN KEY (salle_id) REFERENCES salles(id) ON DELETE SET NULL
);

-- Ajout d'index pour optimiser les recherches
CREATE INDEX idx_personne_nom_prenom ON personnes(nom, prenom);
CREATE INDEX idx_docteur_specialite ON docteurs(specialite);
CREATE INDEX idx_time_slot_dates ON time_slots(debut, fin);
CREATE INDEX idx_rendez_vous_statut ON rendez_vous(statut);
CREATE INDEX idx_reservation_patient ON reservation(patient_id);  -- Correction ici
CREATE INDEX idx_reservation_statut ON reservation(statut);      -- Correction ici

-- Création de vues pour faciliter l'accès aux données

-- Vue pour les rendez-vous avec informations détaillées
CREATE VIEW v_rendez_vous_detailles AS
SELECT 
    rdv.id AS rdv_id,
    rdv.date_creation AS rdv_date_creation,
    rdv.statut AS rdv_statut,
    rdv.commentaire,
    rdv.duree,
    ts.debut AS date_debut,
    ts.fin AS date_fin,
    p_patient.nom AS patient_nom,
    p_patient.prenom AS patient_prenom,
    p_patient.numero_secu,
    p_docteur.nom AS docteur_nom,
    p_docteur.prenom AS docteur_prenom,
    d.specialite,
    s.numero AS salle_numero,
    s.etage AS salle_etage
FROM 
    rendez_vous rdv
    JOIN reservation r ON rdv.reservation_id = r.id
    JOIN time_slots ts ON r.time_slot_id = ts.id
    JOIN patients p_patient ON r.patient_id = p_patient.id
    JOIN docteurs p_docteur ON ts.docteur_id = p_docteur.id
    LEFT JOIN salles s ON rdv.salle_id = s.id;

-- Vue pour le planning des docteurs
CREATE VIEW v_planning_docteurs AS
SELECT 
    d.id AS docteur_id,
    p.nom AS docteur_nom,
    p.prenom AS docteur_prenom,
    d.specialite,
    ts.id AS time_slot_id,
    ts.debut,
    ts.fin,
    ts.statut AS creneau_statut,
    rdv.id AS rdv_id,
    rdv.statut AS rdv_statut,
    p_patient.nom AS patient_nom,
    p_patient.prenom AS patient_prenom
FROM 
    docteurs d
    JOIN personnes p ON d.id = p.id
    JOIN time_slots ts ON d.id = ts.docteur_id
    LEFT JOIN reservation r ON ts.id = r.time_slot_id
    LEFT JOIN rendez_vous rdv ON r.id = rdv.reservation_id
    LEFT JOIN patients p_patient ON r.patient_id = p_patient.id
ORDER BY 
    d.id, ts.debut;

-- Vue pour les disponibilités des salles
CREATE VIEW v_disponibilite_salles AS
SELECT 
    s.id AS salle_id,
    s.numero,
    s.etage,
    s.capacite,
    rdv.id AS rdv_id,
    ts.debut,
    ts.fin,
    rdv.statut
FROM 
    salles s  -- Correction ici
    LEFT JOIN rendez_vous rdv ON s.id = rdv.salle_id
    LEFT JOIN reservation r ON rdv.reservation_id = r.id
    LEFT JOIN time_slots ts ON r.time_slot_id = ts.id
ORDER BY 
    s.numero, s.etage, ts.debut;

-- Procédures stockées pour les opérations courantes

-- Procédure pour créer un rendez-vous
CREATE PROCEDURE sp_creer_rendez_vous(
    IN p_patient_id INT,
    IN p_time_slot_id INT,
    IN p_salle_id INT,
    OUT p_rdv_id INT
)
BEGIN
    DECLARE v_reservation_id INT;
    
    -- Mise à jour du créneau horaire
    UPDATE time_slots SET statut = 'RESERVE' WHERE id = p_time_slot_id;
    
    -- Création de la réservation
    INSERT INTO reservation (patient_id, time_slot_id, statut)
    VALUES (p_patient_id, p_time_slot_id, 'CONFIRMEE');
    
    SET v_reservation_id = LAST_INSERT_ID();
    
    -- Création du rendez-vous
    INSERT INTO rendez_vous (reservation_id, salle_id, statut)
    VALUES (v_reservation_id, p_salle_id, 'PLANIFIE');
    
    SET p_rdv_id = LAST_INSERT_ID();
    
    SELECT p_rdv_id;
END;

-- Procédure pour annuler un rendez-vous
CREATE PROCEDURE sp_annuler_rendez_vous(
    IN p_rdv_id INT,
    IN p_commentaire VARCHAR(255)
)
BEGIN
    DECLARE v_time_slot_id INT;
    DECLARE v_reservation_id INT;
    
    -- Récupérer l'ID du time_slot associé au rendez-vous
    SELECT r.time_slot_id, rdv.reservation_id 
    INTO v_time_slot_id, v_reservation_id
    FROM rendez_vous rdv
    JOIN reservation r ON rdv.reservation_id = r.id
    WHERE rdv.id = p_rdv_id;
    
    -- Mettre à jour le statut du rendez-vous
    UPDATE rendez_vous 
    SET statut = 'ANNULE', commentaire = CONCAT(IFNULL(commentaire, ''), ' | ', p_commentaire)
    WHERE id = p_rdv_id;
    
    -- Mettre à jour le statut de la réservation
    UPDATE reservation SET statut = 'ANNULEE' WHERE id = v_reservation_id;
    
    -- Libérer le créneau horaire
    UPDATE time_slots SET statut = 'DISPONIBLE' WHERE id = v_time_slot_id;
END;

-- Procédure pour rechercher des créneaux disponibles
CREATE PROCEDURE sp_rechercher_creneaux_disponibles(
    IN p_specialite VARCHAR(100),
    IN p_date_debut DATETIME,
    IN p_date_fin DATETIME
)
BEGIN
    SELECT 
        ts.id AS time_slot_id,
        ts.debut,
        ts.fin,
        p.nom AS docteur_nom,
        p.prenom AS docteur_prenom,
        d.specialite
    FROM 
        time_slots ts
        JOIN docteurs d ON ts.docteur_id = d.id
        JOIN personnes p ON d.id = p.id
    WHERE 
        ts.statut = 'DISPONIBLE'
        AND ts.debut >= p_date_debut
        AND ts.fin <= p_date_fin
        AND (p_specialite IS NULL OR d.specialite = p_specialite)
    ORDER BY 
        ts.debut;
END;

-- Procédure pour récupérer les informations d'un rendez-vous
CREATE PROCEDURE sp_recuperer_rendez_vous(
    IN p_rdv_id INT
)
BEGIN
    SELECT 
        rdv.id AS rdv_id,
        rdv.date_creation AS rdv_date_creation,
        rdv.statut AS rdv_statut,
        rdv.commentaire,
        rdv.duree,
        ts.debut AS date_debut,
        ts.fin AS date_fin,
        p_patient.nom AS patient_nom,
        p_patient.prenom AS patient_prenom,
        p_patient.numero_secu,
        p_docteur.nom AS docteur_nom,
        p_docteur.prenom AS docteur_prenom,
        d.specialite,
        s.numero AS salle_numero,
        s.etage AS salle_etage
    FROM 
        rendez_vous rdv
        JOIN reservation r ON rdv.reservation_id = r.id
        JOIN time_slots ts ON r.time_slot_id = ts.id
        JOIN patients p_patient ON r.patient_id = p_patient.id
        JOIN docteurs p_docteur ON ts.docteur_id = p_docteur.id
        LEFT JOIN salles s ON rdv.salle_id = s.id
    WHERE 
        rdv.id = p_rdv_id;
END;