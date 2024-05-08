package ca.sheridancollege.andres.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
	private Long userId;
	@NonNull
	private String email;
	@NonNull
	private String password;
	@NonNull
	private Boolean enabled;
}
