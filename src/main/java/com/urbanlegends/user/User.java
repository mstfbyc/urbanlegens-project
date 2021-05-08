package com.urbanlegends.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	@NotNull(message = "{urbanlegend.constraints.username.NotNull.message}")
	@Column(name = "user_name")
	@UniqueUsername(message = "{urbanlegend.constraints.username.UniqueUsername.message}")
	@Size(min = 4, max = 30)
	private String username;

	@NotNull(message = "{urbanlegend.constraints.displayName.NotNull.message}")
	@Column(name = "display_name")
	@Size(min = 4, max = 30)
	private String displayName;

	@NotNull(message = "{urbanlegend.constraints.password.NotNull.message}")
	@Column(name = "password")
	@Size(min=8,max=12)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$" ,message = "{urbanlegend.constraints.password.pattern.message}")
	private String password;	


}
