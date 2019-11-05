//this proyect belongs to Antonella
package com.codeoftheweb.salvo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
@Bean
public CommandLineRunner initData(PlayerRepository PlayerRep, GameRepository GameRep, GamePlayerRepositoy GPRep, ShipRepository shRep, SalvoRepository salvoesRepo, ScoreRepository scRep ) {
	return (args) -> {
		// save a couple of customers
		Player player1 = new Player("antoto@ctu.gov", passwordEncoder().encode("24"));
		Player player2 = new Player("ccaroto@ctu.gov",passwordEncoder().encode("42"));
		Player player3 = new Player("maruchan@gmail.com",passwordEncoder().encode("kb"));
		Player player4 = new Player("pawbi@ctu.gov",passwordEncoder().encode("mole"));

		PlayerRep.save(player1);
		PlayerRep.save(player2);
		PlayerRep.save(player3);
		PlayerRep.save(player4);


		Game game1 = new Game(0);
		Game game2 = new Game(1);
		Game game3 = new Game(2);
		Game game4 = new Game(3);
		Game game5 = new Game(4);
		Game game6 = new Game(5);
		Game game7 = new Game(6);
		Game game8 = new Game(7);


		GameRep.save(game1);
		GameRep.save(game2);
		GameRep.save(game3);
		GameRep.save(game4);
		GameRep.save(game5);
		GameRep.save(game6);
		GameRep.save(game7);
		GameRep.save(game8);



		GamePlayer gamePlayer1 = new GamePlayer(0, player1,game1);
		GamePlayer gamePlayer2 = new GamePlayer(0, player2,game1);
		GamePlayer gamePlayer3 = new GamePlayer(1, player1,game2);
		GamePlayer gamePlayer4 = new GamePlayer(1, player2,game2);
		GamePlayer gamePlayer5 = new GamePlayer(2, player2,game3);
		GamePlayer gamePlayer6 = new GamePlayer(2, player4,game3);
		GamePlayer gamePlayer7 = new GamePlayer(3, player2,game4);
		GamePlayer gamePlayer8 = new GamePlayer(3, player1,game4);
		GamePlayer gamePlayer9 = new GamePlayer(4, player4,game5);
		GamePlayer gamePlayer10 = new GamePlayer(4, player1,game5);
		GamePlayer gamePlayer11 = new GamePlayer(5, player3,game6);
		GamePlayer gamePlayer12 = new GamePlayer(6, player4,game7);
		GamePlayer gamePlayer13= new GamePlayer(7, player3,game8);
		GamePlayer gamePlayer14= new GamePlayer(7, player4,game8);

		GPRep.save(gamePlayer1);
		GPRep.save(gamePlayer2);
		GPRep.save(gamePlayer3);
		GPRep.save(gamePlayer4);
		GPRep.save(gamePlayer5);
		GPRep.save(gamePlayer6);
		GPRep.save(gamePlayer7);
		GPRep.save(gamePlayer8);
		GPRep.save(gamePlayer9);
		GPRep.save(gamePlayer10);
		GPRep.save(gamePlayer11);
		GPRep.save(gamePlayer12);
		GPRep.save(gamePlayer13);
		GPRep.save(gamePlayer14);


		Ship ship1P1G1 = new Ship("Submarine", Arrays.asList("H2", "H3", "H4"), gamePlayer1);
		Ship ship2P1G1 = new Ship("Patrol Boat", Arrays.asList("B4", "B5"), gamePlayer1);
		Ship ship1P2G1 = new Ship("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer2);
		Ship ship2P2G1 = new Ship("Patrol Boat", Arrays.asList("F1", "F2"), gamePlayer2);
		Ship ship1P1G2 = new Ship("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer3);
		Ship ship2P1G2 = new Ship("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer3);
		Ship ship1P2G2 = new Ship("Submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer4);
		Ship ship2P2G2 = new Ship("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer4);
		Ship ship1P2G3 = new Ship("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer5);
		Ship ship2P2G3 = new Ship("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer5);
		Ship ship1P4G3 = new Ship("Submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer6);
		Ship ship2P4G3 = new Ship("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer6);
		Ship ship1P2G4 = new Ship("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer7);
		Ship ship2P2G4 = new Ship("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer7);
		Ship ship1P1G4 = new Ship("Submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer8);
		Ship ship2P1G4 = new Ship("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer8);
		Ship ship1P4G5 = new Ship("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer9);
		Ship ship2P4G5 = new Ship("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer9);
		Ship ship1P1G5 = new Ship("Submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer10);
		Ship ship2P1G5 = new Ship("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer10);
		Ship ship1P3G6 = new Ship("Destroyer", Arrays.asList("B5", "C5", "D5"), gamePlayer11);
		Ship ship2P3G6 = new Ship("Patrol Boat", Arrays.asList("C6", "C7"),gamePlayer11);
		Ship ship1P3G8 = new Ship("Destroyer", Arrays.asList("B5", "C5", "D5"),gamePlayer12);
		Ship ship2P3G8 = new Ship("Patrol Boat", Arrays.asList("C6", "C7"), gamePlayer12);
		Ship ship1P4G8 = new Ship("Submarine", Arrays.asList("A2", "A3", "A4"), gamePlayer13);
		Ship ship2P4G8 = new Ship("Patrol Boat", Arrays.asList("G6", "H6"), gamePlayer13);

		List<Ship> testShips = Arrays.asList(ship1P1G1, ship2P1G1, ship1P2G1, ship2P2G1, ship1P1G2, ship2P1G2,
				ship1P2G2, ship2P2G2, ship1P2G3, ship2P2G3, ship1P4G3, ship2P4G3, ship1P2G4, ship2P2G4, ship1P1G4,
				ship2P1G4, ship1P4G5, ship2P4G5, ship1P1G5, ship2P1G5, ship1P3G6, ship2P3G6, ship1P3G8, ship2P3G8,
				ship1P4G8, ship2P4G8);

		shRep.saveAll(testShips);

		Salvo salvoP1G1T1 = new Salvo( gamePlayer1,1, Arrays.asList("B5", "C5", "F1"));
		Salvo salvoP1G1T2 = new Salvo(gamePlayer1,2, Arrays.asList("F2", "D5"));
		Salvo salvoP2G1T1 = new Salvo(gamePlayer2,1, Arrays.asList("B4", "B5", "B6"));
		Salvo salvoP2G1T2 = new Salvo( gamePlayer2,2, Arrays.asList("E1", "H3", "A2"));
		Salvo salvoP1G2T1 = new Salvo(gamePlayer3,1, Arrays.asList("A2", "A4", "G6"));
		Salvo salvoP1G2T2 = new Salvo(gamePlayer3,2, Arrays.asList("A3", "H6"));
		Salvo salvoP2G2T1 = new Salvo(gamePlayer4,1, Arrays.asList("B5", "D5", "C7"));
		Salvo salvoP2G2T2 = new Salvo(gamePlayer4,2, Arrays.asList("C5", "C6"));
		Salvo salvoP2G3T1 = new Salvo( gamePlayer5,1, Arrays.asList("G6", "H6", "A4"));
		Salvo salvoP2G3T2 = new Salvo(gamePlayer5,2, Arrays.asList("A2", "A3", "D8"));
		Salvo salvoP4G3T1 = new Salvo(gamePlayer6,1, Arrays.asList("H1", "H2", "H3"));
		Salvo salvoP4G3T2 = new Salvo(gamePlayer6,2, Arrays.asList("E1", "F2", "G3"));
		Salvo salvoP2G4T1 = new Salvo(gamePlayer7,1, Arrays.asList("A3", "A4", "F7"));
		Salvo salvoP2G4T2 = new Salvo(gamePlayer7,2, Arrays.asList("A2", "G6", "H6"));
		Salvo salvoP1G4T1 = new Salvo(gamePlayer8,1, Arrays.asList("B5", "C6", "H1") );
		Salvo salvoP1G4T2 = new Salvo(gamePlayer8,2, Arrays.asList("C5", "C7", "D5"));
		Salvo salvoP4G5T1 = new Salvo(gamePlayer9,1, Arrays.asList("A1", "A2", "A3"));
		Salvo salvoP4G5T2 = new Salvo(gamePlayer9,2, Arrays.asList("G6", "G7", "G8"));
		Salvo salvoP1G5T1 = new Salvo(gamePlayer10,1, Arrays.asList("B5", "B6", "C7"));
		Salvo salvoP1G5T2 = new Salvo( gamePlayer10,2, Arrays.asList("C6", "D6", "E6"));
		Salvo salvoP1G5T3 = new Salvo(gamePlayer10,3, Arrays.asList("H1", "H8"));

		List<Salvo> testSalvoes = Arrays.asList(salvoP1G1T1, salvoP1G1T2, salvoP2G1T1, salvoP2G1T2, salvoP1G2T1,
				salvoP1G2T2, salvoP2G2T1, salvoP2G2T2, salvoP2G3T1, salvoP2G3T2, salvoP4G3T1, salvoP4G3T2,
				salvoP2G4T1, salvoP2G4T2, salvoP1G4T1, salvoP1G4T2, salvoP4G5T1, salvoP4G5T2, salvoP1G5T1,
				salvoP1G5T2, salvoP1G5T3);

		salvoesRepo.saveAll(testSalvoes);

		Score scoreplayer1 = new Score(player1,game1,0.5);
		Score scoreplayer2 = new Score(player1,game2,0);
		Score scoreplayer3 = new Score(player2,game1,0.5);
		Score scoreplayer4 = new Score(player2,game2,1);
		Score scoreplayer5 = new Score(player1,game4,1);
		Score scoreplayer6 = new Score(player2,game4,0);
		Score scoreplayer7 = new Score(player3,game3,0);
		Score scoreplayer8 = new Score(player4,game3,1);




		List<Score> testScores =Arrays.asList(scoreplayer1,scoreplayer2,scoreplayer3,scoreplayer4,scoreplayer5,scoreplayer6,scoreplayer7,scoreplayer8);

		scRep.saveAll(testScores);
		};


	}
}
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository pRepository;



	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = pRepository.findByUserName(inputName);
			if (player != null) {
				return new User(player.getUserName(), player.getPassword(), AuthorityUtils.createAuthorityList("USER"));

			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
						.antMatchers("/admin/**").hasAuthority("ADMIN")
						.antMatchers("/api/logout").hasAuthority("USER")
						.and()
						.formLogin().usernameParameter("userName")
						.passwordParameter("pwd")
						.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");
		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}


}