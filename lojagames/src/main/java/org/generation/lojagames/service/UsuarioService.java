package org.generation.lojagames.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.lojagames.model.Usuario;
import org.generation.lojagames.model.UsuarioLogin;
import org.generation.lojagames.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service //indica que é uma classe de serviço 
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Usuario> cadastrarUsuario(Usuario usuarios) {

		if (usuarioRepository.findByUsuario(usuarios.getUsuario()).isPresent()) {
			return Optional.empty();
		}

		usuarios.setSenha(criptografarSenha(usuarios.getSenha()));
		return Optional.of(usuarioRepository.save(usuarios));
	}
	
	public Optional<Usuario> AtualizarUsuario(Usuario usuario){

	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String senhaEconder = encoder.encode(usuario.getSenha());

	    usuario.setSenha(senhaEconder);

	    return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		Optional<Usuario> usuarios = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario()); //realiza a pesquisa na tb_usuarios

		if (usuarios.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuarios.get().getSenha())) {	//compara a senha criptografada com a senha que o usuario digitou, se for igual retorna um true
				usuarioLogin.get().setId(usuarios.get().getId());
				usuarioLogin.get().setNome(usuarios.get().getNome());
				usuarioLogin.get().setFoto(usuarios.get().getFoto());
				usuarioLogin.get().setSenha(usuarios.get().getSenha());
				usuarioLogin.get().setToken(geradorBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				
				return usuarioLogin;
			}
			
		}

		return Optional.empty();

	}

	private boolean compararSenhas(String senhaDigitada, String senhaDoBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaDoBanco);

	}

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // recebe a senha e criptografa

		return encoder.encode(senha);

	}

	private String geradorBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));

		return "Basic " + new String(tokenBase64);

	}

}
