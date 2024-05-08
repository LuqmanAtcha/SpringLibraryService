package ca.sheridancollege.andres;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import ca.sheridancollege.andres.security.SecurityConfig;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer
{
	public SecurityWebApplicationInitializer()
	{
		super(SecurityConfig.class);
	}
}
