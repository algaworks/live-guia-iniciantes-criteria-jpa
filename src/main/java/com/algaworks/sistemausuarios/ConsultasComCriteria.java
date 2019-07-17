package com.algaworks.sistemausuarios;

import com.algaworks.sistemausuarios.dto.UsuarioDTO;
import com.algaworks.sistemausuarios.model.Configuracao;
import com.algaworks.sistemausuarios.model.Dominio;
import com.algaworks.sistemausuarios.model.Usuario;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ConsultasComCriteria {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Usuarios-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        primeiraConsulta(entityManager);
//        escolhendoORetorno(entityManager);
//        fazendoProjecoes(entityManager);
//        passandoParametros(entityManager);
//        ordenandoResultados(entityManager);
        paginandoResultados(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void paginandoResultados(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery)
                .setMaxResults(2)
                .setFirstResult(4); // PRIMEIRO = (PAGINA - 1) * QTDE_PAG
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));


        /*
        String jpql = "select u from Usuario u";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setFirstResult(0)
                .setMaxResults(2);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
        */
    }


    public static void ordenandoResultados(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nome")));

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));


        /*
        String jpql = "select u from Usuario u order by u.nome";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
        */
    }

    public static void passandoParametros(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

//        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
//        Root<Usuario> root = criteriaQuery.from(Usuario.class);
//
//        criteriaQuery.select(root);
//        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
//
//        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
//        Usuario usuario = typedQuery.getSingleResult();
//        System.out.println(usuario.getId() + ", " + usuario.getNome());

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("login"), "ria"));

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        Usuario usuario = typedQuery.getSingleResult();
        System.out.println(usuario.getId() + ", " + usuario.getNome());

        /*
        String jpql = "select u from Usuario u where u.id = :idUsuario";
        TypedQuery<Usuario> typedQuery = entityManager.
                createQuery(jpql, Usuario.class).
                setParameter("idUsuario", 1);
        Usuario usuario = typedQuery.getSingleResult();
        System.out.println(usuario.getId() + ", " + usuario.getNome());


        String jpqlLog = "select u from Usuario u where u.login = :loginUsuario";
        TypedQuery<Usuario> typedQueryLog = entityManager.
                createQuery(jpqlLog, Usuario.class).
                setParameter("loginUsuario", "ria");
        Usuario usuarioLog = typedQueryLog.getSingleResult();
        System.out.println(usuarioLog.getId() + ", " + usuarioLog.getNome());
        */
    }

    public static void fazendoProjecoes(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

//        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
//        Root<Usuario> root = criteriaQuery.from(Usuario.class);
//
//        criteriaQuery.multiselect(root.get("id"), root.get("login"), root.get("nome"));
//
//        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
//        List<Object[]> lista = typedQuery.getResultList();
//        lista.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));

        CriteriaQuery<UsuarioDTO> criteriaQuery = criteriaBuilder.createQuery(UsuarioDTO.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(criteriaBuilder.construct(UsuarioDTO.class,
                root.get("id"), root.get("login"), root.get("nome")));

        TypedQuery<UsuarioDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<UsuarioDTO> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println("DTO: " + u.getId() + ", " + u.getNome()));




        /*
        String jpqlArr = "select id, login, nome from Usuario";
        TypedQuery<Object[]> typedQueryArr = entityManager.createQuery(jpqlArr, Object[].class);
        List<Object[]> listaArr = typedQueryArr.getResultList();
        listaArr.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));


        String jpqlDto = "select new com.algaworks.sistemausuarios.dto.UsuarioDTO(id, login, nome)" +
                "from Usuario";
        TypedQuery<UsuarioDTO> typedQueryDto = entityManager.createQuery(jpqlDto, UsuarioDTO.class);
        List<UsuarioDTO> listaDto = typedQueryDto.getResultList();
        listaDto.forEach(u -> System.out.println("DTO: " + u.getId() + ", " + u.getNome()));
        */
    }

    public static void escolhendoORetorno(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

//        CriteriaQuery<Dominio> criteriaQuery = criteriaBuilder.createQuery(Dominio.class);
//        Root<Usuario> root = criteriaQuery.from(Usuario.class);
//
//        criteriaQuery.select(root.get("dominio"));
//
//        TypedQuery<Dominio> typedQuery = entityManager.createQuery(criteriaQuery);
//        List<Dominio> lista = typedQuery.getResultList();
//        lista.forEach(d -> System.out.println(d.getId() + ", " + d.getNome()));

        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root.get("nome"));

        TypedQuery<String> typedQuery = entityManager.createQuery(criteriaQuery);
        List<String> lista = typedQuery.getResultList();
        lista.forEach(nome -> System.out.println("Nome: " + nome));

        /*
        String jpql = "select u.dominio from Usuario u where u.id = 1";
        TypedQuery<Dominio> typedQuery = entityManager.createQuery(jpql, Dominio.class);
        Dominio dominio = typedQuery.getSingleResult();
        System.out.println(dominio.getId() + ", " + dominio.getNome());

        String jpqlNom = "select u.nome from Usuario u";
        TypedQuery<String> typedQueryNom = entityManager.createQuery(jpqlNom, String.class);
        List<String> listaNom = typedQueryNom.getResultList();
        listaNom.forEach(nome -> System.out.println(nome));
        */
    }

    public static void primeiraConsulta(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> root = criteriaQuery.from(Usuario.class);

        criteriaQuery.select(root);

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

        /*
        String jpql = "select u from Usuario u";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
        */
    }
}
