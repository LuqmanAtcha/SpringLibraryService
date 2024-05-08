package ca.sheridancollege.andres.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.andres.database.DatabaseAccess;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private DatabaseAccess database;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		ca.sheridancollege.andres.beans.User user=database.findUserAccount(username);
		if(user==null){
			System.out.println("user not found="+username);
			throw new UsernameNotFoundException("user not found="+username);
		}
		else {
			System.out.println("user was found="+username);
		}
		//System.out.println(user.getEmail() + "       " + user.getPassword() + "          " + user.getUserId());
		List<String> roleNameList=database.getRolesById(user.getUserId());
		List<GrantedAuthority> grantList=new ArrayList<GrantedAuthority>();
		if(roleNameList != null)
		{
			for(String role : roleNameList)
			{
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		/*
		for(GrantedAuthority g : grantList) {
			System.out.println("Role: " + g.getAuthority());
		}
		*/
		UserDetails userDetails=new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantList);
		return userDetails;
	}

}
