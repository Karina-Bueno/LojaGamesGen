package org.generation.lojagames.security;

import java.util.Optional;

import org.generation.lojagames.model.Usuario;
import org.generation.lojagames.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired //sobrescrita de método
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Usuario> usuarios = usuarioRepository.findByUsuario(userName);
		
		usuarios.orElseThrow(() -> new UsernameNotFoundException(userName + "Este usuario não foi encontrado"));

		return usuarios.map(UserDetailsImpl::new).get(); //.get para extrair o que tem dentro do onejto  //:: = atribuição
	}

	
}