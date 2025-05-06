package models;
import java.time.LocalDateTime;

public class Salle {
    private int id;
    private int numero;
    private int etage;
    private int capacite;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public boolean estDisponible(LocalDateTime debut, LocalDateTime fin) {
        // Logique pour vérifier la disponibilité
        return true;
    }
}
