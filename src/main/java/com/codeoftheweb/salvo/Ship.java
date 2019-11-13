package com.codeoftheweb.salvo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import java.util.List;


@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


    @ElementCollection
    @Column(name = "locations")
    private List<String> locations;

    public List getLocations() {
        return locations;
    }


    public Ship() { }
    public Ship(String shipType,List <String> locations,GamePlayer gamePlayer){

        this.shipType = shipType;
        this.locations= locations;
        this.gamePlayer= gamePlayer;
    }


    private String shipType;

    public String getShipType() {
        return shipType;
    }

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name= "gamePlayer_id")
    private GamePlayer gamePlayer;

    public GamePlayer getGamePlayers() { return gamePlayer ;}

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}
