package com.codeoftheweb.salvo;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import java.util.List;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name= "Player")
    private Player player;
    public Player getplayer() { return player ;}




    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name= "Game")
    private Game game;
    public Game getGame() { return game ;}

    private double puntaje;

    public Score() {
    }

    public Score(Player pun, Game game, double puntaje){
        this.player = pun;
        this.puntaje= puntaje;
        this.game= game;

    }

    public long getId(){
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }
}
