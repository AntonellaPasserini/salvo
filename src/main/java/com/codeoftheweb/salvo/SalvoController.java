package com.codeoftheweb.salvo;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
     GameRepository repo;
    @Autowired
    GamePlayerRepositoy gpRepo;
    @Autowired
    PlayerRepository pRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String userName, @RequestParam String pwd) {
        if (userName.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No name"), HttpStatus.FORBIDDEN);
        }
        Player user = pRepo.findByUserName(userName);
        if (user != null) {
            return new ResponseEntity<>(makeMap("error", "Name in use"), HttpStatus.CONFLICT);
        }

        Player newPlayer = pRepo.save(new Player(userName, passwordEncoder.encode(pwd)));
        return new ResponseEntity<>(makeMap("id", newPlayer.getId()) , HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }


    @RequestMapping("/games")
    public Map<String, Object> getGameData( Authentication authentication) {

        Map<String, Object> gamesInfo = new LinkedHashMap<>();

        if (isGuest(authentication)){
            gamesInfo.put("current_user", "Guest");
        } else {
            Player ply = pRepo.findByUserName(authentication.getName());

            gamesInfo.put("current_user", ply.getUserDetails());
        }
        List<Map<String, Object>> gameList = repo.findAll().stream().map(e -> e.getJson()).collect(Collectors.toList());

        gamesInfo.put("games_list",gameList);

        return  gamesInfo;
    }
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping(path= "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame (Authentication authentication){
        if(isGuest(authentication)){
            return new ResponseEntity<>(createMap("error","You must login!"), HttpStatus.FORBIDDEN);
        }else{
            Game game = new Game(10);
            repo.save(game);
            Player ply = pRepo.findByUserName(authentication.getName());
            GamePlayer gPlayer = new GamePlayer(10, ply,game );
            gpRepo.save(gPlayer);
            return new ResponseEntity<>(createMap("gpid", gPlayer.getId()), HttpStatus.CREATED);
        }
    }

    @RequestMapping(path= "/games/{id}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame (Authentication authentication,
                                                           @PathVariable Long id){
        if(isGuest(authentication)){
            return new ResponseEntity<>(createMap("error","You must login!"), HttpStatus.UNAUTHORIZED);
        }
        Game gamecito = repo.findById(id).orElse(null);
        if(gamecito == null){
            return new ResponseEntity<>(createMap("error","Game doesn't exist"), HttpStatus.FORBIDDEN);
        }
        if(gamecito.getGamePlayers().size() >= 2){
            return new ResponseEntity<>(createMap("error","Game is full"), HttpStatus.FORBIDDEN);
        }
        Player ply = pRepo.findByUserName(authentication.getName());
        if(gamecito.getGamePlayers().stream().map(
                gamePlayer -> gamePlayer.getPlayer().getUserName()).collect(Collectors.toList()).contains(ply.getUserName())){
            return new ResponseEntity<>(createMap("error","You're already in"), HttpStatus.FORBIDDEN);
        }
        GamePlayer gPlayer = new GamePlayer(10, ply,gamecito);
        gpRepo.save(gPlayer);
        return new ResponseEntity<>(createMap("gpid", gPlayer.getId()), HttpStatus.CREATED);
    }
    public Map<String, Object> createMap (String str, Object obj){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(str, obj);
        return map;
    }

    @RequestMapping("/leaderboard")
    public List<Object> getLeaderboardList() {

        return pRepo.findAll().stream().map(e -> e.getLeaderBoardDTO()).collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{nn}")
    public ResponseEntity<Map<String,Object>> getPlayerIdData(@PathVariable Long nn, Authentication authentication) {
        //GamePlayer gamePlayer = pRepo.findById(nn).get();
        if(isGuest(authentication)){
            return new ResponseEntity<>(createMap("error","You must login!"), HttpStatus.UNAUTHORIZED);
        }



        return new ResponseEntity<>(gpRepo.getOne(nn).getGameViewJson(),HttpStatus.ACCEPTED);
    }


}






