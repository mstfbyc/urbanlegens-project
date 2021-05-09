package com.urbanlegends.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.urbanlegends.shared.Views;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User implements UserDetails {

	private static final long serialVersionUID = -4583395183396431918L;

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	@NotNull(message = "{urbanlegend.constraints.username.NotNull.message}")
	@Column(name = "user_name")
	@UniqueUsername
	@Size(min = 4, max = 30)
	@JsonView(Views.Public.class)
	private String username;

	@NotNull(message = "{urbanlegend.constraints.displayName.NotNull.message}")
	@Column(name = "display_name")
	@Size(min = 4, max = 30)
	@JsonView(Views.Public.class)
	private String displayName;

	@NotNull(message = "{urbanlegend.constraints.password.NotNull.message}")
	@Column(name = "password")
	@Size(min=8,max=12)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$" ,message = "{urbanlegend.constraints.password.pattern.message}")
	private String password;

	@Column(name="image")
	@JsonView(Views.Public.class)
	private String image;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
