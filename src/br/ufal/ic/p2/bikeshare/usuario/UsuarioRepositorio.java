package br.ufal.ic.p2.bikeshare.usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import br.ufal.ic.p2.bikeshare.exceptions.EmailJaCadastradoException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioIdNuloException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Formatador;

public class UsuarioRepositorio implements Serializable {

    private static final String ARQUIVO_DADOS = "relatorio/usuarios.dat";

    private List<Usuario> usuarios;
    private int proximoId;

    public UsuarioRepositorio() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
    }

    public String gerarProximoId() {
        return "U" + proximoId;
    }

    public void confirmarId() {
        proximoId++;
    }

    public void adicionar(Usuario usuario) {
        validarEmailUnico(usuario.getEmail(), null);
        usuarios.add(usuario);
        proximoId++;
    }

    public void remover(String id) {
        validarId(id);
        Usuario usuario = buscarPorId(id);
        usuarios.remove(usuario);
    }

    public Usuario buscarPorId(String id) {
        validarId(id);
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        throw new UsuarioNaoExisteException();
    }

    public Usuario buscarPorEmail(String email) {
        if (Formatador.estaVazio(email)) {
            throw new IllegalArgumentException("Email nao pode ser nulo.");
        }
        for (Usuario u : usuarios) {
            if (u.temEmail(email)) {
                return u;
            }
        }
        throw new UsuarioNaoExisteException();
    }

    public boolean emailJaCadastrado(String email) {
        return emailJaCadastradoExceto(email, null);
    }

    public boolean emailJaCadastradoExceto(String email, String idExcluido) {
        for (Usuario u : usuarios) {
            if (idExcluido != null && u.getId().equals(idExcluido)) continue;
            if (u.temEmail(email)) return true;
        }
        return false;
    }

    private void validarId(String id) {
        if (Formatador.estaVazio(id)) {
            throw new UsuarioIdNuloException();
        }
    }

    private void validarEmailUnico(String email, String idExcluido) {
        if (emailJaCadastradoExceto(email, idExcluido)) {
            throw new EmailJaCadastradoException();
        }
    }

    public void salvar() {
        try {
            File arquivo = new File(ARQUIVO_DADOS);
            arquivo.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
                oos.writeObject(this);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados: " + e.getMessage(), e);
        }
    }

    public static UsuarioRepositorio carregar() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return new UsuarioRepositorio();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (UsuarioRepositorio) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new UsuarioRepositorio();
        }
    }

    public void zerar() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}
