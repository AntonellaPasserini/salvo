package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name= "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations;

    public Salvo(){}

    public Salvo(GamePlayer gp, int tn, List <String> locations){
        this.gamePlayer=gp;
        this.turnNumber= tn;
        this.locations= locations;
    }

    public long getId(){
        return id;
    }

    public List getLocations() {
        return locations;
    }

    private int turnNumber ;

    public  int  GetTurnNumber() { return turnNumber ;}

    public GamePlayer getGamePlayers() { return gamePlayer ;}

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
}
