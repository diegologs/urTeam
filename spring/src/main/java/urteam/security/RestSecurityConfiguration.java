package urteam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserRepositoryAuthenticationProvider userRepoAuthProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");
		
		// URLs that need authentication to access to it
		
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/events/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/events/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/events/**").hasRole("ADMIN");
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/**").hasRole("USER");	
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/stats/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/stats/**").hasRole("USER");
		
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/sports/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/sports/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/sports/**").hasRole("ADMIN");		
			
		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/groups/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/groups/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/groups/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/groups/**").hasRole("ADMIN");
		
		
		
		// Other URLs can be accessed without authentication
		http.authorizeRequests().anyRequest().permitAll();

		// Disable CSRF protection (it is difficult to implement with ng2)
		http.csrf().disable();

		// Use Http Basic Authentication
		http.httpBasic();

		// Do not redirect when logout
		http.logout().logoutSuccessHandler((rq, rs, a) -> {	});
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Database authentication provider
		auth.authenticationProvider(userRepoAuthProvider);
	}
}